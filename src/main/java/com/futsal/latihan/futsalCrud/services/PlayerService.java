package com.futsal.latihan.futsalCrud.services;

import com.futsal.latihan.futsalCrud.model.Player;
import com.futsal.latihan.futsalCrud.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepo;

    public void inputData(Player player) {
        playerRepo.save(player);
    }

    public List<Player> getDataAll() {
        return playerRepo.findAll();
    }

    public Player getDataById(Long id) {
        Optional<Player> opt = playerRepo.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }else {
            return null;
        }
    }

    public List<Player> getDataAllByIdPosition(Long id) {
        return playerRepo.findByPositionCode(id);
    }

    public List<Player> getDataAllByIdTeam(Long id) {
        return playerRepo.findDataByTeam(id);
    }

    public List<Player> getDataByTeamAndPlayer(Long teamCode, String playerName, Long positionCode) {
        return playerRepo.findByTeamAndName(teamCode, playerName, positionCode);
    }

    public List<Player> getDataByPlayerName(String playerName) {
        return playerRepo.findByPlayerName(playerName);
    }

    public Optional<Player> getDataAllById(Long id) {
        return playerRepo.findById(id);
    }

    public void deleteData(Player player) {
        playerRepo.delete(player);
    }
}
