package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.data.NameConfig;
import net.coordinate.keeper.helpers.CoordinatesHelper;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public final class HomeCommand implements Command<ServerCommandSource> {
    public static final String homeArgument = "player";

    private final String type;
    private Config config;
    private NameConfig nameConfig;

    public HomeCommand(NameConfig nameConfig, Config config, String type){
        this.type = type;
        this.nameConfig = nameConfig;
        this.config = config;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String playerName = getString(context, homeArgument);
        Coordinates coordinates;
        if (nameConfig.isUsernameStored(playerName)) {
            coordinates = config.getCoordinates(playerName);
        } else {
            coordinates = config.getCoordinates(nameConfig.getUsername(playerName));
        }
        return CoordinatesHelper.displayCoordinates(context, playerName, coordinates, type);
    }
}