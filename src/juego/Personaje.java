/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class Personaje extends Objeto{
    
    public Personaje(int refY, int fotogramas, Dimension dimension) {
        super(refY, fotogramas, "rana.png", dimension);
        this.salto = this.altoFotograma;
        this.vivo = true;
        this.xPasado = 0;
        this.yPasado = 0;
        this.puntaje = 0;
    }
    
    public void keyPressed(KeyEvent e) throws IOException {
        this.xPasado = this.refX;
        this.yPasado = this.refY;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN: this.refY += this.salto;break;
            case KeyEvent.VK_UP: this.refY -= this.salto;break;
            case KeyEvent.VK_LEFT: this.refX -= this.salto;break;
            case KeyEvent.VK_RIGHT: this.refX += this.salto;break;
              
          }
    }
    
    @Override
    public void dibujar(Graphics g, JPanel panel) {
        g.drawImage(imagen, refX, refY, refX+this.anchoFotograma, 
            refY+this.altoFotograma, this.anchoFotograma*this.fotogramaActual, 
            0, this.anchoFotograma*this.fotogramaActual + this.anchoFotograma, this.altoFotograma, panel);
        Font fuente = new Font("SansSerif", Font.PLAIN, 20);
        g.setFont(fuente);
        g.setColor(Color.red);
        g.drawString("Puntaje: "+this.puntaje, 10, 10);
    }
    
    public boolean isVivo(){
        return this.vivo;
    }
    
    public void toggleVida(){
        this.vivo = !this.vivo;
    }
    
    public void subirPuntaje(){
        this.puntaje++;
    }
    
    public void undoMove(){
        this.refX = this.xPasado;
        this.refY = this.yPasado;
    }
    
    private int salto;
    private boolean vivo;
    private int xPasado;
    private int yPasado;
    private int puntaje;
}
