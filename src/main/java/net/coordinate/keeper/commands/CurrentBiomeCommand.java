package net.coordinate.keeper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.coordinate.keeper.helpers.MessageHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public final class CurrentBiomeCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity user = context.getSource().getPlayer();
        String biome = parseBiomeString(context.getSource().getWorld().getBiome(user.getBlockPos()).toString());
        MessageHelper.sendSpecialInfo(context, "You are in the", biome);
        return 1;
    }

    private String parseBiomeString(String biome) {
        String onlyBiome = biome.substring("net.minecraft.world.biome.".length(), biome.indexOf('@'));
        StringBuilder spacedOut = new StringBuilder();
        for ( char letter: onlyBiome.toCharArray()) {
            if (Character.isUpperCase(letter)) {
                spacedOut.append(' ');
            }
            spacedOut.append(letter);
        }
        return spacedOut.toString();
    }
}