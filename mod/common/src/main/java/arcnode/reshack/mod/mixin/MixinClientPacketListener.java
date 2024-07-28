package arcnode.reshack.mod.mixin;

import arcnode.reshack.mod.ResourceHack;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    @Redirect(
            method = "handleResourcePack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ServerData;getResourcePackStatus()Lnet/minecraft/client/multiplayer/ServerData$ServerPackStatus;")
    )
    // Forced enable server resource pack
    public ServerData.ServerPackStatus redirectResourcePackStatus(ServerData instance) {
        return ServerData.ServerPackStatus.ENABLED;
    }

    @Inject(
            method = "handleResourcePack",
            at = @At("HEAD"),
            cancellable = true)
    // Prevent resource reloading between servers when connecting through a proxy
    public void injectHandleResourcePack(ClientboundResourcePackPacket clientboundResourcePackPacket, CallbackInfo ci) {
        if (ResourceHack.getLoadedUrls().contains(clientboundResourcePackPacket.getUrl())) {
            ci.cancel();
        } else ResourceHack.getLoadedUrls().add(clientboundResourcePackPacket.getUrl());
    }
}
