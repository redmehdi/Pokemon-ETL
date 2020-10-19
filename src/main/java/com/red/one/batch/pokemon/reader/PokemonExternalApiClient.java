package com.red.one.batch.pokemon.reader;

import com.red.one.batch.pokemon.reader.dto.PokeApiPokemon;
import com.red.one.batch.pokemon.reader.dto.PokeApiSpecies;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "pokeApi",
        url = "https://pokeapi.co/api/v2/")
public interface PokemonExternalApiClient {

    /**
     * Retrieve all pokemon list by color id or color name highly recommended to use id than word of color
     *
     * @param id identifier of pokemon
     * @return pokemon's details
     */
    @GetMapping("/pokemon/{id}")
    PokeApiPokemon getPokemonById(@PathVariable("id") Long id);

    /**
     * Retrieve all pokemon list by color id or color name highly recommended to use id than word of color
     *
     * @param idColor identifier of color
     * @return list of pokemon
     */
    @GetMapping("/pokemon-color/{idColor}")
    PokeApiSpecies getPokemonListByColorId(@PathVariable("idColor") String idColor);
}
