package com.futsal.latihan.futsalCrud.controller;

import com.futsal.latihan.futsalCrud.dto.TeamDto;
import com.futsal.latihan.futsalCrud.model.Player;
import com.futsal.latihan.futsalCrud.model.Team;
import com.futsal.latihan.futsalCrud.services.PlayerService;
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
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    private ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger  = LoggerFactory.getLogger(PlayerController.class);

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getData() {
        HashMap<String, Object> data = new HashMap<>();
        try {
            List<Team> list = teamService.getDataAll();
            data.put(Constant.CONST_DATA, list);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/{teamCode}")
    public ResponseEntity<HashMap<String, Object>> getDataByCode(@PathVariable(value="teamCode") String teamCode) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Team list = teamService.getDataById(teamCode);
            data.put(Constant.CONST_DATA, list);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/player")
    public ResponseEntity<HashMap<String, Object>> getPlayerByTeamAndName(@RequestParam String teamCode, @RequestParam String playerName) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Team list = teamService.getDataById(teamCode);
            List<Player> player = playerService.getDataByTeamAndPlayer(list.getIdTeam(), playerName+'%');
            list.setPlayer(player);
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
    public ResponseEntity<HashMap<String, Object>> insertData(@RequestBody TeamDto dto) {
        HashMap<String, Object> data = new HashMap<>();
        Team team = modelMapper.map(dto, Team.class);
        try {
            teamService.inputData(team);
            data.put(Constant.CONST_DATA, team);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HashMap<String, Object>> updateData(@RequestBody TeamDto dto) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Team teamExist = teamService.getDataById(dto.getTeamCode());
            teamExist.setTeamName(dto.getTeamName() != null ? dto.getTeamName() : teamExist.getTeamName());
            teamExist.setLocation(dto.getLocation() != null ? dto.getLocation() : teamExist.getLocation());
            teamExist.setStadium(dto.getStadium() != null ? dto.getStadium() : teamExist.getStadium());
            teamService.inputData(teamExist);
            data.put(Constant.CONST_DATA, teamExist);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, Object>> deleteData(@RequestParam String teamCode) {
        HashMap<String, Object> data = new HashMap<>();
        GenericJson genericJson = new GenericJson();
//        JSONObject jsonReq		= genericJson.getGenericJson(request);
//        String id	= jsonReq.get("teamCode").toString();
        try {
            Team team = teamService.getDataById(teamCode);
            List<Player> player = playerService.getDataAllByIdTeam(team.getIdTeam());
            if (player != null) {
                for (Player ply : player) {
                    ply.setTeam(null);
                    playerService.inputData(ply);
                }
            }
            teamService.deleteData(team);
            List<Team> list = teamService.getDataAll();
            data.put(Constant.CONST_DATA, list);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
