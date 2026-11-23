package me.imgrui.solidbar.config.parser;

import org.meteordev.starscript.Script;
import org.meteordev.starscript.Section;
import org.meteordev.starscript.StandardLib;
import org.meteordev.starscript.Starscript;
import org.meteordev.starscript.compiler.Compiler;
import org.meteordev.starscript.compiler.Parser;
import org.meteordev.starscript.utils.StarscriptError;
import org.meteordev.starscript.value.Value;
import org.meteordev.starscript.value.ValueMap;

public class StarScript {
    public Starscript ss = new Starscript();

    public StarScript() {
        StandardLib.init(ss);

        ss.set("general", new ValueMap()
                .set("fps", () -> Value.number(Values.General.getFps()))
                .set("time", () -> Value.string(Values.General.getTime()))
        );

        ss.set("player", new ValueMap()
                .set("name", () -> Value.string(Values.Player.getName()))
                .set("health", () -> Value.number(Values.Player.getHealth()))
                .set("health_max", () -> Value.number(Values.Player.getMaxHealth()))
                .set("health_percent", () -> Value.number(Values.Player.getHealthPercent()))
                .set("armor", () -> Value.number(Values.Player.getArmor()))
                .set("direction", () -> Value.string(Values.Player.getDirection()))
                .set("direction_towards", () -> Value.string(Values.Player.getDirectionCoords()))
                .set("hunger", () -> Value.number(Values.Player.getHunger()))
                .set("ping", () -> Value.number(Values.Player.getPing()))
                .set("pos", new ValueMap()
                        .set("x", () -> Value.number(Values.Player.getX()))
                        .set("y", () -> Value.number(Values.Player.getY()))
                        .set("z", () -> Value.number(Values.Player.getZ()))
                )
                .set("chunk", new ValueMap()
                        .set("x", () -> Value.string(Values.Player.getChunkX()))
                        .set("y", () -> Value.string(Values.Player.getChunkY()))
                        .set("z", () -> Value.string(Values.Player.getChunkZ()))
                )
                .set("convert", new ValueMap()
                        .set("x", () -> Value.number(Values.Player.getConvertX()))
                        .set("z", () -> Value.number(Values.Player.getConvertZ()))
                )
                .set("speed", new ValueMap()
                        .set("horizontal", new ValueMap()
                            .set("ms", () -> Value.string(Values.Player.getHorizontalMeterSpeed()))
                            .set("kmh", () -> Value.string(Values.Player.getHorizontalKilometerSpeed()))
                        )
                        .set("all", new ValueMap()
                                .set("ms", () -> Value.string(Values.Player.getMeterSpeed()))
                                .set("kmh", () -> Value.string(Values.Player.getKilometerSpeed()))
                        )
                )
                .set("yaw", () -> Value.string(Values.Player.getYaw()))
                .set("pitch", () -> Value.string(Values.Player.getPitch()))
        );

        ss.set("world", new ValueMap()
                .set("dimension", () -> Value.string(Values.World.getDimension()))
                .set("time", () -> Value.string(Values.World.getTime()))
                .set("biome", () -> Value.string(Values.World.getBiome()))
                .set("days", () -> Value.number(Values.World.getDays()))
        );

        ss.set("entity", new ValueMap()
                .set("name", () -> Value.string(Values.Entity.getName()))
                .set("health", () -> Value.number(Values.Entity.getHealth()))
                .set("health_max", () -> Value.number(Values.Entity.getMaxHealth()))
                .set("health_percent", () -> Value.number(Values.Entity.getHealthPercent()))
                .set("direction", () -> Value.string(Values.Entity.getDirection()))
                .set("direction_towards", () -> Value.string(Values.Entity.getDirectionCoords()))
                .set("pos", new ValueMap()
                        .set("x", () -> Value.number(Values.Entity.getX()))
                        .set("y", () -> Value.number(Values.Entity.getY()))
                        .set("z", () -> Value.number(Values.Entity.getZ()))
                )
                .set("chunk", new ValueMap()
                        .set("x", () -> Value.string(Values.Entity.getChunkX()))
                        .set("y", () -> Value.string(Values.Entity.getChunkY()))
                        .set("z", () -> Value.string(Values.Entity.getChunkZ()))
                )
                .set("convert", new ValueMap()
                        .set("x", () -> Value.number(Values.Entity.getConvertX()))
                        .set("z", () -> Value.number(Values.Entity.getConvertZ()))
                )
                .set("speed", new ValueMap()
                        .set("horizontal", new ValueMap()
                                .set("ms", () -> Value.string(Values.Entity.getHorizontalMeterSpeed()))
                                .set("kmh", () -> Value.string(Values.Entity.getHorizontalKilometerSpeed()))
                        )
                        .set("all", new ValueMap()
                                .set("ms", () -> Value.string(Values.Entity.getMeterSpeed()))
                                .set("kmh", () -> Value.string(Values.Entity.getKilometerSpeed()))
                        )
                )
                .set("yaw", () -> Value.string(Values.Entity.getYaw()))
                .set("pitch", () -> Value.string(Values.Entity.getPitch()))
        );

        ss.set("server", new ValueMap()
                .set("ip", () -> Value.string(Values.Server.getIp()))
                .set("name", () -> Value.string(Values.Server.getName()))
        );
    }

    public static Script compile(String source) {
        Parser.Result result = Parser.parse(source);

        if (result.hasErrors()) {
            return null;
        }
        return Compiler.compile(result);
    }

    public Section runSection(Script script, StringBuilder sb) {
        try {
            return ss.run(script, sb);
        } catch (StarscriptError e) {
            return null;
        }
    }

    public String run(Script script, StringBuilder sb) {
        try {
            Section section = runSection(script,sb);
            return section == null ? "" : section.toString();
        } catch (Exception e) {
            return "StarScript error";
        }
    }

    public String run(Script script) {
        return run(script, new StringBuilder());
    }
}
