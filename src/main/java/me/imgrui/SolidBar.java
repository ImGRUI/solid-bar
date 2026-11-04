package me.imgrui;

import com.mojang.blaze3d.platform.InputConstants;
import me.imgrui.config.SolidBarConfig;
import me.imgrui.config.parser.StarScript;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if !26.2 {
import java.util.regex.Pattern;
//?}

public class SolidBar implements ModInitializer {
	public static final String MOD_ID = "solid-bar";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Minecraft mc = Minecraft.getInstance();
	public static StarScript ss;

	public static String modBarFormatted;
	public static String parseResult;

	KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("solid-bar","keybinds"));
	KeyMapping toggleModEnabled = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.toggleModEnabled",
			//? 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.KEY_RALT, CATEGORY));
	KeyMapping toggleStealthMode = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.toggleStealthMode",
			//? 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.UNKNOWN.getValue(), CATEGORY));

	@Override
	public void onInitialize() {
		LOGGER.info("[Solid Bar] Loading Solid Bar");
		SolidBarConfig.CONFIG.load();
		getModBarFormatted(SolidBarConfig.config().modBar);
		ss = new StarScript();
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.level == null || client.player == null) return;
			if (toggleModEnabled.consumeClick()) {
				SolidBarConfig.config().modEnabled = !SolidBarConfig.config().modEnabled;
				SolidBarConfig.CONFIG.save();
			}
			if (toggleStealthMode.consumeClick()) {
				SolidBarConfig.config().stealthMode = !SolidBarConfig.config().stealthMode;
				SolidBarConfig.CONFIG.save();
			}
			if (!SolidBarConfig.config().modEnabled) return;
			if (modBarFormatted.isEmpty()) parseResult = "";
			parseResult = ss.run(StarScript.compile(modBarFormatted));
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	private void getModBarFormatted(String modBar) {
		// Due to text changing position while stealth is enabled
		//? if !26.2 {
		if (SolidBarConfig.config().stealthMode) {
			Pattern stealthPattern = Pattern.compile("\\{(player|entity)\\.pos\\.[xyz]}");
			modBar = stealthPattern.matcher(modBar).replaceAll(mr -> "&k" + mr.group() + "&r");
		}
		//?}
		modBarFormatted = modBar.replace("&", "§");
	}
}
