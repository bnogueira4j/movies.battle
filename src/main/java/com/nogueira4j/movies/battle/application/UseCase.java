package com.nogueira4j.movies.battle.application;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN in);
}
