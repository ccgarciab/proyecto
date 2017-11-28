/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class Objeto extends Dibujable{

    public Objeto(int refY, int fotogramas, String nombreImagen, Dimension dimension) {
        super(refY, fotogramas, nombreImagen, dimension);
    }



    @Override
    public void dibujar(Graphics g, JPanel panel) {
        g.drawImage(imagen, refX, refY, refX+this.anchoFotograma, 
            refY+this.altoFotograma, this.anchoFotograma*this.fotogramaActual, 
            0, this.anchoFotograma*this.fotogramaActual + this.anchoFotograma, this.altoFotograma, panel);
    }
    public void setX(int X){
        this.refX = X;
    }
                
    public Rectangle obtenerRectangulo(){
        return new Rectangle(this.refX, this.refY, this.anchoFotograma, this.altoFotograma);
    }    
}