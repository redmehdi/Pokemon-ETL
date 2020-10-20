package com.red.one.batch.pokemon.writer;

import com.red.one.batch.pokemon.reader.dto.PokeApiNamedApiResource;
import com.red.one.batch.pokemon.reader.dto.PokeApiPokemon;
import com.red.one.batch.pokemon.reader.dto.PokemonDetail;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class PokemonDBWriter implements ItemWriter<PokemonDetail>{

    @Autowired
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;


    @Override
    public void write(List<? extends PokemonDetail> items) throws Exception {
        for (PokemonDetail item1 : items) {

            final String SQL_INSERT_INTO_RESOURCE = "insert into poke_species (created_by, created_date, modified_by, modified_date, color, id_ext, name, url ) " +
                    "values ('POKE_ETL', CURRENT_TIMESTAMP(), 'POKE_ETL', CURRENT_TIMESTAMP(), 'red', :idExt, :name, :url)";

            final List<MapSqlParameterSource> params = new ArrayList<>();
            for (PokeApiNamedApiResource registration : item1.getPokemon_species()) {
                MapSqlParameterSource param = new MapSqlParameterSource();

                param.addValue("name", item1.getName());
                param.addValue("idExt", item1.getId());
                param.addValue("color", registration.getName());
                param.addValue("url", registration.getUrl());

                params.add(param);
            }
            executeInsert(SQL_INSERT_INTO_RESOURCE, params);

            final String SQL_INSERT_INTO_POKEMON = "insert into poke_character (created_by, created_date, modified_by, " +
                    "modified_date, base_experience, height, name, species_id, weight) values ('POKE_ETL', CURRENT_TIMESTAMP()," +
                    " 'POKE_ETL', CURRENT_TIMESTAMP(), :baseExperience, :height, :name, " +
                    "(SELECT id from poke_species WHERE name= :name), :weight ) ";
            for (PokeApiPokemon item2 : item1.getPokemons()) {
                MapSqlParameterSource param = new MapSqlParameterSource();

                param.addValue("baseExperience", item1.getName());
                param.addValue("height", item1.getId());
                param.addValue("name", item2.getName());
                param.addValue("weight", item2.getBase_experience());

                params.add(param);

            }
            executeInsert(SQL_INSERT_INTO_POKEMON, params);
        }
    }

    private void executeInsert(final String SQL_INSERT_INTO_RESOURCE, final List<MapSqlParameterSource> params) {
        namedParamJdbcTemplate.batchUpdate(SQL_INSERT_INTO_RESOURCE, params.toArray(new MapSqlParameterSource[params.size()]));
    }
}
