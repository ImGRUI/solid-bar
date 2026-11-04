package me.imgrui.config.parser;

import me.imgrui.config.SolidBarConfig;
import net.minecraft.network.chat.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static me.imgrui.SolidBar.mc;

public class Values {

    public static class General {

        static DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        public static int getFps() {
            return mc.getFps();
        }

        public static String getTime() {
            return dateFormat.format(System.currentTimeMillis());
        }
    }

    public static class Player {
        public static double getHealth() {
            if (mc.player == null) return 0;
            return Math.round(mc.player.getHealth() * 10) / 10.0;
        }

        public static double getMaxHealth() {
            if (mc.player == null) return 0;
            return Math.round(mc.player.getMaxHealth() * 10) / 10.0;
        }

        public static double getHealthPercent() {
            if (mc.player == null) return 0;
            return Math.round(mc.player.getHealth() * 100 / mc.player.getMaxHealth() * 10) / 10.0;
        }

        public static int getArmor() {
            if (mc.player == null) return 0;
            return mc.player.getArmorValue();
        }

        public static String getDirection() {
            if (mc.player == null || SolidBarConfig.config().stealthMode) return "?";
            return switch (mc.player.getDirection()) {
                case NORTH -> "N";
                case SOUTH -> "S";
                case WEST -> "W";
                case EAST -> "E";
                default -> "?";
            };
        }

        public static int getHunger() {
            if (mc.player == null) return 0;
            return mc.player.getFoodData().getFoodLevel();
        }

        public static int getPing() {
            if (mc.player == null) return 0;
            return mc.player.connection.getPlayerInfo(mc.player.getUUID()).getLatency();
        }

        public static int getX() {
            if (mc.player == null || SolidBarConfig.config().stealthMode) return 0;
            return (int) mc.player.getX();
        }

        public static int getY() {
            if (mc.player == null || SolidBarConfig.config().stealthMode) return 0;
            return (int) mc.player.getY();
        }

        public static int getZ() {
            if (mc.player == null || SolidBarConfig.config().stealthMode) return 0;
            return (int) mc.player.getZ();
        }
    }

    public static class World {
        public static String getDimension() {
            if (mc.level == null) return "";
            var dimension = mc.level.dimension().identifier().toLanguageKey();
            return switch (dimension) {
                case "minecraft.overworld" -> Component.translatable("solid-bar.world.overworld").getString();
                case "minecraft.the_nether" -> Component.translatable("solid-bar.world.the_nether").getString();
                case "minecraft.the_end" -> Component.translatable("solid-bar.world.the_end").getString();
                default -> dimension;
            };
        }

        public static String getTime() {
            if (mc.level == null) return "";
            long gameDayTime = mc.level.getDefaultClockTime() + 6000;
            int hours = (int) ((gameDayTime / 1000) % 24);
            int minutes = (int) ((gameDayTime % 1000) * 60 / 1000);
            return String.format("%02d:%02d", hours, minutes);
        }
    }

    public static class Entity {
        public static String getName() {
            if (mc.crosshairPickEntity == null) return "";
            return mc.crosshairPickEntity.getName().getString();
        }

        public static double getHealth() {
            if (mc.crosshairPickEntity == null) return 0;
            return Math.round(mc.crosshairPickEntity.asLivingEntity().getHealth() * 10) / 10.0;
        }

        public static double getMaxHealth() {
            if (mc.crosshairPickEntity == null) return 0;
            return Math.round(mc.crosshairPickEntity.asLivingEntity().getMaxHealth() * 10) / 10.0;
        }

        public static double getHealthPercent() {
            if (mc.crosshairPickEntity == null) return 0;
            return Math.round(mc.crosshairPickEntity.asLivingEntity().getHealth() * 100 / mc.crosshairPickEntity.asLivingEntity().getMaxHealth() * 10) / 10.0;
        }

        public static int getX() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return (int) mc.crosshairPickEntity.getX();
        }

        public static int getY() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return (int) mc.crosshairPickEntity.getY();
        }

        public static int getZ() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return (int) mc.crosshairPickEntity.getZ();
        }
    }
}
