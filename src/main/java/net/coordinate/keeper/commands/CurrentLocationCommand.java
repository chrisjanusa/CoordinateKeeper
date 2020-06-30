package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.helpers.MessageHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public final class CurrentLocationCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity user = context.getSource().getPlayer();
        MessageHelper.sendSpecialInfo(context, user.getEntityName() + " is at coordinates ", "X: " + user.getBlockPos().getX() + " Y: " + user.getBlockPos().getY() + " Z: " + user.getBlockPos().getZ());
        return 1;
    }
}