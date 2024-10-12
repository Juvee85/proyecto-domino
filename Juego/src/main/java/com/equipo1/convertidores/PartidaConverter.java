/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo1.convertidores;

import DTOS.PartidaDTO;
import entidades.Partida;

/**
 *
 * @author diana
 */
public class PartidaConverter extends Converter<PartidaDTO, Partida> {

    
    public PartidaConverter(){
        super(PartidaConverter::convertToEntity, PartidaConverter::convertToDto);
    }
   
    private static PartidaDTO convertToDto(Partida partida) {
        PartidaDTO dto = new  PartidaDTO();
        dto.setJugadores(new JugadorConverter().createFromEntities(partida.getJugadores()));
        dto.setTablero(new TableroConverter().convertFromEntity(partida.getTablero()));
        return dto;
    }

    private static Partida convertToEntity(PartidaDTO DTO) {
        return null;
    }
    
    
}
