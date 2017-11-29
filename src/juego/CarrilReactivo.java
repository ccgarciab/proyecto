/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 *
 * @author Cristian
 */
public class CarrilReactivo extends CarrilDinamico{
    
    public CarrilReactivo(int refY, int fotogramas, String nombreImagen, Dimension dimension, int velocidad, boolean movimientoDerecha) {
        super(refY, fotogramas, nombreImagen, dimension, velocidad, movimientoDerecha);
        this.nombreObjetoPropio = "tronco";
    }
    
    @Override
    public void lanzarMovil(){
        Movil movil = new Movil(refY, 1, this.nombreObjetoPropio+".png", new Dimension(this.largoPantalla, this.altoPantalla), this.velocidad, movimientoDerecha);
        if(this.movimientoDerecha){
            movil.setX(0);
        }else{
            movil.setX(this.largoPantalla);
        }
        this.objetos.add(movil);
    }
    
    public Rectangle obtenerRectangulo(){
        return new Rectangle(refX, refY, this.largoPantalla, this.altoFotograma);
    }
}
