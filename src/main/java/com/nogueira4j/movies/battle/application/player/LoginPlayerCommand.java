package com.nogueira4j.movies.battle.application.player;

public record LoginPlayerCommand(String username, String password){
    public static LoginPlayerCommand with(
            final String username,
            final String password
    ) {
        return new LoginPlayerCommand(username, password);
    }
}
