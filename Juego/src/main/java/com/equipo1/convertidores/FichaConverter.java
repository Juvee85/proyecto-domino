/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo1.convertidores;

import DTOS.FichaDTO;
import entidades.Pozo.Ficha;

/**
 *
 * @author diana
 */
public class FichaConverter extends Converter<FichaDTO, Ficha> {

    public FichaConverter(){
        super(FichaConverter::convertToEntity, FichaConverter::convertToDto);
    }
   
    private static FichaDTO convertToDto(Ficha ficha) {
        return null;
    }

    private static Ficha convertToEntity(FichaDTO DTO) {
        return null;
    }
    
}
