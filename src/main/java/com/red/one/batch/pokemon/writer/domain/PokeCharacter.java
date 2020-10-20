package com.red.one.batch.pokemon.writer.domain;

import lombok.*;

@Data
public class PokeCharacter {
    private Long id;
    private String name;
    private Integer baseExperience;
    private Integer height;
    private Integer weight;
}