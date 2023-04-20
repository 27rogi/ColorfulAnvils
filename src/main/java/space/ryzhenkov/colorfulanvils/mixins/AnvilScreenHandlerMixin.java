package space.ryzhenkov.colorfulanvils.mixins;

import eu.pb4.placeholders.TextParser;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Shadow
    private String newItemName;

    @ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCustomName(Lnet/minecraft/text/Text;)Lnet/minecraft/item/ItemStack;", ordinal = 0))
    public Text updateResult(Text name) {
        // disable formatting for players with 'colorfulanvils.use' permission set to false, otherwise allow formatting
        if (!Permissions.check(this.player, "colorfulanvils.use", true)) return new LiteralText(this.newItemName);
        return TextParser.parseSafe(this.newItemName);
    }

    @ModifyArg(method = "setNewItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCustomName(Lnet/minecraft/text/Text;)Lnet/minecraft/item/ItemStack;", ordinal = 0))
    public Text newItemName(Text name) {
        if (!Permissions.check(this.player, "colorfulanvils.use", true)) return new LiteralText(this.newItemName);
        return TextParser.parseSafe(this.newItemName);
    }
}
