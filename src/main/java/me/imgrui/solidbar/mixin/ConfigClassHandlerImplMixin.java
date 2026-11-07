package me.imgrui.solidbar.mixin;

import dev.isxander.yacl3.config.v2.impl.ConfigClassHandlerImpl;
import me.imgrui.solidbar.SolidBar;
import me.imgrui.solidbar.config.SolidBarConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if !26.2 {
import java.util.regex.Pattern;
//?}

@Mixin(ConfigClassHandlerImpl.class)
public abstract class ConfigClassHandlerImplMixin {

    @Shadow
    public abstract <T> Class<T> configClass();

    @Inject(method = "save", at = @At("TAIL"))
    private void solidBar$save(CallbackInfo ci) {
        if (SolidBarConfig.config() == null || !configClass().equals(SolidBarConfig.class)) return;
        String modBar = SolidBarConfig.config().modBar;
        // Due to text changing position while stealth is enabled
        //? if !26.2 {
        if (SolidBarConfig.config().stealthMode) {
            Pattern stealthPattern = Pattern.compile("\\{(player|entity)\\.pos\\.[xyz]}");
            modBar = stealthPattern.matcher(modBar).replaceAll(mr -> "&k" + mr.group() + "&r");
        }
        //?}
        SolidBar.modBarFormatted = modBar.replace("&", "§");
    }
}
