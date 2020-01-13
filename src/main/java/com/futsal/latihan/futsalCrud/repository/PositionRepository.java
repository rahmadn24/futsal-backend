package com.futsal.latihan.futsalCrud.repository;

import com.futsal.latihan.futsalCrud.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PositionRepository extends JpaRepository <Position, String> {

//    @Query(nativeQuery= true, value= "SELECT s.* FROM position s WHERE s.position_code = ?1")
    Optional<Position> findByPositionCode(String positionCode);
}
