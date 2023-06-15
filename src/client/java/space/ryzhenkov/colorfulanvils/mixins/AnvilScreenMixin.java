package space.ryzhenkov.colorfulanvils.mixins;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin {
    @Shadow
    private TextFieldWidget nameField;

    @Inject(at = @At("RETURN"), method = "setup")
    public void setup(CallbackInfo info){
        nameField.setMaxLength(255);
    }
}
