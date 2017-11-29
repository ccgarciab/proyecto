/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class Movil extends Objeto{

    public Movil(int refY, int fotogramas, String nombreImagen, Dimension dimension, int velocidad ,boolean movimientoADerecha) {
        super(refY, fotogramas, nombreImagen, dimension);
        this.movimientoADerecha = movimientoADerecha;
    }
    //este metodo dibujar() se distingue de los demás en que también mueve el movil
    @Override
    public void dibujar(Graphics g, JPanel panel) {
        g.drawImage(imagen, refX, refY, refX+this.anchoFotograma, 
            refY+this.altoFotograma, this.anchoFotograma*this.fotogramaActual, 
            0, this.anchoFotograma*this.fotogramaActual + this.anchoFotograma, this.altoFotograma, panel);
        this.moverEnX();
    }
    //el boolean indica si el movimiento debe ser hacia la derecha o no
    public void moverEnX(){
        if(this.movimientoADerecha){
            this.refX ++;
        }else{
            this.refX --;
        }
    }
    
    private boolean movimientoADerecha;
    private int velocidad;
}
