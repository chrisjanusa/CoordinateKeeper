package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.NameConfig;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public final class SetNameCommand implements Command<ServerCommandSource> {
    public static final String nameArgument = "name";

    private NameConfig config;

    public SetNameCommand(NameConfig config){
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity user = context.getSource().getPlayer();
        String name = getString(context, "name");
        config.addName(user.getEntityName(), getString(context, "name"));
        MutableText nameMessage = new LiteralText("Your name was set to " + name);
        context.getSource().getPlayer().sendMessage(nameMessage.formatted(Formatting.GRAY).formatted(Formatting.ITALIC), false);

        return 1;
    }
}