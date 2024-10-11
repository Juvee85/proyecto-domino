/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo1.convertidores;

import DTOS.TableroDTO;
import entidades.Tablero;

/**
 *
 * @author diana
 */
public class TableroConverter extends Converter<TableroDTO, Tablero> {

    public TableroConverter(){
        super(TableroConverter::convertToEntity, TableroConverter::convertToDto);
    }
    
    private static TableroDTO convertToDto(Tablero tablero ) {
        return null;
        
    }
    
    
    private static Tablero convertToEntity(TableroDTO DTO) {
        return null;
    }
    
    
}
