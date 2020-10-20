package com.red.one.batch.pokemon.reader.dto;

import lombok.Data;

import java.util.List;

@Data
public class PokemonDetail {
    private Integer id;
    private String name;
    private List<PokeApiNamedApiResource> pokemon_species;
    private List<PokeApiPokemon> pokemons;
}
