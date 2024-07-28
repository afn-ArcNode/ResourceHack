package arcnode.reshack.mod.fabric;

import arcnode.reshack.mod.ResourceHack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

public final class ResourceHackFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER)
            throw new UnsupportedOperationException("Client only mod");

        ResourceHack.init();

        ClientEntityEvents.ENTITY_LOAD.register((Entity entity, ClientLevel world) -> {
            if (entity instanceof LocalPlayer)
                ResourceHack.sendRequest();
        });
    }
}
