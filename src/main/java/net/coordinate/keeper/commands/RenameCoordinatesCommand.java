package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.helpers.MessageHelper;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public final class RenameCoordinatesCommand implements Command<ServerCommandSource> {
    public static final String OLD_ID_ARGUMENT = "old_id";
    public static final String NEW_ID_ARGUMENT = "new_id";

    private Config config;

    public RenameCoordinatesCommand(Config config){
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String oldId = getString(context, OLD_ID_ARGUMENT);
        String newId = getString(context, NEW_ID_ARGUMENT);
        if (!config.containsId(oldId)) {
            MessageHelper.sendError(context, oldId + " does not exist");
            return 0;
        }
        if (config.containsId(newId)) {
            MessageHelper.sendError(context, newId + " already exists");
            return 0;
        }
        Coordinates coordinates = config.getCoordinates(oldId);
        config.addCoordinates(newId, coordinates);
        config.removeCoordinates(oldId);
        MessageHelper.sendSpecialInfo(context, oldId + " was renamed to ", newId);
        return 1;
    }
}