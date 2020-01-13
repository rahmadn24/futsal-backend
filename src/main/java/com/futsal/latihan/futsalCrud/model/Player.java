package com.futsal.latihan.futsalCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="player")
//@NamedQuery(name="Player.findAll", query="SELECT p FROM player p")
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_player", unique = true, nullable = false)
    private Long idPlayer;

    @Column(length=30, name = "player_name")
    private String playerName;

    @Column(name="jersey_no")
    private Integer jerseyNo;

    @Column
    private Integer age;

    //bi-directional many-to-one association to Position
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="position_code", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "player"})
    private Position position;

    //bi-directional many-to-one association to Team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_code", nullable=true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","player"})
    private Team team;

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getJerseyNo() {
        return jerseyNo;
    }

    public void setJerseyNo(Integer jerseyNo) {
        this.jerseyNo = jerseyNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
