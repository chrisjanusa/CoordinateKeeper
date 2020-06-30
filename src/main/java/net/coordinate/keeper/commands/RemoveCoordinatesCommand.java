package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.helpers.MessageHelper;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public final class RemoveCoordinatesCommand implements Command<ServerCommandSource> {
    public static final String ID_ARGUMENT = "id";

    private Config config;

    public RemoveCoordinatesCommand(Config config){
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String id = getString(context, ID_ARGUMENT);
        config.removeCoordinates(id);
        MessageHelper.sendInfo(context, id + " was removed");
        return 1;
    }
}