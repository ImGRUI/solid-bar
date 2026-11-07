package me.imgrui.solidbar.mixin;

import me.imgrui.solidbar.config.SolidBarConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
//? >=26.2 {
import net.minecraft.client.gui.Hud;
//?} else {
/*import net.minecraft.client.gui.Gui;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.imgrui.solidbar.SolidBar.mc;
import static me.imgrui.solidbar.SolidBar.parseResult;

//? >=26.2 {
@Mixin(Hud.class)
//?} else {
/*@Mixin(Gui.class)
*///?}
public class HudMixin {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void solidBar$extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!SolidBarConfig.config().modEnabled ||
                //? >=26.2 {
                graphics.guiRenderState.isHudHidden
                //?} else {
                /*mc.options.hideGui
                *///?}
        || mc.level == null || mc.player == null) return;
        switch (SolidBarConfig.config().renderType) {
            case DEFAULT -> graphics.centeredText(mc.font, parseResult, graphics.guiWidth() / 2, graphics.guiHeight() - SolidBarConfig.config().defaultRenderIndentY, -1);
            case TOP_LEFT -> graphics.text(mc.font, parseResult, SolidBarConfig.config().renderIndentX, SolidBarConfig.config().renderIndentY, -1);
            case TOP_RIGHT -> graphics.text(mc.font, parseResult, graphics.guiWidth() - SolidBarConfig.config().renderIndentX - mc.font.width(parseResult), SolidBarConfig.config().renderIndentY, -1);
            case BOTTOM_LEFT -> graphics.text(mc.font, parseResult, SolidBarConfig.config().renderIndentX, graphics.guiHeight() - SolidBarConfig.config().renderIndentY - mc.font.lineHeight, -1);
            case BOTTOM_RIGHT -> graphics.text(mc.font, parseResult, graphics.guiWidth() - SolidBarConfig.config().renderIndentX - mc.font.width(parseResult), graphics.guiHeight() - SolidBarConfig.config().renderIndentY - mc.font.lineHeight, -1);
        }
    }
}
