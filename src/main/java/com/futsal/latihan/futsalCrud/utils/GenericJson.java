package com.futsal.latihan.futsalCrud.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericJson {

    private static final Logger logger = LoggerFactory.getLogger(GenericJson.class);

    public JSONObject getGenericJson(String stringToParse) {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(stringToParse);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return json;
    }
}