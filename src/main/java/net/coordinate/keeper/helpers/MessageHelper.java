package net.coordinate.keeper.helpers;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class MessageHelper {
    public static void sendInfo(CommandContext<ServerCommandSource> context, String string) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(new LiteralText(string).formatted(Formatting.GRAY).formatted(Formatting.ITALIC), false);
    }

    public static void sendError(CommandContext<ServerCommandSource> context, String error) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(new LiteralText(error).formatted(Formatting.RED).formatted(Formatting.ITALIC), false);
    }

    public static void sendSpecialInfo(CommandContext<ServerCommandSource> context, String preHighlighted, String highlighted) throws CommandSyntaxException {
        MutableText preHighlightedText = new LiteralText(preHighlighted).formatted(Formatting.GRAY);
        MutableText highlightedText = new LiteralText(highlighted).formatted(Formatting.YELLOW);
        context.getSource().getPlayer().sendMessage(preHighlightedText.append(highlightedText).formatted(Formatting.ITALIC), false);
    }
}
