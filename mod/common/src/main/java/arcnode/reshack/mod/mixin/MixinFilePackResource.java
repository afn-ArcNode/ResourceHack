package arcnode.reshack.mod.mixin;

import arcnode.reshack.mod.ResourceHack;
import arcnode.reshack.mod.access.ForgeAccessHack;
import net.minecraft.server.packs.FilePackResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Mixin(FilePackResources.class)
public class MixinFilePackResource {
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/AbstractPackResources;<init>(Ljava/io/File;)V")
    )
    private static File injectConstructor(File file) {
        try {
            byte[] original = Files.readAllBytes(file.toPath());

            String key = ResourceHack.getKey();
            if (key == null) {
                // Not configured yet
                ResourceHack.LOG.info("Resource not decrypted (Not configured yet)");
                return file;
            }
            if (key.equals("NULL_KEY")) {
                // NULL_KEY (default configuration)
                ResourceHack.LOG.warn("Null key received from server");
                return file;
            }
            if (ForgeAccessHack.accessValidate(original)) {
                String yee = UUID.randomUUID().toString();
                File tmp = File.createTempFile(yee.substring(0, 8), null);

                byte[] dec = ForgeAccessHack.accessDecrypt(key, original);
                Files.write(tmp.toPath(), dec);

                ResourceHack.LOG.info("Resource decryption completed");
                return tmp;
            } else {
                // Not encrypted
                return file;
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
