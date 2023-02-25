package com.nogueira4j.movies.battle.infrastructure.player.persistence;

import com.nogueira4j.movies.battle.domain.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "Player")
@Table(name = "player")
public class PlayerJpaEntity {

    @Id
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    public PlayerJpaEntity() {

    }

    private PlayerJpaEntity(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static PlayerJpaEntity from(final Player player) {
        return new PlayerJpaEntity(
                player.id(),
                player.username(),
                player.password()
        );
    }
    public Player toAggregate() {
        return Player.with(
                getId(),
                getUsername(),
                getPassword()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}





















