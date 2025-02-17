package szy.endportalfix.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import szy.endportalfix.config.ModConfig;

@Mixin(TheEndPortalBlockEntity.class)
public class MixinTheEndPortalBlockEntity {
    @Inject(
            method = "shouldRenderFace",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void shouldRenderFace(Direction direction, CallbackInfoReturnable<Boolean> cir) {
        int mode = ModConfig.getInstance().getRenderMode();
        if (mode != 0) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(direction == Direction.UP);
        }
    }
}
