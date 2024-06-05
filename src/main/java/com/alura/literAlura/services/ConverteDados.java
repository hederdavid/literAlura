package com.alura.literAlura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {

    private final ObjectMapper MAPPER = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return MAPPER.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
