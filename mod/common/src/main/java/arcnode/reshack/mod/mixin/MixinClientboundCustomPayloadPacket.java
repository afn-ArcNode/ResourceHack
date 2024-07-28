package arcnode.reshack.mod.mixin;

import arcnode.reshack.mod.ResourceHack;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientboundCustomPayloadPacket.class, priority = 1514)
public abstract class MixinClientboundCustomPayloadPacket {
    @Inject(
            method = "handle(Lnet/minecraft/network/protocol/game/ClientGamePacketListener;)V",
            at = @At("HEAD"),
            cancellable = true)
    public void injectHandle(ClientGamePacketListener clientGamePacketListener, CallbackInfo ci) {
        if (ResourceHack.configure(ClientboundCustomPayloadPacket.class.cast(this)))
            ci.cancel();
    }
}
