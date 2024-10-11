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
        return new FichaDTO(ficha.getPuntosCabeza(), ficha.getPuntosCola());
    }

    private static Ficha convertToEntity(FichaDTO DTO) {
        return null;
    }
    
}
