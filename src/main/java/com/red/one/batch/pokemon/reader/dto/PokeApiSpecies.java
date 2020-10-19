package com.red.one.batch.pokemon.reader.dto;

import lombok.Data;

import java.util.List;

@Data
public class PokeApiSpecies {
    private Integer id;
    private String name;
    private List<PokeApiNamedApiResource> pokemon_species;
}
