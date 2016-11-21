package practica2;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author Elmer Ramos™
 */
public class Estadisticas extends JFrame {
    
    public Estadisticas(JTextArea txtAEstadisticas) {
        
        // Atributos del JFrame
        setTitle( " Estadisticas del juego " );
        setBounds(450,300,300,150);
        setLayout(null);
        setResizable(false);
        setVisible( true );
        
        JTextArea titulo = new JTextArea();
        titulo.setBounds(0,0,300,30);        
        titulo.append("No."+"   "+"NOMBRE"+"\t"+"TABLERO"+"\t"+"MOVIMIENTOS");
        titulo.setEditable(false);
        
        txtAEstadisticas.setEditable(false);
        txtAEstadisticas.setBounds(0,30,300,120);
        
        add(titulo);
        add(txtAEstadisticas);
        
    } //fin del constructor   
}//fin de la clase Estadisticas
