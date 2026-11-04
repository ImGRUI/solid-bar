package me.imgrui.config.parser;

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
                .set("health", () -> Value.number(Values.Player.getHealth()))
                .set("health_max", () -> Value.number(Values.Player.getMaxHealth()))
                .set("health_percent", () -> Value.number(Values.Player.getHealthPercent()))
                .set("armor", () -> Value.number(Values.Player.getArmor()))
                .set("direction", () -> Value.string(Values.Player.getDirection()))
                .set("hunger", () -> Value.number(Values.Player.getHunger()))
                .set("ping", () -> Value.number(Values.Player.getPing()))
                .set("pos", new ValueMap()
                        .set("x", () -> Value.number(Values.Player.getX()))
                        .set("y", () -> Value.number(Values.Player.getY()))
                        .set("z", () -> Value.number(Values.Player.getZ()))
                )
        );

        ss.set("world", new ValueMap()
                .set("dimension", () -> Value.string(Values.World.getDimension()))
                .set("time", () -> Value.string(Values.World.getTime()))
        );

        ss.set("entity", new ValueMap()
                .set("name", () -> Value.string(Values.Entity.getName()))
                .set("health", () -> Value.number(Values.Entity.getHealth()))
                .set("health_max", () -> Value.number(Values.Entity.getMaxHealth()))
                .set("health_percent", () -> Value.number(Values.Entity.getHealthPercent()))
                .set("pos", new ValueMap()
                        .set("x", () -> Value.number(Values.Entity.getX()))
                        .set("y", () -> Value.number(Values.Entity.getY()))
                        .set("z", () -> Value.number(Values.Entity.getZ()))
                )
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
