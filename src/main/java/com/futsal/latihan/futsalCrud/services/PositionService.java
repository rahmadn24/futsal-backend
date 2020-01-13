package com.futsal.latihan.futsalCrud.services;

import com.futsal.latihan.futsalCrud.model.Position;
import com.futsal.latihan.futsalCrud.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {

    @Autowired
    PositionRepository positionRepo;

    public void inputData(Position position) {
        positionRepo.save(position);
    }

    public List<Position> getDataAll() {
        return positionRepo.findAll();
    }

    public Position getDataById(String position_code) {
        Optional<Position> opt = positionRepo.findByPositionCode(position_code);
        if (opt.isPresent()) {
            return opt.get();
        }else {
            return null;
        }
    }

    public Optional<Position> getDataAllById(String position_code) {
        return positionRepo.findById(position_code);
    }

    public void deleteData(Position position) {
        positionRepo.delete(position);
    }
}
