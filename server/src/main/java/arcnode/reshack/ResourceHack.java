package arcnode.reshack;

import arcnode.reshack.common.ConfigData;
import arcnode.reshack.common.Networking;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPluginMessage;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ResourceHack extends JavaPlugin implements PacketListener {
    private static final String CONFIG_CHANNEL = "%s:%s".formatted(Networking.NAMESPACE, Networking.CHANNEL_CONFIG);
    private static final String RESET_CHANNEL = "%s:%s".formatted(Networking.NAMESPACE, Networking.CHANNEL_RESET);
    @Getter
    private static ResourceHack instance;

    private Metrics metrics;
    private byte[] clientConfigData = new byte[0];

    @Override
    public void onLoad() {
        instance = this;

        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Load and encode configuration
        FileConfiguration conf = getConfig();
        ByteBuf confBuf = Networking.write(new ConfigData(
                conf.getString("cryptic_key", "NULL_KEY")
        ));
        clientConfigData = confBuf.array();
        getSLF4JLogger().info("Encoded configuration to {} bytes", clientConfigData.length);

        // Register packet listener
        PacketEvents.getAPI().getEventManager().registerListener(this, PacketListenerPriority.HIGH);

        // Register command
        Bukkit.getCommandMap().register(getName(), new EncryptCommand());

        // Setup metrics
        getSLF4JLogger().info("Setting up metrics");
        metrics = new Metrics(this, 22807);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLUGIN_MESSAGE) {
            WrapperPlayClientPluginMessage pkt = new WrapperPlayClientPluginMessage(event);
            if (pkt.getChannelName().equals(CONFIG_CHANNEL)) {
                getSLF4JLogger().info("Received configuration request, sending configuration to {}", event.getUser().getName());
                event.getUser().sendPacket(new WrapperPlayServerPluginMessage(CONFIG_CHANNEL, clientConfigData));
            }
        }
    }
}
