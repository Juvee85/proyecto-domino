package com.equipo1.convertidores;

import DTOS.FichaDTO;
import DTOS.TableroDTO;
import entidades.Pozo;
import entidades.Tablero;
import entidades.TrenFichas;
import java.util.ArrayList;
import tablero.Orientacion;

/**
 *
 * @author diana
 */
public class TableroConverter extends Converter<TableroDTO, Tablero> {

    public TableroConverter() {
        super(TableroConverter::convertToEntity, TableroConverter::convertToDTO);
    }

    private static TableroDTO convertToDTO(Tablero tablero) {
        FichaConverter convertidor = new FichaConverter();
        TableroDTO dto = new TableroDTO();
        TrenFichas fichas = tablero.getFichas();
        int cuenta = 0;
        ArrayList<FichaDTO> dtos = new ArrayList();
        for (Pozo.Ficha ficha : fichas) {
            FichaDTO fichaDTO = convertidor.convertFromEntity(ficha);

            if (cuenta < 5 && cuenta > 0) {
                fichaDTO.setOrientacion(Orientacion.HORIZONTAL);
            } else {
                fichaDTO.setOrientacion(Orientacion.VERTICAL);
            }
            cuenta++;
            dtos.add(fichaDTO);
            if (ficha.equals(fichas.obtenerFichaExtremoIzquierdo())) {
                cuenta = 1;
            }
        }
        dto.setFichaExtremoIzquierda(convertidor.convertFromEntity(fichas.obtenerFichaExtremoIzquierdo()));
        dto.setFichas(dtos);
        return dto;

    }

    private static Tablero convertToEntity(TableroDTO DTO) {
        return null;
    }

}
