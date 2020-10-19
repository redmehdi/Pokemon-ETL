package com.red.one.batch.pokemon.reader.exception;

import com.red.one.batch.pokemon.reader.dto.PokeApiPokemon;
import com.red.one.batch.pokemon.reader.dto.PokeApiSpecies;

public class JSONPlaceHolderFallback  {

    public PokeApiPokemon getPokemon(Long id) {
        return null;
    }

    public PokeApiSpecies getPokemonListByColorId(String idColor) {
        return null;
    }
}
