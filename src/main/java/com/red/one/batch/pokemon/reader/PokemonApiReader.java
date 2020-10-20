package com.red.one.batch.pokemon.reader;

import com.red.one.batch.pokemon.reader.dto.PokeApiNamedApiResource;
import com.red.one.batch.pokemon.reader.dto.PokeApiPokemon;
import com.red.one.batch.pokemon.reader.dto.PokeApiSpecies;
import com.red.one.batch.pokemon.reader.dto.PokemonDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokemonApiReader implements ItemReader<PokemonDetail> {

    @Value("${com.red.one.color.id:red}")
    private String colorId;

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonApiReader.class);

    private final PokemonExternalApiClient client;

    private PokemonDetail pokeApiSpecies;

    public PokemonApiReader(final PokemonExternalApiClient client) {
        this.client = client;
    }

    @Override
    public PokemonDetail read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        LOGGER.info("Reading the information of the next pokemon list");

        if (dataIsNotInitialized()) {
            pokeApiSpecies = fetchDataFromAPI(colorId);
        }
        return pokeApiSpecies;
    }

    private PokemonDetail fetchDataFromAPI(final String colorId) {
        LOGGER.debug("Fetching Pokemon data from an external API by using the url: {}", colorId);

        PokeApiSpecies response = client.getPokemonListByColorId(colorId);
        List<PokeApiNamedApiResource> pokemonSpecies = response.getPokemon_species();
        LOGGER.debug("Found {} pokemon", pokemonSpecies.size());

        List<PokeApiPokemon> characterList = new ArrayList<>();
        for (final PokeApiNamedApiResource pokemon : pokemonSpecies) {
            final String url = pokemon.getUrl();
            final String[] idExt = url.replaceAll("[^0-9]+", ";").split(";");
            PokeApiPokemon character = null;
            if (idExt.length > 0) {
                final String id = idExt[idExt.length - 1];
                character = client.getPokemonById(Long.valueOf(id));
                characterList.add(character);
            }
        }

        PokemonDetail detail = new PokemonDetail();
        detail.setId(response.getId());
        detail.setName(response.getName());
        detail.setPokemon_species(pokemonSpecies);
        detail.setPokemons(characterList);

        return detail;
    }

    private boolean dataIsNotInitialized() {
        return this.pokeApiSpecies == null;
    }
}
