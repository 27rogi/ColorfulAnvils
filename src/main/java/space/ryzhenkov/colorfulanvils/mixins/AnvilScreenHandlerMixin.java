package space.ryzhenkov.colorfulanvils.mixins;

import eu.pb4.placeholders.api.TextParserUtils;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
        return customTextFormatting(name);
    }

    @ModifyArg(method = "setNewItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCustomName(Lnet/minecraft/text/Text;)Lnet/minecraft/item/ItemStack;", ordinal = 0))
    public Text newItemName(Text name) {
        return customTextFormatting(name);
    }

    @Unique
    private Text customTextFormatting(Text defaultText) {
        // disable formatting for players with 'colorfulanvils.use' permission set to false, otherwise allow formatting
        if (!Permissions.check(this.player, "colorfulanvils.use", true)) return defaultText;

        Text formattedText = TextParserUtils.formatTextSafe(this.newItemName);
        // check if the new item name has a custom format
        boolean hasFormat = switch (formattedText.getSiblings().size()) {
            case 0 -> false;
            // this detection fails if the item name is "<reset>Itemname"
            case 1 -> !formattedText.getSiblings().get(0).getStyle().equals(Style.EMPTY);
            default -> true;
        };
        // if the new item name has a custom format, disable italics
        if (hasFormat) return formattedText.copy().setStyle(formattedText.getStyle().withItalic(false));
        return formattedText;
    }
}
