package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.data.Config;
import net.coordinate.keeper.data.Coordinates;
import net.coordinate.keeper.data.NameConfig;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public final class SetHomeCommand implements Command<ServerCommandSource> {
    private final String type;
    private Config config;
    private NameConfig nameConfig;


    public SetHomeCommand(Config config, NameConfig nameConfig, String type){
        this.config = config;
        this.nameConfig = nameConfig;
        this.type = type;
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity user = context.getSource().getPlayer();
        BlockPos block = user.getBlockPos();
        Coordinates coordinates = new Coordinates(block.getX(), block.getY(), block.getZ());
        config.addCoordinates(user.getEntityName(), coordinates);
        MutableText coordinatesString = new LiteralText("Your " + type + " was set to " + coordinates.toString());
        context.getSource().getPlayer().sendMessage(coordinatesString.formatted(Formatting.GRAY).formatted(Formatting.ITALIC), false);
        if (!nameConfig.isUsernameStored(user.getEntityName())) {
            nameConfig.addName(user.getEntityName(), "");
        }
        return 1;
    }
}