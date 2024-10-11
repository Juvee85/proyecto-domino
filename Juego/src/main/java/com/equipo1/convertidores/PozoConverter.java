/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo1.convertidores;

import DTOS.PozoDTO;
import entidades.Pozo;

/**
 *
 * @author diana
 */
public class PozoConverter extends Converter<PozoDTO, Pozo> {
 
    public PozoConverter(){
        super(PozoConverter::convertToEntity, PozoConverter::convertToDto);
    }
    private static PozoDTO convertToDto(Pozo pozo) {
        return null;
    }

    private static Pozo convertToEntity(PozoDTO DTO) {
       return null;
    }

   
}
