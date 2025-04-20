package br.com.argonaut.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T getData(String json, Class<T> classToConvert) {
        try {
            return mapper.readValue(json,classToConvert);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
