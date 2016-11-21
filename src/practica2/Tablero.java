package practica2;

import java.awt.*;
import javax.swing.*;

/**
 * @author Elmer C. Ramos™
 */
// clase para mostrar un objeto ImageIcon en un panel
class Tablero extends JPanel {
    
   private ImageIcon imagen; // imagen a mostrar
   private String[] imagenes = {"/fondo/icono.JPG"};
   
   // carga la imagen
   public Tablero() {
     imagen = new ImageIcon( imagenes[ 0 ] ); // establece el icono
   } // ﬁn del constructor de MiJPanel
   
   // muestra el objeto imageIcon en el panel
    @Override
    public void paintComponent( Graphics g ) {
     super.paintComponent( g );
     imagen.paintIcon( this, g, 0, 0 ); // muestra el icono
   } // ﬁn del método paintComponent
    
   // devuelve las medidas de la imagen
    @Override
   public Dimension getPreferredSize() {
     return new Dimension( imagen.getIconWidth(),
     imagen.getIconHeight() );
   } // ﬁn del método getPreferredSize
    
} // ﬁn de la clase MiJPane