package net.coordinate.keeper.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.coordinate.keeper.data.NameConfig;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class NameSuggestions implements SuggestionProvider<ServerCommandSource> {

    private NameConfig config;

    public NameSuggestions(NameConfig config){
        this.config = config;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        for (String name : config.getNames()) {
            builder.suggest(name);
        }
        for (String name : config.getUserNames()) {
            builder.suggest(name);
        }
        return builder.buildFuture();
    }
}
