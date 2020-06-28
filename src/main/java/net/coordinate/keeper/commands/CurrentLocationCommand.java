package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public final class CurrentLocationCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity user = context.getSource().getPlayer();
        LiteralText locationString = new LiteralText(user.getEntityName() + " is at coordinates ");
        MutableText coordinatesString = new LiteralText("X: " + user.getBlockPos().getX() + " Y: " + user.getBlockPos().getY() + " Z: " + user.getBlockPos().getZ()).formatted(Formatting.YELLOW);
        context.getSource().getMinecraftServer().getPlayerManager().broadcastChatMessage(locationString.append(coordinatesString), MessageType.SYSTEM, user.getUuid());

        return 1;
    }
}