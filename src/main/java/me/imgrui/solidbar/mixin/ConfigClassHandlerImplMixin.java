package me.imgrui.solidbar.mixin;

import dev.isxander.yacl3.config.v2.impl.ConfigClassHandlerImpl;
import me.imgrui.solidbar.SolidBar;
import me.imgrui.solidbar.config.SolidBarConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if !26.2 {
import java.util.regex.Pattern;
//?}

@Mixin(ConfigClassHandlerImpl.class)
public abstract class ConfigClassHandlerImplMixin {

    @Shadow
    public abstract <T> Class<T> configClass();

    @Inject(method = "load", at = @At("RETURN"))
    private void solidBar$load(CallbackInfoReturnable<Boolean> cir) {
        if (SolidBarConfig.config() == null || !configClass().equals(SolidBarConfig.class)) return;
        solidBar$getFormatted();
    }

    @Inject(method = "save", at = @At("TAIL"))
    private void solidBar$save(CallbackInfo ci) {
        if (SolidBarConfig.config() == null || !configClass().equals(SolidBarConfig.class)) return;
        solidBar$getFormatted();
    }

    @Unique
    private void solidBar$getFormatted() {
        if (SolidBarConfig.config().barProfilesEnabled) {
            int index = SolidBarConfig.config().barProfileNumber;
            if (index >= 0 && index < SolidBarConfig.config().barProfiles.size()) {
                String modBar = SolidBarConfig.config().barProfiles.get(index);
                SolidBar.modBarFormatted = solidBar$format(modBar);
            } else {
                SolidBar.modBarFormatted = "IndexOutOfBounds :(";
            }
            return;
        }
        String modBar = SolidBarConfig.config().modBar;
        SolidBar.modBarFormatted = solidBar$format(modBar);
    }

    @Unique
    private String solidBar$format(String modBar) {
        // Due to text changing position while stealth is enabled
        //? if !26.2 {
        if (SolidBarConfig.config().stealthMode) {
            Pattern stealthPattern = Pattern.compile("\\{(player|entity)\\.(pos|convert|yaw|pitch)(\\.[xyz])?}");
            modBar = stealthPattern.matcher(modBar).replaceAll(mr -> "&k" + mr.group() + "&r");
        }
        //?}
        return modBar.replace("&", "§");
    }
}
