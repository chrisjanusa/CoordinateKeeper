package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.helpers.MessageHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public final class AddCoordinatesCommand implements Command<ServerCommandSource> {
    public static final String ID_ARGUMENT = "id";

    private Config config;

    public AddCoordinatesCommand(Config config) {
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String id = getString(context, ID_ARGUMENT);
        if (config.containsId(id)) {
            MessageHelper.sendError(context, id + " already exists so please use /coordinates replace <id> if you wish to replace or try a different id");
            return 0;
        }
        ServerPlayerEntity user = context.getSource().getPlayer();
        BlockPos block = user.getBlockPos();
        Coordinates coordinates = new Coordinates(block.getX(), block.getY(), block.getZ());
        config.addCoordinates(id, coordinates);
        MessageHelper.sendSpecialInfo(context, id + " was set to ", coordinates.toString());
        return 1;
    }
}