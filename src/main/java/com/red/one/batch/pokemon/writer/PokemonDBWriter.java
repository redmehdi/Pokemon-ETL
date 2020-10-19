package com.red.one.batch.pokemon.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class PokemonDBWriter implements ItemWriter<List<PokeSpecies>>{

    @Autowired
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Override
    public void write(List<? extends List<PokeSpecies>> items) throws Exception {
        for (List<PokeSpecies> registrations : items) {

            final String SQL_INSERT_INTO_REGISTRATION = "insert into poke_species (created_by, created_date, modified_by, modified_date, color, id_ext, name, url ) " +
                    "values ('POKE_ETL', CURRENT_TIMESTAMP(), 'POKE_ETL', CURRENT_TIMESTAMP(), 'red', :idExt, :name, :url)";

            final List<MapSqlParameterSource> params = new ArrayList<>();
            for (PokeSpecies registration : registrations) {
                MapSqlParameterSource param = new MapSqlParameterSource();

                param.addValue("color", registration.getColor());
                param.addValue("idExt", registration.getIdExt());
                param.addValue("name", registration.getName());
                param.addValue("url", registration.getUrl());

                params.add(param);

            }

            namedParamJdbcTemplate.batchUpdate(SQL_INSERT_INTO_REGISTRATION, params.toArray(new MapSqlParameterSource[params.size()]));
        }
    }
}
