package com.futsal.latihan.futsalCrud.controller;

import com.futsal.latihan.futsalCrud.dto.PlayerDto;
import com.futsal.latihan.futsalCrud.model.Player;
import com.futsal.latihan.futsalCrud.model.Position;
import com.futsal.latihan.futsalCrud.model.Team;
import com.futsal.latihan.futsalCrud.services.PlayerService;
import com.futsal.latihan.futsalCrud.services.PositionService;
import com.futsal.latihan.futsalCrud.services.TeamService;
import com.futsal.latihan.futsalCrud.utils.Constant;
import com.futsal.latihan.futsalCrud.utils.GenericJson;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    PositionService positionService;

    @Autowired
    TeamService teamService;

    private ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger  = LoggerFactory.getLogger(PlayerController.class);

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getData() {
        HashMap<String, Object> data = new HashMap<>();
        try {
            List<Player> list = playerService.getDataAll();
            data.put(Constant.CONST_DATA, list);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/{idPlayer}")
    public ResponseEntity<HashMap<String, Object>> getDataById(@PathVariable(value="idPlayer") Long idPlayer) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Player list = playerService.getDataById(idPlayer);
            data.put(Constant.CONST_DATA, list);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/team")
    public ResponseEntity<HashMap<String, Object>> getPlayerByTeamAndName(@RequestParam String teamCode, @RequestParam String playerName, @RequestParam String positionCode) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Team team = teamService.getDataById(teamCode);
            Position position = positionService.getDataById(positionCode);
            List<Player> list = playerService.getDataByTeamAndPlayer(team.getIdTeam(), playerName+'%', position.getIdPosition());
            data.put(Constant.CONST_DATA, list);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, Object>> insertData(@RequestBody PlayerDto dto) {
        HashMap<String, Object> data = new HashMap<>();
        Player player = modelMapper.map(dto, Player.class);
        try {
            System.out.print(dto.getPosition());
            System.out.print(dto.getTeam());
            player.setPosition(positionService.getDataById(dto.getPosition()));
            player.setTeam(teamService.getDataById(dto.getTeam()));
            playerService.inputData(player);
            data.put(Constant.CONST_DATA, player);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HashMap<String, Object>> updateData(@RequestBody PlayerDto dto) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Player playerExist = playerService.getDataById(dto.getIdPlayer());
            playerExist.setPlayerName(dto.getPlayerName() != null ? dto.getPlayerName() : playerExist.getPlayerName());
            playerExist.setJerseyNo(dto.getJerseyNo() != null ? dto.getJerseyNo() : playerExist.getJerseyNo());
            playerExist.setAge(dto.getAge() != null ? dto.getAge() : playerExist.getAge());
            playerExist.setTeam(dto.getTeam() != null ? teamService.getDataById(dto.getTeam()) : playerExist.getTeam());
            playerExist.setPosition(dto.getPosition() != null ? positionService.getDataById(dto.getPosition()) : playerExist.getPosition());
            playerService.inputData(playerExist);
            data.put(Constant.CONST_DATA, playerExist);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, Object>> deleteData(@RequestParam Long id) {
        HashMap<String, Object> data = new HashMap<>();
        GenericJson genericJson = new GenericJson();
//        JSONObject jsonReq		= genericJson.getGenericJson(request);
//        Long id	= Long.parseLong(jsonReq.get("idPlayer").toString());
        try {
            Player player = playerService.getDataById(id);
            playerService.deleteData(player);
            data.put(Constant.CONST_DATA, player);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
