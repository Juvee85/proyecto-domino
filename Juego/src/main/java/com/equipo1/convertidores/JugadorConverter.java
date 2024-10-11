/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo1.convertidores;

import DTOS.JugadorDTO;
import entidades.Jugador;

/**
 *
 * @author diana
 */
public class JugadorConverter extends Converter <JugadorDTO, Jugador> {

    public JugadorConverter(){
        super(JugadorConverter::convertToEntity, JugadorConverter::convertToDto);
    }
    private static JugadorDTO convertToDto(Jugador jugador) {
     return new JugadorDTO();     
    }

    private static Jugador convertToEntity(JugadorDTO DTO) {
      return null;
    }
    
    
}
