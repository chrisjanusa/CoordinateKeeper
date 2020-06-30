package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.helpers.MessageHelper;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public final class ReadCoordinatesCommand implements Command<ServerCommandSource> {
    public static final String ID_ARGUMENT = "id";

    private Config config;

    public ReadCoordinatesCommand(Config config){
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String id = getString(context, ID_ARGUMENT);
        Coordinates coordinates = config.getCoordinates(id);
        if (coordinates == null) {
            MessageHelper.sendError(context, id + " does not exist yet");
            return 0;
        }
        MessageHelper.sendSpecialInfo(context, id + " is at coordinates ", coordinates.toString());
        return 1;
    }
}