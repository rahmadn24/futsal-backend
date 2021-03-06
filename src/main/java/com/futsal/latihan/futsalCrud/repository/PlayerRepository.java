package com.futsal.latihan.futsalCrud.repository;

import com.futsal.latihan.futsalCrud.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(nativeQuery= true, value= "SELECT s.* FROM player s WHERE s.position_code = ?1")
    List<Player> findByPositionCode(Long idPosition);

    @Query(nativeQuery= true, value= "SELECT s.* FROM player s WHERE s.team_code = ?1")
    List<Player> findDataByTeam(Long idTeam);

//    @Query(nativeQuery = true, value= "SELECT p.* from player p WHERE p.team_code = ?1 and p.player_name LIKE ?2 and p.position_code = ?3")
    @Query("SELECT p from Player p WHERE p.team.idTeam = ?1 and p.playerName LIKE ?2 and p.position.idPosition = ?3")
    List<Player> findByTeamAndName(Long idTeam, String playerName, Long idPosition);

    @Query(nativeQuery = true, value= "SELECT p.* from player p WHERE p.player_name LIKE ?2")
    List<Player> findByPlayerName(String playerName);

}
