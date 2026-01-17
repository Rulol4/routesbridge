package me.Rulol4.RoutesBridge.mixin.client;

import me.Rulol4.RoutesBridge.SidebarTracker;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class ScoreboardMixin {

    @Redirect(
            method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)V"
            )
    )

    private void drawText(net.minecraft.client.gui.DrawContext context, TextRenderer textRenderer, Text text, int x, int y, int color, boolean shadow) {
        SidebarTracker.capture(text);
        context.drawText(textRenderer, text, x, y, color, shadow);
    }
}
