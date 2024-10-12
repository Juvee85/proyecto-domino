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
        dto.setPartidasGanadas(jugador.getPartidasGanadas());

        return dto;
    }

    private static Jugador convertToEntity(JugadorDTO DTO) {
        return null;
    }

}
