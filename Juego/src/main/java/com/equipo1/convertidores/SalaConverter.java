/*
 * SalaConverter.java
 */
package com.equipo1.convertidores;

import DTOS.SalaDTO;
import entidades.Sala;

/**
 *
 * @author Juventino López García - 00000248547 - 13/11/2024
 */
public class SalaConverter extends Converter<SalaDTO, Sala> {

    public SalaConverter() {
        super(SalaConverter::convertToEntity, SalaConverter::convertToDto);
    }

    private static SalaDTO convertToDto(Sala sala) {
        SalaDTO dto = new SalaDTO();
        dto.setNombre(sala.getNombre());
        dto.setContrasena(sala.getContrasena());
        dto.setMaxJugadores(sala.getMaxJugadores());
        dto.setJugadoresEnSala(sala.getJugadoresEnSala());
        dto.setNumeroFichasPorJugador(sala.getNumeroFichasPorJugador());

        return dto;
    }

    private static Sala convertToEntity(SalaDTO dto) {
        Sala sala = new Sala();
        sala.setNombre(dto.getNombre());
        sala.setContrasena(dto.getContrasena());
        sala.setMaxJugadores(dto.getMaxJugadores());
        sala.setJugadoresEnSala(dto.getJugadoresEnSala());
        sala.setNumeroFichasPorJugador(dto.getNumeroFichasPorJugador());

        return sala;
    }
}
