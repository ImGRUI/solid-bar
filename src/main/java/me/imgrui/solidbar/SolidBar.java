package me.imgrui.solidbar;

import com.mojang.blaze3d.platform.InputConstants;
import me.imgrui.solidbar.config.SolidBarConfig;
import me.imgrui.solidbar.config.parser.StarScript;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SolidBar implements ModInitializer {
	public static final String MOD_ID = "solid-bar";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Minecraft mc = Minecraft.getInstance();
	public static StarScript ss;

	public static String modBarFormatted;
	public static String parseResult;

	private static final Random random = new Random();

	KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("solid-bar","keybinds"));
	KeyMapping toggleModEnabled = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.toggleModEnabled",
			//? if 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.KEY_RALT, CATEGORY));
	KeyMapping toggleStealthMode = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.toggleStealthMode",
			//? if 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.UNKNOWN.getValue(), CATEGORY));
	KeyMapping randomBarProfile = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.randomBarProfile",
			//? if 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.UNKNOWN.getValue(), CATEGORY));
	KeyMapping nextBarProfile = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.nextBarProfile",
			//? if 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.UNKNOWN.getValue(), CATEGORY));
	KeyMapping prevBarProfile = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.prevBarProfile",
			//? if 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.UNKNOWN.getValue(), CATEGORY));
	KeyMapping resetBarProfile = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.solid-bar.resetBarProfile",
			//? if 26.3 {
			InputConstants.Type.KEYBOARD,
			//?} else {
			/*InputConstants.Type.KEYSYM,
			 *///?}
	InputConstants.UNKNOWN.getValue(), CATEGORY));

	@Override
	public void onInitialize() {
		LOGGER.info("[Solid Bar] Loading Solid Bar");
		SolidBarConfig.CONFIG.load();
		ss = new StarScript();
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.level == null || client.player == null) return;
			if (toggleModEnabled.consumeClick()) {
				SolidBarConfig.config().modEnabled = !SolidBarConfig.config().modEnabled;
				SolidBarConfig.CONFIG.save();
			}
			if (!SolidBarConfig.config().modEnabled) return;
			if (toggleStealthMode.consumeClick()) {
				SolidBarConfig.config().stealthMode = !SolidBarConfig.config().stealthMode;
				SolidBarConfig.CONFIG.save();
			}
			if (randomBarProfile.consumeClick()) {
				if (SolidBarConfig.config().barProfiles.size() >= 2) {
					SolidBarConfig.config().barProfileNumber = generateIndex(SolidBarConfig.config().barProfileNumber, SolidBarConfig.config().barProfiles.size());
					SolidBarConfig.CONFIG.save();
				}
			}
			if (nextBarProfile.consumeClick()) {
				if (SolidBarConfig.config().barProfiles.size() >= 2) {
					if (SolidBarConfig.config().barProfiles.size() - 1 == SolidBarConfig.config().barProfileNumber) {
						SolidBarConfig.config().barProfileNumber = 0;
					} else {
						SolidBarConfig.config().barProfileNumber += 1;
					}
					SolidBarConfig.CONFIG.save();
				}
			}
			if (prevBarProfile.consumeClick()) {
				if (SolidBarConfig.config().barProfiles.size() >= 2) {
					if (SolidBarConfig.config().barProfileNumber == 0) {
						SolidBarConfig.config().barProfileNumber = SolidBarConfig.config().barProfiles.size() - 1;
					} else {
						SolidBarConfig.config().barProfileNumber -= 1;
					}
					SolidBarConfig.CONFIG.save();
				}
			}
			if (resetBarProfile.consumeClick()) {
				if (SolidBarConfig.config().barProfileNumber != 0) {
					SolidBarConfig.config().barProfileNumber = 0;
					SolidBarConfig.CONFIG.save();
				}
			}
			parseResult = ss.run(StarScript.compile(modBarFormatted));
		});
	}

	private static int generateIndex(int current, int size) {
		if (size < 2) return current;
		int offset = random.nextInt(1, size);
		int index = current + offset;
		if (index >= size) index -= size;
		return index;
	}
}
