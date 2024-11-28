package com.equipo1.convertidores;

import DTOS.JugadorDTO;
import entidades.Jugador;

/**
 *
 * @author diana
 */
public class JugadorConverter extends Converter<JugadorDTO, Jugador> {

    public JugadorConverter() {
        super(JugadorConverter::convertToEntity, JugadorConverter::convertToDto);
    }

    private static JugadorDTO convertToDto(Jugador jugador) {
        JugadorDTO dto = new JugadorDTO();
        dto.setAnfitrion(jugador.esAnfitrion());
        dto.setFichas(new FichaConverter().createFromEntities(jugador.obtenerFichas()));
        dto.setAvatar(jugador.getAvatar());
        dto.setNombre(jugador.getNombre());
        dto.setNumero(jugador.getNumero());
        dto.setListo(jugador.estaListo());

        return dto;
    }

    private static Jugador convertToEntity(JugadorDTO dto) {
        Jugador jugador = new Jugador();
        jugador.asignarFichas(new FichaConverter().createFromDTOS(dto.getFichas()));
        jugador.setAvatar(dto.getAvatar());
        jugador.setNombre(dto.getNombre());
        jugador.setNumero(dto.getNumero());
        jugador.setListo(dto.estaListo());

        return jugador;
    }

}
