package com.conti.scoreboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "players",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "player1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matchesAsPlayer1 = new ArrayList<>();

    @OneToMany(mappedBy = "player2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matchesAsPlayer2 = new ArrayList<>();

    @OneToMany(mappedBy = "winner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> wonMatches = new ArrayList<>();

    // Конструкторы
    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }
}
