package com.red.one.batch.pokemon.process;

import com.red.one.batch.pokemon.reader.dto.PokemonDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PokemonItemProcessor implements ItemProcessor<PokemonDetail, PokemonDetail> {

    private static final Logger log = LoggerFactory.getLogger(PokemonItemProcessor.class);


    @Override
    public PokemonDetail process(final PokemonDetail apiSpecies) throws Exception {
        return apiSpecies;
    }

}
