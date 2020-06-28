package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.data.NameConfig;
import net.coordinate.keeper.helpers.HomeHelper;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;


public final class NameHomeCommand implements Command<ServerCommandSource> {
    public static final String nameArgument = "name";

    private Config config;
    private NameConfig nameConfig;

    public NameHomeCommand(NameConfig nameConfig, Config config){
        this.nameConfig = nameConfig;
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String playerName = getString(context, nameArgument);
        Coordinates coordinates = config.getCoordinates(nameConfig.getUsername(playerName));
        return HomeHelper.displayHomeCoordinates(context, playerName, coordinates);
    }
}