package arcnode.reshack.mod.forge;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import arcnode.reshack.mod.ResourceHack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ResourceHack.MOD_ID)
@OnlyIn(Dist.CLIENT)
public final class ResourceHackForge {
    public ResourceHackForge() {
        // Run our common setup.
        ResourceHack.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LocalPlayer)
            ResourceHack.sendRequest();
    }
}
