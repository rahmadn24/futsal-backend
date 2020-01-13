package com.futsal.latihan.futsalCrud.repository;

import com.futsal.latihan.futsalCrud.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeamRepository extends JpaRepository <Team, String> {

    @Query(nativeQuery= true, value= "SELECT s.* FROM team s WHERE s.team_code = ?1")
    Optional<Team> findDataByCode(String teamCode);

}
