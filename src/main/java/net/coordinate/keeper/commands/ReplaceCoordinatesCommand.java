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

public final class ReplaceCoordinatesCommand implements Command<ServerCommandSource> {
    public static final String ID_ARGUMENT = "id";

    private Config config;

    public ReplaceCoordinatesCommand(Config config){
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String id = getString(context, ID_ARGUMENT);
        ServerPlayerEntity user = context.getSource().getPlayer();
        BlockPos block = user.getBlockPos();
        Coordinates coordinates = new Coordinates(block.getX(), block.getY(), block.getZ());
        config.addCoordinates(id, coordinates);
        MessageHelper.sendSpecialInfo(context, id + " was replaced with ", coordinates.toString());
        return 1;
    }
}