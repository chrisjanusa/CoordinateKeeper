package net.coordinate.keeper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

final class CurrentBiomeCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity user = context.getSource().getPlayer();
        LiteralText locationString = new LiteralText("You are in the" + parseBiomeString(context.getSource().getWorld().getBiome(user.getBlockPos()).toString()));
        context.getSource().getPlayer().sendMessage(locationString.formatted(Formatting.GRAY).formatted(Formatting.ITALIC), false);

        return 1;
    }

    private String parseBiomeString(String biome) {
        String onlyBiome = biome.substring("net.minecraft.world.biome.".length(), biome.indexOf('@'));
        StringBuilder spacedOut = new StringBuilder();
        for ( char letter: onlyBiome.toCharArray()) {
            if (letter < 91) {
                spacedOut.append(' ');
            }
            spacedOut.append(letter);
        }
        return spacedOut.toString();
    }
}