package com.red.one.batch.pokemon.reader.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PokeApiNamedApiResource implements Serializable {
    private String name;
    private String url;
}
