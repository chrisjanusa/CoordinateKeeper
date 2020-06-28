package net.coordinate.keeper;

import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.coordinate.keeper.commands.*;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.NameConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.word;

public class CoordinateKeeper implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralCommandNode<ServerCommandSource> currCoordinates = CommandManager
                    .literal("currLoc")
                    .executes(new CurrentLocationCommand())
                    .build();

            LiteralCommandNode<ServerCommandSource> currBiome = CommandManager
                    .literal("currBiome")
                    .executes(new CurrentBiomeCommand())
                    .build();

            LiteralCommandNode<ServerCommandSource> setName = CommandManager
                    .literal("setName")
                    .build();

            NameConfig nameConfig = new NameConfig();
            ArgumentCommandNode<ServerCommandSource, String> name = CommandManager
                    .argument(SetNameCommand.nameArgument, word())
                    .executes(new SetNameCommand(nameConfig))
                    .build();

            Config homeConfig = new Config(Config.Category.HOME);
            LiteralCommandNode<ServerCommandSource> setHome = CommandManager
                    .literal("setHome")
                    .executes(new SetHomeCommand(homeConfig))
                    .build();

            LiteralCommandNode<ServerCommandSource> home = CommandManager
                    .literal("home")
                    .build();

            CommandNode<ServerCommandSource> ownHome = CommandManager
                    .literal("home")
                    .executes(new OwnHomeCommand(homeConfig))
                    .build();

            ArgumentCommandNode<ServerCommandSource, EntitySelector> homePlayer = CommandManager
                    .argument(HomeCommand.homeArgument, EntityArgumentType.player())
                    .executes(new HomeCommand(homeConfig))
                    .build();

            ArgumentCommandNode<ServerCommandSource, String> homeName = CommandManager
                    .argument(NameHomeCommand.nameArgument, word())
                    .suggests(new NameSuggestions(nameConfig))
                    .executes(new NameHomeCommand(nameConfig, homeConfig))
                    .build();



            dispatcher.getRoot().addChild(currCoordinates);
            dispatcher.getRoot().addChild(currBiome);
            dispatcher.getRoot().addChild(setHome);
            dispatcher.getRoot().addChild(ownHome);
            dispatcher.getRoot().addChild(home);
            home.addChild(homePlayer);
            home.addChild(homeName);
            dispatcher.getRoot().addChild(setName);
            setName.addChild(name);
        });
    }
}
