package com.equipo1.logicadomino;

import DTOS.PartidaDTO;
import com.equipo1.convertidores.PartidaConverter;
import entidades.ConfiguracionJuego;
import entidades.Jugador;
import entidades.Partida;
import entidades.Pozo.Ficha;
import entidades.Tablero;
import mediador.MediadorPantallas;

/**
 *
 * @author
 */
public class LogicaDomino {

    public static void main(String[] args) {
        Jugador anfitrion = new Jugador();
        anfitrion.setNombre("Alfonso");
        anfitrion.setAvatar("DonAlfonsoDestroyer");
        anfitrion.setPartidasGanadas(0);

        Partida partida = new Partida(anfitrion);

        // se crea la configuracion (solo 3 jugadores y 5 fichas para cada uno)
        ConfiguracionJuego configuracion = new ConfiguracionJuego(3, 5);
        partida.setConfiguracion(configuracion);

        // se crea otro jugador
        Jugador jugador2 = new Jugador();
        jugador2.setNombre("Pedro");
        jugador2.setAvatar("ElPerroShesh14");
        jugador2.setPartidasGanadas(0);
        boolean agregado = partida.agregarJugador(jugador2);
        System.out.println(agregado);

        // se inicia la partida
        partida.iniciar();

        System.out.println("Fichas jugador 1: ");
        for (Ficha f : anfitrion.obtenerFichas()) {
            System.out.println(f);
        }

        System.out.println("Fichas jugador 2: ");
        for (Ficha f : jugador2.obtenerFichas()) {
            System.out.println(f);
        }

        System.out.println("Estado de la partida: " + partida.getEstado());

        Tablero tab = new Tablero();

        // Agrega fichas de prueba
        Ficha ficha1 = partida.getPozo().sacarFicha();
        Ficha ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);
        
        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);
        
        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);
        
        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        partida.setTablero(tab);
        // Hasta aqui se agregan datos de prueba

        PartidaDTO dto = new PartidaConverter().convertFromEntity(partida);

        MediadorPantallas.mostrarPantallaJuego(dto);

    }
}
