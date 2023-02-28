package com.nogueira4j.movies.battle.application.player.login;

public record LoginPlayerOutput(String authorization){
    public static LoginPlayerOutput from(
            final String authorization
    ) {
        return new LoginPlayerOutput(authorization);
    }
}
