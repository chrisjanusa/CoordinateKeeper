package net.coordinate.keeper.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.coordinate.keeper.data.Config;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class IdSuggestions implements SuggestionProvider<ServerCommandSource> {

    private Config config;

    public IdSuggestions(Config config){
        this.config = config;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        for (String name : config.getIds()) {
            builder.suggest(name);
        }
        return builder.buildFuture();
    }
}
