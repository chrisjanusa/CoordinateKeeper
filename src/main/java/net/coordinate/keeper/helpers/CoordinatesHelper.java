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
            MessageHelper.sendError(context, playerName + " has not set up their " + type + " yet");
            return 0;
        }
        MessageHelper.sendSpecialInfo(context, playerName + "'s " + type + " is at coordinates ", coordinates.toString());
        return 1;
    }

    public enum Category {
        NETHER("nether_portals"),
        HOME("home"),
        GENERAL("general");

        private String fileName;

        Category(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
