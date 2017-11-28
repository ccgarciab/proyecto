/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Cristian
 */
public class Tablero extends JPanel implements ActionListener{

    public Tablero(int ancho, int alto) {
        this.objeto = new Objeto(50, 2, "tree.png", dimension);
        this.carril = new Carril(200, 4, "agua.png", dimension);
        this.setFocusable(true);
        this.timer = new Timer(100, this);
        this.timer.start();
        this.dimension = new Dimension(ancho, alto);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.objeto.cambiarFotogramas();
        this.carril.cambiarFotogramas();
        this.objeto.dibujar(g, this);
        this.carril.dibujar(g, this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    private Objeto objeto;
    private Carril carril;
    private Timer timer; 
    private Dimension dimension;
}
