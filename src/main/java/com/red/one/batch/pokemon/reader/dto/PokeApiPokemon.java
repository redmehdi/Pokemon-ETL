package com.red.one.batch.pokemon.reader.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PokeApiPokemon implements Serializable {
    private Integer id;
    private String name;
    private Integer base_experience;
    private Integer height;
    private Integer weight;
}
