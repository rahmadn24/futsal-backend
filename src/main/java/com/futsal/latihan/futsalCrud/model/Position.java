package com.futsal.latihan.futsalCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="position")
//@NamedQuery(name="Position.findAll", query="SELECT p FROM position p")
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_position", unique = true, nullable = false)
    private Long idPosition;

    @Column(length=3, name = "position_code", unique = true)
    private String positionCode;

    @Column(length=20, name = "position_name")
    private String positionName;

    //bi-directional many-to-one association to Book
    @OneToMany(mappedBy="position")
    @JsonIgnoreProperties({"position"})
    private List<Player> player;

    public Long getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(Long idPosition) {
        this.idPosition = idPosition;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public List<Player> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }
}
