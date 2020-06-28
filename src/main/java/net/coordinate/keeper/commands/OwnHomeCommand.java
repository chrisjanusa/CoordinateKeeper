package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.helpers.HomeHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


public final class OwnHomeCommand implements Command<ServerCommandSource> {
    public static final String homeArgument = "player";

    private Config config;

    public OwnHomeCommand(Config config) {
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        String playerName = player.getEntityName();
        Coordinates coordinates = config.getCoordinates(playerName);
        return HomeHelper.displayHomeCoordinates(context, playerName, coordinates);
    }
}