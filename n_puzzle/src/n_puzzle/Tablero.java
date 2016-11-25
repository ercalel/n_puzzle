package n_puzzle;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;

/**
 * @author Elmer C. Ramos
 */
public class Tablero extends JFrame implements ActionListener {
    
    private int cantidad_movimientos = 0;//numero de movimientos del jugador    
    private JButton[][] tablero;//arreglo de botones para generar el tablero
    private int filas_tab; // Filas del tablero
    private int columnas_tab; // Columnas del tablero
    private int[][] matriz_aleatorios;//matriz donde se colocan los numeros aleatorios    
    private int[][] matriz_ordenada; //matriz con los numeros ordenados sirve para verificar el estado del juego
    static Jugador[] lista_jugadores = new Jugador[10];
    int cantidad_jugadores = 0;//indica el número de jugadores en la lista    
    static Estadisticas estadisticas;//muestra las estadisticas del juego

    /**
     * Constructor.
     */
    Tablero() throws ParseException {
        // Atributos del JFrame
        super(" N-puzzle");
        this.setSize(720, 640);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        inicializar_comoponentes();
        this.setJMenuBar(barra_menu);
        this.getContentPane().add(toolbar_herramientas, BorderLayout.NORTH);
        this.getContentPane().add(panel_tablero);
        this.getContentPane().add(toolbar_información, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < filas_tab; i++) {
            for (int j = 0; j < columnas_tab; j++) {
                if (ae.getSource() == tablero[i][j]) {
                    // Busca el boton oculto para cambiar propiedades
                    if (i - 1 >= 0 && tablero[i - 1][j].getText() == null) {
                        tablero[i - 1][j].setBackground(Color.WHITE);
                        tablero[i - 1][j].setText(tablero[i][j].getText());
                        tablero[i][j].setText(null);
                        tablero[i][j].setBackground(Color.BLACK);
                        
                        cantidad_movimientos++;
                        txt_movimientos.setText(String.valueOf(cantidad_movimientos));
                        estado_juego();
                    } else if (j - 1 >= 0 && tablero[i][j - 1].getText() == null) {
                        tablero[i][j - 1].setBackground(Color.WHITE);
                        tablero[i][j - 1].setText(tablero[i][j].getText());
                        tablero[i][j].setText(null);
                        tablero[i][j].setBackground(Color.BLACK);
                        
                        cantidad_movimientos++;
                        txt_movimientos.setText(String.valueOf(cantidad_movimientos));
                        estado_juego();
                    } else if (j + 1 < columnas_tab && tablero[i][j + 1].getText() == null) {
                        tablero[i][j + 1].setBackground(Color.WHITE);
                        tablero[i][j + 1].setText(tablero[i][j].getText());
                        tablero[i][j].setText(null);
                        tablero[i][j].setBackground(Color.BLACK);
                        
                        cantidad_movimientos++;
                        txt_movimientos.setText(String.valueOf(cantidad_movimientos));
                        estado_juego();
                    } else if (i + 1 < filas_tab && tablero[i + 1][j].getText() == null) {
                        tablero[i + 1][j].setBackground(Color.WHITE);
                        tablero[i + 1][j].setText(tablero[i][j].getText());
                        tablero[i][j].setText(null);
                        tablero[i][j].setBackground(Color.BLACK);
                        
                        cantidad_movimientos++;
                        txt_movimientos.setText(String.valueOf(cantidad_movimientos));
                        estado_juego();
                    }
                }
            }
        }
    }

    /**
     * Creia el tablero de juego con las dimenciones indicadas.
     */
    private void crear_tablero(int f, int c) {
        int numero = (f * c) - 1;
        for (int i = 0; i < f; i++) {
            for (int j = 0; j < c; j++) {
                tablero[i][j] = new JButton();
                if (matriz_aleatorios[i][j] != numero) {
                    tablero[i][j].setBackground(Color.WHITE);
                    tablero[i][j].setText(String.valueOf(matriz_aleatorios[i][j] + 1));
                } else {
                    tablero[i][j].setBackground(Color.BLACK);
                    tablero[i][j].setText(null);
                }
                panel_tablero.add(tablero[i][j]);
                tablero[i][j].addActionListener(this);
            }
        }
    }

    /**
     * Retorna el juego a su estado inicial.
     */
    private void reiniciar_juego() {
        for (int i = 0; i < filas_tab; i++) {
            for (int j = 0; j < columnas_tab; j++) {
                if (matriz_aleatorios[i][j] != (filas_tab * columnas_tab - 1)) {
                    tablero[i][j].setBackground(Color.WHITE);
                    tablero[i][j].setText(String.valueOf(matriz_aleatorios[i][j] + 1));
                } else {
                    tablero[i][j].setText(null);
                    tablero[i][j].setBackground(Color.BLACK);
                }
            }
        }
        cantidad_movimientos = 0;
        txt_movimientos.setText("0");
    }

