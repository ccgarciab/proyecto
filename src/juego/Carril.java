/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class Carril extends Dibujable{

    public Carril(int refY, int fotogramas, String nombreImagen, Dimension dimension) {
        super(refY, fotogramas, nombreImagen, dimension);
        this.objetos = new ArrayList(0);
    }

    @Override
    public void dibujar(Graphics g, JPanel panel) {
        for(int i = 0; i<(this.largoPantalla/this.anchoFotograma)+1; i++){
            g.drawImage(imagen, refX+(i*this.anchoFotograma), refY, refX+((i+1)*this.anchoFotograma), 
                refY+this.altoFotograma, this.anchoFotograma*this.fotogramaActual, 
                0, this.anchoFotograma*this.fotogramaActual + this.anchoFotograma, this.altoFotograma, panel);
        }
        for(Objeto objeto: this.objetos){
            objeto.dibujar(g, panel);
        }
    }
    
    public void colocarArbol(int X){
        Objeto arbol = new Objeto(refY, 2, "arbol.png",new Dimension(this.largoPantalla, this.altoPantalla));
        arbol.setX(X);
        this.objetos.add(arbol);
    }
    
    protected ArrayList<Objeto> objetos; 
}
