package net.coordinate.keeper.helpers;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Coordinates;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class CoordinatesHelper {
    public static int displayCoordinates(CommandContext<ServerCommandSource> context, String playerName, Coordinates coordinates, String type) throws CommandSyntaxException {
        if (coordinates == null) {
            MutableText errorString = new LiteralText(playerName + " has not set up their " + type + " yet").formatted(Formatting.RED);
            context.getSource().getPlayer().sendMessage(errorString.formatted(Formatting.ITALIC), false);
            return 0;
        }
        MutableText locationString = new LiteralText(playerName + "'s " + type + " is at coordinates ").formatted(Formatting.GRAY);
        MutableText coordinatesString = new LiteralText(coordinates.toString()).formatted(Formatting.YELLOW);
        context.getSource().getPlayer().sendMessage(locationString.append(coordinatesString).formatted(Formatting.ITALIC), false);
        return 1;
    }

    public enum Category {
        NETHER("nether_portals"),
        HOME("home"),
        SHOPPING("shops"),
        BIOME("biomes"),
        SPAWNER("spawners"),
        OTHER("other");

        private String fileName;

        Category(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
