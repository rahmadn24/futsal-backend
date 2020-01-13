package com.futsal.latihan.futsalCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="team")
//@NamedQuery(name="Team.findAll", query="SELECT t FROM team t")
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_team", unique = true, nullable = false)
    private Long idTeam;

    @Column(length=20, name = "team_name")
    private String teamName;

    @Column(length=30)
    private String location;

    @Column(length=20)
    private String stadium;

    @Column(length=3, name = "team_code")
    private String teamCode;

    //bi-directional many-to-one association to Book
    @OneToMany(mappedBy="team")
    @JsonIgnoreProperties({"team"})
    private List<Player> player;

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public List<Player> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }
}
