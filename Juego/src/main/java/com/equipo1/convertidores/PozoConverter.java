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
        return new PozoDTO();
    }

    private static Pozo convertToEntity(PozoDTO DTO) {
       return null;
    }

   
}
