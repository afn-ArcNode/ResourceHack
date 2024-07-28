package arcnode.reshack;

import arcnode.reshack.common.CrypticUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class EncryptCommand extends Command {
    protected EncryptCommand() {
        super("res-encrypt");

        this.setPermission("reshack.encrypt");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: res-encrypt <path>");
        } else {
            Path file = Path.of(args[0]);

            if (!Files.exists(file)) {
                sender.sendMessage("File not exists");
            } else {
                new Thread(() -> {
                    try {
                        byte[] read = Files.readAllBytes(file);
                        Path out = Path.of(args[0] + ".out");
                        Files.write(out, CrypticUtils.encrypt(
                                Objects.requireNonNull(ResourceHack.getInstance().getConfig().getString("cryptic_key"), "cryptic_key"),
                                read
                        ));
                        sender.sendMessage("Encrypted and saved to " + out);
                    } catch (Throwable t) {
                        sender.sendMessage("Unable to encrypt: " + t);
                        ResourceHack.getInstance().getSLF4JLogger().info("Unable to encrypt {}", args[0], t);
                    }
                }).start();
            }
        }

        return true;
    }
}
