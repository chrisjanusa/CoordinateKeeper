package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.helpers.HomeHelper;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;

public final class HomeCommand implements Command<ServerCommandSource> {
    public static final String homeArgument = "player";

    private Config config;

    public HomeCommand(Config config){
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        EntitySelector player = context.getArgument(homeArgument, EntitySelector.class);
        String playerName = player.getPlayer(context.getSource()).getEntityName();
        Coordinates coordinates = config.getCoordinates(playerName);
        return HomeHelper.displayHomeCoordinates(context, playerName, coordinates);
    }
}