/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Rectangle;
//import java.awt.Graphics;
//import java.util.ArrayList;
//import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class CarrilDinamico extends Carril{
    
    public CarrilDinamico(int refY, int fotogramas, String nombreImagen, Dimension dimension, int velocidad, boolean movimientoDerecha) {
        super(refY, fotogramas, nombreImagen, dimension);
        this.movimientoDerecha = movimientoDerecha;
        this.nombreObjetoPropio = "carro";
    }
    //ubica un movil al inicio o final del carril, y lo deja inicializado de forma que tenga el movimiento adecuado
    public void lanzarMovil(){
        Movil movil = null;
        if(this.movimientoDerecha){
            movil = new Movil(refY, 1, this.nombreObjetoPropio+".png", new Dimension(this.largoPantalla, this.altoPantalla), this.velocidad, movimientoDerecha);
            movil.setX(0);
        }else{
            movil = new Movil(refY, 1, this.nombreObjetoPropio+"_i.png", new Dimension(this.largoPantalla, this.altoPantalla), this.velocidad, movimientoDerecha);
            movil.setX(this.largoPantalla);
        }
        this.objetos.add(movil);
    }
    
    @Override
    public void colocarObjeto(){
        boolean intersectando = true;
        Rectangle rect = null;
        String nombre = null;
        if(this.movimientoDerecha){
            nombre = this.nombreObjetoPropio+".png";
        }else{
            nombre = this.nombreObjetoPropio+"_i.png";
        }
        Movil movil = new Movil(refY, 1, nombre, new Dimension(this.largoPantalla, this.altoPantalla), velocidad, movimientoDerecha);
        while(intersectando){
            movil.setX(rand.nextInt(this.largoPantalla));
            rect = movil.obtenerRectangulo();
            for(int i = 0; i<this.objetos.size(); i++){
                if(rect.intersects(this.objetos.get(i).obtenerRectangulo())){
                    break;
                }else if(i == this.objetos.size()-1){
                    intersectando = false;
                }
            }
            if(this.objetos.isEmpty()){
                break;
            }
        }
        this.objetos.add(movil);
    }
    
    protected boolean movimientoDerecha;
    protected int velocidad;
}
