package practica2;

/**
 *
 * @author Elmer C. Ramos™
 */
public class Jugador {
    
    //Atributos del jugador
    private String nombre;
    private String tamañoTablero;
    private int cantidadMovimientos;

    public Jugador(String nombre,String tamañoTablero, int cantidadMovimientos) {
        this.nombre = nombre;
        this.tamañoTablero = tamañoTablero;
        this.cantidadMovimientos = cantidadMovimientos;
    }//fin del constructor de la clase Jugador

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTamañoTablero() {
        return tamañoTablero;
    }

    public void setTamañoTablero(String tamañoTablero) {
        this.tamañoTablero = tamañoTablero;
    }

    public int getCantidadMovimientos() {
        return cantidadMovimientos;
    }

    public void setCantidadMovimientos(int cantidadMovimientos) {
        this.cantidadMovimientos = cantidadMovimientos;
    }  
    
    @Override
    public String toString() {
        return nombre + "\t" + tamañoTablero + "\t" + cantidadMovimientos+"\n";
    }        
}//fin de la clase Jugador
