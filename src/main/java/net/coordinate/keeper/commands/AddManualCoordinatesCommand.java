package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.helpers.MessageHelper;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;

public final class AddManualCoordinatesCommand implements Command<ServerCommandSource> {
    public static final String ID_ARGUMENT = "id";
    public static final String X_ARGUMENT = "x";
    public static final String Y_ARGUMENT = "y";
    public static final String Z_ARGUMENT = "z";

    private Config config;

    public AddManualCoordinatesCommand(Config config){
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String id = getString(context, ID_ARGUMENT);
        if (config.containsId(id)) {
            MessageHelper.sendError(context, id + " already exists so please use /coordinates replace <id> if you wish to replace or try a different id");
            return 0;
        }
        int x = getInteger(context, X_ARGUMENT);
        int y = getInteger(context, Y_ARGUMENT);
        int z = getInteger(context, Z_ARGUMENT);
        Coordinates coordinates = new Coordinates(x, y, z);
        config.addCoordinates(id, coordinates);
        MessageHelper.sendSpecialInfo(context, id + " was set to ", coordinates.toString());
        return 1;
    }
}