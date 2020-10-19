package com.red.one.batch.pokemon.reader;

import com.red.one.batch.pokemon.reader.dto.PokeApiNamedApiResource;
import com.red.one.batch.pokemon.reader.dto.PokeApiSpecies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PokemonApiReader implements ItemReader<PokeApiSpecies> {

    @Value("${com.red.one.color.id:red}")
    private String colorId;

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonApiReader.class);

    private final PokemonExternalApiClient client;

    private PokeApiSpecies pokeApiSpecies;

    public PokemonApiReader(final PokemonExternalApiClient client) {
        this.client = client;
    }

    @Override
    public PokeApiSpecies read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        LOGGER.info("Reading the information of the next pokemon list");

        if (dataIsNotInitialized()) {
            pokeApiSpecies = fetchDataFromAPI(colorId);
        }
        return pokeApiSpecies;
    }

    private PokeApiSpecies fetchDataFromAPI(final String colorId) {
        LOGGER.debug("Fetching Pokemon data from an external API by using the url: {}", colorId);

        PokeApiSpecies response = client.getPokemonListByColorId(colorId);
        List<PokeApiNamedApiResource> pokemonSpecies = response.getPokemon_species();
        LOGGER.debug("Found {} pokemon", pokemonSpecies.size());

        return response;
    }

    private boolean dataIsNotInitialized() {
        return this.pokeApiSpecies == null;
    }
}
