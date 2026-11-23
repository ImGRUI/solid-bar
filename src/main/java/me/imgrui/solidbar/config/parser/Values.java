package me.imgrui.solidbar.config.parser;

import me.imgrui.solidbar.config.SolidBarConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.clock.ClockManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.timeline.Timelines;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static me.imgrui.solidbar.SolidBar.mc;

public class Values {

    public static class General {
        public static int getFps() {
            return mc.getFps();
        }

        public static String getTime() {
            DateFormat dateFormat = new SimpleDateFormat(SolidBarConfig.config().timeFormat);
            return dateFormat.format(System.currentTimeMillis());
        }
    }

    public static class Player {
        public static String getName() {
            return mc.getUser().getName();
        }

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
            if (mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return "?";
            return switch (mc.getCameraEntity().getDirection()) {
                case NORTH -> "N";
                case SOUTH -> "S";
                case WEST -> "W";
                case EAST -> "E";
                default -> "?";
            };
        }

        public static String getDirectionCoords() {
            if (mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return "?";
            return switch (mc.getCameraEntity().getDirection()) {
                case NORTH -> "-Z";
                case SOUTH -> "+Z";
                case WEST -> "-X";
                case EAST -> "+X";
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
            if (mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return 0;
            return mc.getCameraEntity().getBlockX();
        }

        public static int getY() {
            if (mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return 0;
            return mc.getCameraEntity().getBlockY();
        }

        public static int getZ() {
            if (mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return 0;
            return mc.getCameraEntity().getBlockZ();
        }

        public static String getChunkX() {
            if (mc.getCameraEntity() == null) return "0";
            return String.format("%02d", mc.getCameraEntity().getBlockX() & 15);
        }

        public static String getChunkY() {
            if (mc.getCameraEntity() == null) return "0";
            return String.format("%02d", mc.getCameraEntity().getBlockY() & 15);
        }

        public static String getChunkZ() {
            if (mc.getCameraEntity() == null) return "0";
            return String.format("%02d", mc.getCameraEntity().getBlockZ() & 15);
        }

        public static int getConvertX() {
            if (mc.level == null || mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return 0;
            return switch (mc.level.dimension().identifier().toLanguageKey()) {
                case "minecraft.overworld" -> mc.getCameraEntity().getBlockX() / 8;
                case "minecraft.the_nether" -> mc.getCameraEntity().getBlockX() * 8;
                default -> 0;
            };
        }

        public static int getConvertZ() {
            if (mc.level == null || mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return 0;
            return switch (mc.level.dimension().identifier().toLanguageKey()) {
                case "minecraft.overworld" -> mc.getCameraEntity().getBlockZ() / 8;
                case "minecraft.the_nether" -> mc.getCameraEntity().getBlockZ() * 8;
                default -> 0;
            };
        }

        public static String getHorizontalMeterSpeed() {
            if (mc.getCameraEntity() == null) return "0.0";
            return String.format("%.1f", mc.getCameraEntity().getKnownSpeed().horizontal().length() * 20);
        }

        public static String getHorizontalKilometerSpeed() {
            if (mc.getCameraEntity() == null) return "0.0";
            return String.format("%.1f", mc.getCameraEntity().getKnownSpeed().horizontal().length() * 20 * 3.6);
        }

        public static String getMeterSpeed() {
            if (mc.getCameraEntity() == null) return "0.0";
            return String.format("%.1f", mc.getCameraEntity().getKnownSpeed().length() * 20);
        }

        public static String getKilometerSpeed() {
            if (mc.getCameraEntity() == null) return "0.0";
            return String.format("%.1f", mc.getCameraEntity().getKnownSpeed().length() * 20 * 3.6);
        }

        public static String getYaw() {
            if (mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return "0.0";
            return String.format("%.1f", Mth.wrapDegrees(mc.getCameraEntity().getYRot()));
        }

        public static String getPitch() {
            if (mc.getCameraEntity() == null || SolidBarConfig.config().stealthMode) return "0.0";
            return String.format("%.1f", Mth.wrapDegrees(mc.getCameraEntity().getXRot()));
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

        public static String getBiome() {
            if (mc.level == null || mc.getCameraEntity() == null) return "";
            Identifier identifier = mc.level.getBiome(mc.getCameraEntity().getOnPos()).unwrapKey().map(ResourceKey::identifier).orElse(null);
            if (identifier != null) {
                return Component.translatable(Util.makeDescriptionId("biome", identifier)).getString();
            }
            return null;
        }

        public static int getDays() {
            if (mc.level == null) return 0;
            ClockManager clockManager = mc.level.clockManager();
            return mc.level.registryAccess().get(Timelines.OVERWORLD_DAY).map(timeline -> timeline.value().getPeriodCount(clockManager)).orElse(0);
        }

        public static String getGlobalWeather() {
            if (mc.level == null) return "";
            if (mc.level.isThundering()) {
                return Component.translatable("solid-bar.weather.thunder").getString();
            } else if (mc.level.isRaining()) {
                return Component.translatable("solid-bar.weather.rain").getString();
            } else {
                return Component.translatable("solid-bar.weather.clear").getString();
            }
        }

        public static String getLocalWeather() {
            if (mc.level == null || mc.getCameraEntity() == null) return "";
            BlockPos blockPos = mc.getCameraEntity().getOnPos();
            Biome biome = mc.level.getBiome(blockPos).value();
            if (mc.level.isThundering()) {
                return biome.coldEnoughToSnow(blockPos, blockPos.getY()) ? Component.translatable("solid-bar.weather.thunder_snow").getString() : Component.translatable("solid-bar.weather.thunder").getString();
            } else if (mc.level.isRaining()) {
                return biome.coldEnoughToSnow(blockPos, blockPos.getY()) ? Component.translatable("solid-bar.weather.snow").getString() : Component.translatable("solid-bar.weather.rain").getString();
            } else {
                return Component.translatable("solid-bar.weather.clear").getString();
            }
        }
    }

    public static class Entity {
        public static String getName() {
            if (mc.crosshairPickEntity == null) return "";
            return mc.crosshairPickEntity.getName().getString();
        }

        public static double getHealth() {
            if (mc.crosshairPickEntity == null || mc.crosshairPickEntity.asLivingEntity() == null) return 0;
            return Math.round(mc.crosshairPickEntity.asLivingEntity().getHealth() * 10) / 10.0;
        }

        public static double getMaxHealth() {
            if (mc.crosshairPickEntity == null || mc.crosshairPickEntity.asLivingEntity() == null) return 0;
            return Math.round(mc.crosshairPickEntity.asLivingEntity().getMaxHealth() * 10) / 10.0;
        }

        public static double getHealthPercent() {
            if (mc.crosshairPickEntity == null || mc.crosshairPickEntity.asLivingEntity() == null) return 0;
            return Math.round(mc.crosshairPickEntity.asLivingEntity().getHealth() * 100 / mc.crosshairPickEntity.asLivingEntity().getMaxHealth() * 10) / 10.0;
        }

        public static String getDirection() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return "?";
            return switch (mc.crosshairPickEntity.getDirection()) {
                case NORTH -> "N";
                case SOUTH -> "S";
                case WEST -> "W";
                case EAST -> "E";
                default -> "?";
            };
        }

        public static String getDirectionCoords() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return "?";
            return switch (mc.crosshairPickEntity.getDirection()) {
                case NORTH -> "-Z";
                case SOUTH -> "+Z";
                case WEST -> "-X";
                case EAST -> "+X";
                default -> "?";
            };
        }

        public static int getX() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return mc.crosshairPickEntity.getBlockX();
        }

        public static int getY() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return mc.crosshairPickEntity.getBlockY();
        }

        public static int getZ() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return mc.crosshairPickEntity.getBlockZ();
        }

        public static String getChunkX() {
            if (mc.crosshairPickEntity == null) return "0";
            return String.format("%02d", mc.crosshairPickEntity.getBlockX() & 15);
        }

        public static String getChunkY() {
            if (mc.crosshairPickEntity == null) return "0";
            return String.format("%02d", mc.crosshairPickEntity.getBlockY() & 15);
        }

        public static String getChunkZ() {
            if (mc.crosshairPickEntity == null) return "0";
            return String.format("%02d", mc.crosshairPickEntity.getBlockZ() & 15);
        }

        public static int getConvertX() {
            if (mc.level == null || mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return switch (mc.level.dimension().identifier().toLanguageKey()) {
                case "minecraft.overworld" -> mc.crosshairPickEntity.getBlockX() / 8;
                case "minecraft.the_nether" -> mc.crosshairPickEntity.getBlockX() * 8;
                default -> 0;
            };
        }

        public static int getConvertZ() {
            if (mc.level == null || mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return 0;
            return switch (mc.level.dimension().identifier().toLanguageKey()) {
                case "minecraft.overworld" -> mc.crosshairPickEntity.getBlockZ() / 8;
                case "minecraft.the_nether" -> mc.crosshairPickEntity.getBlockZ() * 8;
                default -> 0;
            };
        }

        public static String getHorizontalMeterSpeed() {
            if (mc.crosshairPickEntity == null) return "0.0";
            return String.format("%.1f", mc.crosshairPickEntity.getKnownSpeed().horizontal().length() * 20);
        }

        public static String getHorizontalKilometerSpeed() {
            if (mc.crosshairPickEntity == null) return "0.0";
            return String.format("%.1f", mc.crosshairPickEntity.getKnownSpeed().horizontal().length() * 20 * 3.6);
        }

        public static String getMeterSpeed() {
            if (mc.crosshairPickEntity == null) return "0.0";
            return String.format("%.1f", mc.crosshairPickEntity.getKnownSpeed().length() * 20);
        }

        public static String getKilometerSpeed() {
            if (mc.crosshairPickEntity == null) return "0.0";
            return String.format("%.1f", mc.crosshairPickEntity.getKnownSpeed().length() * 20 * 3.6);
        }

        public static String getYaw() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return "0.0";
            return String.format("%.1f", Mth.wrapDegrees(mc.crosshairPickEntity.getYRot()));
        }

        public static String getPitch() {
            if (mc.crosshairPickEntity == null || SolidBarConfig.config().stealthMode) return "0.0";
            return String.format("%.1f", Mth.wrapDegrees(mc.crosshairPickEntity.getXRot()));
        }
    }

    public static class Server {
        public static String getIp() {
            if (mc.player == null || mc.player.connection.getServerData() == null) return "";
            return mc.player.connection.getServerData().ip;
        }

        public static String getName() {
            if (mc.player == null || mc.player.connection.getServerData() == null) return "";
            return mc.player.connection.getServerData().name;
        }
    }
}
