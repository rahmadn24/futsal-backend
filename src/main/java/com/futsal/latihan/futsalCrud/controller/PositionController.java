package com.futsal.latihan.futsalCrud.controller;

import com.futsal.latihan.futsalCrud.dto.PositionDto;
import com.futsal.latihan.futsalCrud.model.Player;
import com.futsal.latihan.futsalCrud.model.Position;
import com.futsal.latihan.futsalCrud.services.PlayerService;
import com.futsal.latihan.futsalCrud.services.PositionService;
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
@RequestMapping("/position")
public class PositionController {

    @Autowired
    PositionService positionService;

    @Autowired
    PlayerService playerService;

    private ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger  = LoggerFactory.getLogger(PositionController.class);

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getData() {
        HashMap<String, Object> data = new HashMap<>();
        try {
            List<Position> list = positionService.getDataAll();
            data.put(Constant.CONST_DATA, list);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/{positionCode}")
    public ResponseEntity<HashMap<String, Object>> getDataByCode(@PathVariable(value="positionCode") String positionCode) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Position list = positionService.getDataById(positionCode);
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
    public ResponseEntity<HashMap<String, Object>> insertData(@RequestBody PositionDto dto) {
        HashMap<String, Object> data = new HashMap<>();
        Position position = modelMapper.map(dto, Position.class);
        try {
            positionService.inputData(position);
            data.put(Constant.CONST_DATA, position);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HashMap<String, Object>> updateData(@RequestBody PositionDto dto) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            Position positionExist = positionService.getDataById(dto.getPositionCode());
            positionExist.setPositionName(dto.getPositionName() != null ? dto.getPositionName() : positionExist.getPositionName());
            positionService.inputData(positionExist);
            data.put(Constant.CONST_DATA, positionExist);
            data.put(Constant.CONST_STATUS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.format(Constant.CONST_ERROR, e.getMessage()));
            data.put(Constant.CONST_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
            data.put(Constant.CONST_MSG, e.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, Object>> deleteData(@RequestParam String positionCode) {
        HashMap<String, Object> data = new HashMap<>();
        GenericJson genericJson = new GenericJson();
//        JSONObject jsonReq		= genericJson.getGenericJson(request);
//        String id	= jsonReq.get("positionCode").toString();
        try {
            Position position = positionService.getDataById(positionCode);
            List<Player> player = playerService.getDataAllByIdPosition(position.getIdPosition());
            if (player != null) {
                for (Player ply : player) {
                    ply.setPosition(null);
                    playerService.inputData(ply);
                }
            }
            positionService.deleteData(position);
            List<Position> list = positionService.getDataAll();
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
