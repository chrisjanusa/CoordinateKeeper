package net.coordinate.keeper;

import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.coordinate.keeper.commands.*;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.NameConfig;
import net.coordinate.keeper.helpers.CoordinatesHelper.Category;
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

            Config homeConfig = new Config(Category.HOME);
            LiteralCommandNode<ServerCommandSource> setHome = CommandManager
                    .literal("setHome")
                    .executes(new SetHomeCommand(homeConfig, nameConfig, "home"))
                    .build();

            LiteralCommandNode<ServerCommandSource> home = CommandManager
                    .literal("home")
                    .build();

            CommandNode<ServerCommandSource> ownHome = CommandManager
                    .literal("myhome")
                    .executes(new OwnHomeCommand(homeConfig, "home"))
                    .build();

            ArgumentCommandNode<ServerCommandSource, String> homePlayer = CommandManager
                    .argument(HomeCommand.homeArgument, word())
                    .suggests(new NameSuggestions(nameConfig))
                    .executes(new HomeCommand(nameConfig, homeConfig, "home"))
                    .build();

            Config netherConfig = new Config(Category.NETHER);
            LiteralCommandNode<ServerCommandSource> setNether = CommandManager
                    .literal("setNether")
                    .executes(new SetHomeCommand(netherConfig, nameConfig,"nether portal"))
                    .build();

            LiteralCommandNode<ServerCommandSource> nether = CommandManager
                    .literal("nether")
                    .build();

            CommandNode<ServerCommandSource> ownNether = CommandManager
                    .literal("mynether")
                    .executes(new OwnHomeCommand(netherConfig, "nether portal"))
                    .build();

            ArgumentCommandNode<ServerCommandSource, String> netherPlayer = CommandManager
                    .argument(HomeCommand.homeArgument, word())
                    .suggests(new NameSuggestions(nameConfig))
                    .executes(new HomeCommand(nameConfig, netherConfig, "nether portal"))
                    .build();

            // Curr Coordinates
            dispatcher.getRoot().addChild(currCoordinates);

            // Curr Biome
            dispatcher.getRoot().addChild(currBiome);

            // Player Homes
            dispatcher.getRoot().addChild(setHome);
            dispatcher.getRoot().addChild(ownHome);
            dispatcher.getRoot().addChild(home);
            home.addChild(homePlayer);

            // Player Nether Portals
            dispatcher.getRoot().addChild(setNether);
            dispatcher.getRoot().addChild(ownNether);
            dispatcher.getRoot().addChild(nether);
            nether.addChild(netherPlayer);

            // Set Name
            dispatcher.getRoot().addChild(setName);
            setName.addChild(name);
        });
    }
}
