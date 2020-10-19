package com.red.one.batch.pokemon.process;

import com.red.one.batch.pokemon.writer.PokeSpecies;
import com.red.one.batch.pokemon.reader.dto.PokeApiNamedApiResource;
import com.red.one.batch.pokemon.reader.dto.PokeApiSpecies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class PokemonItemProcessor implements ItemProcessor<PokeApiSpecies, List<PokeSpecies>> {

    private static final Logger log = LoggerFactory.getLogger(PokemonItemProcessor.class);


    @Override
    public List<PokeSpecies> process(final PokeApiSpecies apiSpecies) throws Exception {
        final Integer id = apiSpecies.getId();
        final String name = apiSpecies.getName();
        final List<PokeApiNamedApiResource> pokemonSpecies = apiSpecies.getPokemon_species();
        final List<PokeSpecies> transformedPokemonsData = new ArrayList<>();

        for (final PokeApiNamedApiResource pokemonApi : pokemonSpecies) {
			final String url = pokemonApi.getUrl();
			PokeSpecies pokemon = new PokeSpecies();
			pokemon.setColor(name);
			pokemon.setId(id);
			pokemon.setName(pokemonApi.getName());
			pokemon.setUrl(url);
            final String[] idExt = url.replaceAll("[^0-9]+", ";").split(";");
            if (idExt.length >0){
                pokemon.setIdExt(idExt[idExt.length-1]);
            }
            transformedPokemonsData.add(pokemon);
        }
        log.info("Converting (" + apiSpecies + ") into (" + transformedPokemonsData + ")");

        return transformedPokemonsData;
    }

}
