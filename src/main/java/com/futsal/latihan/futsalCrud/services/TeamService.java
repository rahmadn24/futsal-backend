package com.futsal.latihan.futsalCrud.services;

import com.futsal.latihan.futsalCrud.model.Team;
import com.futsal.latihan.futsalCrud.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepo;

    public void inputData(Team team) {
        teamRepo.save(team);
    }

    public List<Team> getDataAll() {
        return teamRepo.findAll();
    }

    public Team getDataById(String team_code) {
        Optional<Team> opt = teamRepo.findDataByCode(team_code);
        if (opt.isPresent()) {
            return opt.get();
        }else {
            return null;
        }
    }

    public Optional<Team> getDataAllById(String team_code) {
        return teamRepo.findById(team_code);
    }

    public void deleteData(Team team) {
        teamRepo.delete(team);
    }
}