    /**
     * Asigna un número aleatorio a cada posición del tablero.
     */
    private void generar_aleatorios() {
        int x = 0;
        int y = 0;
        int numero = -1;
        double x1;
        double y1;

        //Pone la matriz de las fichas a -1. El número -1 indica que no tiene ficha
        for (int i = 0; i < filas_tab; i++) {
            for (int j = 0; j < columnas_tab; j++) {
                matriz_aleatorios[i][j] = -1;
            }
        }

        //Crear Numeros aleatorios. 
        for (int i = 0; i < filas_tab; i++) {
            for (int j = 0; j < columnas_tab; j++) {
                do {
                    x1 = Math.random() * filas_tab;
                    y1 = Math.random() * columnas_tab;
                    x = (int) x1;
                    y = (int) y1;
                } while (matriz_aleatorios[x][y] != -1);
                numero++;
                if (numero == filas_tab * columnas_tab) {
                    numero = 0;
                }
                matriz_aleatorios[x][y] = numero;
            }
        }
    }

    /**
     * Ordena la lista de jugadores.
     */
    public static void ordenar_lista_jugadores() {
        int in;
        for (int i = 1; i < lista_jugadores.length; i++) {
            if (lista_jugadores[i] != null) {
                Jugador aux = lista_jugadores[i];
                in = i;//inicia el desplazamiento en i
                while (in > 0 && lista_jugadores[in - 1].getCantidadMovimientos() > aux.getCantidadMovimientos()) {
                    lista_jugadores[in] = lista_jugadores[in - 1];//desplaza el elemento hacia la derecha
                    --in;
                }
                lista_jugadores[in] = aux;//inserta elemento
            }
        }
    }

