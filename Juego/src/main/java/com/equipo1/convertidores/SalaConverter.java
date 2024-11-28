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
        /*
        dto.setHost(sala.getHost());
        dto.setPuerto(sala.getPuerto());
*/
        dto.setContrasena(sala.getContrasena());
        dto.setMaxJugadores(sala.getMaxJugadores());
        dto.setJugadoresEnSala(sala.getJugadoresEnSala());

        return dto;
    }

    private static Sala convertToEntity(SalaDTO dto) {
        Sala sala = new Sala();
        sala.setNombre(dto.getNombre());
        /*
        sala.setHost(dto.getHost());
        sala.setPuerto(dto.getPuerto());
*/
        sala.setContrasena(dto.getContrasena());
        sala.setMaxJugadores(dto.getMaxJugadores());
        sala.setJugadoresEnSala(dto.getJugadoresEnSala());

        return sala;
    }
}
