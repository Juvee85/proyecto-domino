/*
 * Converter.java
 */
package com.equipo1.convertidores;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 
 * @author Juventino López García - 00000248547 - 10/10/2024
 */
public class Converter <T, U> {

    private final Function<T, U> fromDto;
    private final Function<U, T> fromEntity;

    public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final U convertFromDto(final T dto) {
        return fromDto.apply(dto);
    }

    public final T convertFromEntity(final U entity) {
        return fromEntity.apply(entity);
    }

    public final List<U> createFromDtos(final Collection<T> dtos) {
        return dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
    }

    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }
}