    /**
     * Verifica la cantidad de aciertos para determinar el estado del juego.
     */
    void estado_juego() {
        int aciertos = 0;//cuenta el numero de aciertos
        for (int i = 0; i < filas_tab; i++) {
            for (int j = 0; j < columnas_tab; j++) {
                String temp = String.valueOf(matriz_ordenada[i][j]);
                if (tablero[i][j].getText() != null && temp.equals(tablero[i][j].getText())) {
                    aciertos++;
                }
            }
        }
        
        if (aciertos == filas_tab * columnas_tab - 1) {
            if (cantidad_jugadores <= 9) {
                JOptionPane.showMessageDialog(null, "FELICIDADES HAS GANADO!!!!!"
                        + "\nTotal movimientos: " + cantidad_movimientos, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                // Agrega al jugador
                lista_jugadores[cantidad_jugadores] = new Jugador(txt_jugador.getText(),
                        filas_tab + " X " + columnas_tab, cantidad_movimientos);
                ordenar_lista_jugadores();
                cantidad_jugadores++;
            }
        }
    }

    /**
     * Inicializa los componentes del JFrame
     */
    private void inicializar_comoponentes() throws ParseException {
        // Barra de menú
        barra_menu = new JMenuBar();
        barra_menu.setBorder(new BevelBorder(BevelBorder.RAISED));

        // Menú
        JMenu menu = new JMenu("Menú");

        // Opciones del menú
        JMenuItem item_juego_nuevo = new JMenuItem("Juego nuevo");
        // Habilita los campos para la creación del juego
        item_juego_nuevo.addActionListener((ActionEvent ae) -> {
            txt_nombre.setEnabled(true);
            txt_filas.setEnabled(true);
            txt_columnas.setEnabled(true);
            btn_crear_tablero.setEnabled(true);
            txt_nombre.setText(null);
            txt_jugador.setText(null);
            txt_movimientos.setText(null);
            cantidad_movimientos = 0;
            panel_tablero.removeAll();
            panel_tablero.setVisible(false);
        });
        // Opción para reiniciar el juego
        JMenuItem item_reiniciar_juego = new JMenuItem("Reiniciar juego");
        item_reiniciar_juego.addActionListener((ActionEvent ae) -> {
            try {
                JOptionPane.showMessageDialog(null, "Se perderan los movimientos "
                        + "realizados hasta ahora.", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
                reiniciar_juego();//llamamos al metodo para volver al estado inicial del juego
            } catch (Exception excepcion) {
                JOptionPane.showMessageDialog(null, "No se ha iniciado el juego!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Opción para ver estadísticas de juegos
        JMenuItem item_estadisticas_juego = new JMenuItem("Estadisticas de juego");
        item_estadisticas_juego.addActionListener((ActionEvent e) -> {
            if (lista_jugadores[0] != null) {
                if (lista_jugadores[1] == null & lista_jugadores[2] == null) {
                    txtAEstadisticas.setText("1. " + "   " + lista_jugadores[0] + "\n");
                    estadisticas = new Estadisticas(txtAEstadisticas);
                } else if (lista_jugadores[2] == null) {
                    txtAEstadisticas.setText("1. " + "   " + lista_jugadores[0] + "\n"
                            + "2. " + "   " + lista_jugadores[1] + "\n");
                    estadisticas = new Estadisticas(txtAEstadisticas);
                } else {
                    txtAEstadisticas.setText("1. " + "   " + lista_jugadores[0] + "\n"
                            + "2. " + "   " + lista_jugadores[1] + "\n"
                            + "3. " + "   " + lista_jugadores[2] + "\n");
                    
                    estadisticas = new Estadisticas(txtAEstadisticas);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay estadisticas para"
                        + " mostrar.", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Agregamos las opciones al menú
        menu.add(item_juego_nuevo);
        menu.add(item_reiniciar_juego);
        menu.add(item_estadisticas_juego);

        // Agrega el menú a la barra de menú
        barra_menu.add(menu);

        // Barra de herramientas
        toolbar_herramientas = new JToolBar();
        toolbar_herramientas.setBorder(new EtchedBorder());
        
        lb_nombre = new JLabel();
        lb_nombre.setText("Ingrese su nombre:");
        
        txt_nombre = new JTextField();
        txt_nombre.setEnabled(false);
        
        lb_filas = new JLabel();
        lb_filas.setText("Filas:");
        txt_filas = new JFormattedTextField(new MaskFormatter("#"));
        txt_filas.enable(false);
        
        lb_columnas = new JLabel();
        lb_columnas.setText("Columnas:");
        txt_columnas = new JFormattedTextField(new MaskFormatter("#"));
        txt_columnas.enable(false);
        
        btn_crear_tablero = new JButton();
        btn_crear_tablero.setText("Crear tablero");
        btn_crear_tablero.setEnabled(false);
        btn_crear_tablero.addActionListener((ActionEvent ae) -> {
            filas_tab = Integer.parseInt(txt_filas.getValue().toString());
            columnas_tab = Integer.parseInt(txt_columnas.getValue().toString());
            
            if ("".equals(txt_nombre.getText()) || (filas_tab < 2 && columnas_tab < 2)) {
                JOptionPane.showMessageDialog(null, "Nombre no ingresado o dimensiones inválidas!!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (cantidad_jugadores <= 9) {
                    txt_jugador.setText(txt_nombre.getText());//muestra el nombre del jugador durante el juego
                    tablero = new JButton[filas_tab][columnas_tab];//crea un nuevo arreglo de botones
                    matriz_aleatorios = new int[filas_tab][columnas_tab];//crea un nuevo arreglo de numeros aleatorios
                    matriz_ordenada = new int[filas_tab][columnas_tab];//crea un nuevo arreglo de numeros ordenados
                    // llenamos la matriz de numeros ordenados
                    int totalNumeros = 0;
                    for (int i = 0; i < filas_tab; i++) {
                        for (int j = 0; j < columnas_tab; j++) {
                            matriz_ordenada[i][j] = totalNumeros + 1;
                            totalNumeros++;
                        }
                    }
                    
                    generar_aleatorios();
                    // Creamos y mostramos el tablero
                    panel_tablero.setLayout(new GridLayout(filas_tab, columnas_tab));
                    crear_tablero(filas_tab, columnas_tab);//colocamos los botones en el panel
                    panel_tablero.setVisible(true);
                }

                //desactivamos los siguientes objetos
                txt_nombre.setEnabled(false);
                txt_filas.setEnabled(false);
                txt_columnas.setEnabled(false);
                btn_crear_tablero.setEnabled(false);
            }
        });

        // Agregamos los elementos a la barra de herramientas
        toolbar_herramientas.add(lb_nombre);
        toolbar_herramientas.add(txt_nombre);
        toolbar_herramientas.add(lb_filas);
        toolbar_herramientas.add(txt_filas);
        toolbar_herramientas.add(lb_columnas);
        toolbar_herramientas.add(txt_columnas);
        toolbar_herramientas.add(btn_crear_tablero);

        // Panel donde se creará el tablero
        panel_tablero = new JPanel();
        panel_tablero.setVisible(false);
        
        txtAEstadisticas = new JTextArea();
        txtAEstadisticas.setEditable(false);

        // Barra de información de juego
        toolbar_información = new JToolBar();
        toolbar_información.setBorder(new EtchedBorder());
        
        lb_jugador = new JLabel();
        lb_jugador.setText("Nombre del jugador actual:");
        
        txt_jugador = new JTextField(3);
        txt_jugador.setEditable(false);
        
        lb_movimientos = new JLabel();
        lb_movimientos.setText("Número de movimientos:");
        
        txt_movimientos = new JTextField(3);
        txt_movimientos.setEditable(false);
        
        toolbar_información.add(lb_jugador);
        toolbar_información.add(txt_jugador);
        toolbar_información.add(lb_movimientos);
        toolbar_información.add(txt_movimientos);
    }

    /**
     * Componentes del JFrame
     */
    private JMenuBar barra_menu;
    private JToolBar toolbar_herramientas;
    private JPanel panel_tablero;
    private JLabel lb_nombre;
    private JTextField txt_nombre;
    private JLabel lb_filas;
    private JFormattedTextField txt_filas;
    private JLabel lb_columnas;
    private JFormattedTextField txt_columnas;
    private JButton btn_crear_tablero;
    private JTextArea txtAEstadisticas;
    private JLabel lb_jugador;
    private JTextField txt_jugador;
    private JLabel lb_movimientos;
    private JTextField txt_movimientos;
    private JToolBar toolbar_información;
}
