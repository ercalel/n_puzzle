package practica2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * @author Elmer C. Ramos™
 */
public final class VentanaPrincipal extends JFrame implements ActionListener{
    
    static Jugador[] listaJugadores = new Jugador[10];//lista de los jugadores
    String nombre;//nombre del jugador
    String dimenciones;//dimenciones del tablero del jugador    
    int numeroMovimientos = 0;//numero de movimientos del jugador
    
    //dimenciones del tablero
    int filas;
    int columnas;
    
    //arreglos de las dimenciones permitidas para el tablero
    String[] listaFilas = {"2","3","4","5","6","7","8","9","10"};
    String[] listaColumnas = {"2","3","4","5","6","7","8","9","10"};
    
    JButton[][] tablero;//arreglo de botones para generar el tablero
    int[][] numeroAleatorios;//matriz donde se colocan los numeros aleatorios    
    int[][] numerosOrdenados; //matriz con los numeros ordenados sirve para verificar el estado del juego
    //imagen ue indica el espacio vacio en el tablero
    ImageIcon imagen  = new ImageIcon( getClass().getResource( "/fondo/icono.JPG" ) );
    
    int cantidadJugadores = 0;//indica el número de jugadores en la lista    
    static Estadisticas estadisticas;//muestra las estadisticas del juego

    //objetos de la ventana
    FondoPantalla panelFondo;
    JButton btnNuevo;
    JLabel lbNombre;
    JTextField txtNombre;
    JPanel panelDimenciones;
    JLabel lbFilas; 
    JComboBox cbxFilas;
    JLabel lbColumnas;
    JComboBox cbxColumnas;
    JButton btnCrearTablero;
    JButton btnReiniciar;
    JButton btnEstadisticas;
    JTextArea txtAEstadisticas;
    JButton btnSalir;
    JLabel lbJugador;
    JTextField txtJugador;
    JLabel lbMovimientos;
    JTextField txtMovimientos;
    JInternalFrame marcoTablero;
        
    //constructor de la clase Tablero
    VentanaPrincipal(){
        // Atributos del JFrame
        setTitle( " N-puzzle_IPC1 v.1.0 " );
        setSize(720,640);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        
        panelFondo = new FondoPantalla();
        panelFondo.setLayout(null);
        ((FondoPantalla) panelFondo).setImagen("/fondo/puzzle0.jpg");
        
        btnNuevo = new JButton();
        btnNuevo.setText("Juego Nuevo");
        btnNuevo.setBounds(20,35,130,25);
        
        lbNombre = new JLabel();
        lbNombre.setText("Ingrese su nombre:");
        lbNombre.setBounds(20,70,150,25);
        
        txtNombre =new JTextField();
        txtNombre.setEnabled(false);
        txtNombre.setBounds(20, 95, 170, 25);
        
        panelDimenciones = new JPanel();
        panelDimenciones.setBounds(20,130,170,90);
        panelDimenciones.setLayout(null);
        panelDimenciones.setBackground(Color.WHITE);
        panelDimenciones.setBorder(BorderFactory.createTitledBorder("Dimenciones "
                + "del tablero:"));
        add(panelDimenciones);
        
        lbFilas = new JLabel();
        lbFilas.setText("Filas:");
        lbFilas.setBounds(10,20,60,25);
        
        cbxFilas = new JComboBox(listaFilas); // establece JComboBox
        cbxFilas.setMaximumRowCount( 9 ); // muestra nueve ?las
        cbxFilas.setEnabled(false);
        cbxFilas.setBounds(70,20, 65, 25);     
        
        lbColumnas = new JLabel();
        lbColumnas.setText("Columnas:");
        lbColumnas.setBounds(5,50,60,25); 
        
        cbxColumnas = new JComboBox(listaColumnas); // establece JComboBox
        cbxColumnas.setMaximumRowCount( 9 ); // muestra nueve ?las
        cbxColumnas.setEnabled(false);
        cbxColumnas.setBounds(70,50,65, 25);
        
        //agregamos objetos a panelDimenciones
        panelDimenciones.add(lbFilas);
        panelDimenciones.add(cbxFilas);
        panelDimenciones.add(lbColumnas);
        panelDimenciones.add(cbxColumnas);
        
        btnCrearTablero = new JButton();
        btnCrearTablero.setText("Crear tablero");
        btnCrearTablero.setEnabled(false);
        btnCrearTablero.setBounds(20,230,130,25);
        
        btnReiniciar = new JButton();
        btnReiniciar.setText("Reiniciar");
        btnReiniciar.setEnabled(false);
        btnReiniciar.setBounds(20,265,130,25);
        
        btnEstadisticas = new JButton();
        btnEstadisticas.setText("Estadisticas");
        btnEstadisticas.setBounds(20,300,130,25);
        
        txtAEstadisticas = new JTextArea();
        txtAEstadisticas.setEditable(false);
        
        btnSalir = new JButton();
        btnSalir.setText("Salir");
        btnSalir.setBounds(20,335,130,25);
        
        lbJugador = new JLabel();
        lbJugador.setText("Nombre del jugador:");
        lbJugador.setBounds(360,540,150,25);
        
        txtJugador = new JTextField(3);
        txtJugador.setEditable(false);
        txtJugador.setBounds(510, 540, 190, 25);
        
        lbMovimientos = new JLabel();
        lbMovimientos.setText("Número de movimientos:");
        lbMovimientos.setBounds(360,570,150,25);
        
        txtMovimientos=new JTextField(3);
        txtMovimientos.setEditable(false);
        txtMovimientos.setBounds(510,570, 100, 25);
        
        //agregamos todos nuestros objetos al JFrame
        panelFondo.add(btnNuevo);
        panelFondo.add(lbNombre);
        panelFondo.add(txtNombre);
        panelFondo.add(btnCrearTablero);
        panelFondo.add(btnReiniciar);
        panelFondo.add(btnEstadisticas);
        panelFondo.add(btnSalir);
        panelFondo.add(lbJugador);
        panelFondo.add(txtJugador);
        panelFondo.add(lbMovimientos);
        panelFondo.add(txtMovimientos);
        
        add(panelFondo);
        
        //activamos las opciones para el registro del jugador
        btnNuevo.addActionListener(new ActionListener(){          
           @Override
            public void actionPerformed(ActionEvent ae) {                
               try{
                   //activamos los sigientes objetos
                   txtNombre.setEnabled(true);
                   cbxFilas.setEnabled(true);
                   cbxColumnas.setEnabled(true);
                   btnCrearTablero.setEnabled(true);
                   
                   marcoTablero.dispose();//eliminamos el juego actual
                   
                   txtNombre.setText(null);//limpia el area de texto
                   txtJugador.setText(null);//borra el nombre del jugador actual
                   txtMovimientos.setText(null);//borra los movimientos del jugador actual
                   numeroMovimientos = 0;//reinicia el contador de movimientos
                   btnReiniciar.setEnabled(false);
               }catch(Exception e){
                   //atrapa el error si se crea un nuevo juego
               }               
            }
        });//fin de la accion para el btnNuevo
        
        //crea un nuevo tablero para iniciar el juego
        btnCrearTablero.addActionListener(new ActionListener(){          
           @Override
            public void actionPerformed(ActionEvent ae) {
               nombre = txtNombre.getText();//obtenemos el nombre del jugador
               filas =  Integer.parseInt(String.valueOf(cbxFilas.getSelectedItem()));//obtiene el numero de filas del tablero
               columnas = Integer.parseInt(String.valueOf(cbxColumnas.getSelectedItem()));//obtiene el numero de columnas del tablro
               if("".equals(nombre)) {//verifica ue se haya ingresado el nombre del jugador
                   JOptionPane.showMessageDialog(null,"Ingrese un nombre.",
                           "Error",JOptionPane.ERROR_MESSAGE);
               }else if(filas==2 & columnas==2){//verifica ue las dimenciones del tablero sean validas
                   JOptionPane.showMessageDialog(null,"Tamaño de tablero no "
                           + "válido, elija dimenciones distintas de 2 X 2",
                           "Error",JOptionPane.ERROR_MESSAGE);
               }else{
                   if (cantidadJugadores <=9 ) { 
                       txtJugador.setText(nombre);//muestra el nombre del jugador durante el juego
                       tablero = new JButton[filas][columnas];//crea un nuevo arreglo de botones
                       numeroAleatorios = new int[filas][columnas];//crea un nuevo arreglo de numeros aleatorios
                       numerosOrdenados = new int[filas][columnas];//crea un nuevo arreglo de numeros ordenados
                       //llenamos la matriz de numeros ordenados
                       int totalNumeros = 0;
                       for (int i = 0; i < filas; i++) {
                           for (int j = 0; j < columnas; j++) {
                               numerosOrdenados[i][j] = totalNumeros+1;
                               totalNumeros++;
                           }
                       }
                       
                       generarAleatorio ();//generamos numeros aleatorios en la matriz numeroAleatorio
                          
                       // crea el marco interno donde se dibuja nuestro tablero
                       marcoTablero = new JInternalFrame( 
                               "Tablero "+filas+" X "+columnas, false, true, false, false );
                       
                       Tablero panelTablero = new Tablero(); // crea nuevo panel  tipo Tablero
                       panelTablero.setBounds(0,0,500,500);
                       panelTablero.setLayout(new GridLayout(filas,columnas));
                       crearTablero(panelTablero,filas,columnas);//colocamos los botones en el panel 
                       marcoTablero.add( panelTablero, BorderLayout.CENTER ); // agrega el panel al marco interno
                       marcoTablero.setBounds(200,20,500,500); // establece las dimenciones del marco interno
                       marcoTablero.setVisible( true ); // muestra marco interno
                       panelFondo.add( marcoTablero ); // agrega el marco interno al principal
                   } else{
                       JOptionPane.showMessageDialog(null,"La lista está llena",
                               "Advertencia",JOptionPane.WARNING_MESSAGE);
                   }//fin del if...else                   
                   
                   //desactivamos los siguientes objetos
                   txtNombre.setEnabled(false);
                   cbxFilas.setEnabled(false);
                   cbxColumnas.setEnabled(false);
                   btnCrearTablero.setEnabled(false);
                   btnReiniciar.setEnabled(true);//activa el boton reiniciar
               }                   
           }           
        });//fin de la accion para el boton crear tablero
        
        //reinicia el juego actual del jugador
        btnReiniciar.addActionListener(new ActionListener(){          
           @Override
            public void actionPerformed(ActionEvent ae) {
               try{
                   JOptionPane.showMessageDialog(null,"Se perderan los movimientos "
                           + "realizados hasta ahora.","Advertencia",JOptionPane.OK_CANCEL_OPTION);
                   reiniciarJuego();//llamamos al metodo para volver al estado inicial del juego
               }catch(Exception excepcion){
                   JOptionPane.showMessageDialog(null,"No se ha iniciado el juego!"
                           ,"Error",JOptionPane.ERROR_MESSAGE);
               }              
            }
        });//fin de la accion de btnReiniciar
        
        //muestra las estadisticas del juego
        btnEstadisticas.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (listaJugadores[0] != null) {
                    if (listaJugadores[1] == null & listaJugadores[2] == null) {
                        txtAEstadisticas.setText("1. " +"   "+ listaJugadores[0] + "\n");
                        estadisticas = new Estadisticas(txtAEstadisticas);
                    }else if (listaJugadores[2] == null) {
                        txtAEstadisticas.setText(
                          "1. "+"   "+ listaJugadores[0] + "\n"
                        + "2. " +"   "+ listaJugadores[1] + "\n");
                        estadisticas = new Estadisticas(txtAEstadisticas);
                    }else{
                        txtAEstadisticas.setText(
                                "1. " +"   "+ listaJugadores[0] + "\n"
                                + "2. " +"   "+ listaJugadores[1] + "\n"
                                + "3. " +"   "+ listaJugadores[2] + "\n");
                        
                        estadisticas = new Estadisticas(txtAEstadisticas);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"No hay estadisticas para"
                            + " mostrar.","Advertencia",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });//fin de la accion btnEstadisticas
       
       //termina la ejecucion del programa
       btnSalir.addActionListener(new ActionListener(){
           @Override
	   public void actionPerformed(ActionEvent e){
               System.exit(0); //cierra nuestro programa 
           }
       });//fin de la accion de btnSalir
        
    }//fin del constructor de la clase tablero
    
        @Override
    public void actionPerformed(ActionEvent ae){
        for (int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                if(ae.getSource() == tablero[i][j]){
                    //al pulsar un boton buscamos un boton oculto para intercambiarlo
                    if ( i-1 >= 0 && tablero[i-1][j].getIcon()!=null) {
                        tablero[i-1][j].setText(tablero[i][j].getText());
                        tablero[i][j].setIcon(imagen);
                        tablero[i][j].setText(null);
                        tablero[i-1][j].setIcon(null);
                        //aumetamos el contador de movimientos
                        numeroMovimientos++;
                        //mostramos el numero de movimientos 
                        txtMovimientos.setText(String.valueOf(numeroMovimientos));
                        //verifica el estado del juego por cada movimiento
                        ganarJuego();
                    } else if ( j-1 >= 0 && tablero[i][j-1].getIcon()!=null) {
                        tablero[i][j-1].setText(tablero[i][j].getText());
                        tablero[i][j].setIcon(imagen);
                        tablero[i][j].setText(null);
                        tablero[i][j-1].setIcon(null);
                        //aumetamos el contador de movimientos
                        numeroMovimientos++;
                        //mostramos el numero de movimientos 
                        txtMovimientos.setText(String.valueOf(numeroMovimientos));
                        //verifica el estado del juego por cada movimiento
                        ganarJuego();
                    } else if ( j+1 < columnas && tablero[i][j+1].getIcon()!=null) {
                        tablero[i][j+1].setText(tablero[i][j].getText());
                        tablero[i][j].setIcon(imagen);
                        tablero[i][j].setText(null);
                        tablero[i][j+1].setIcon(null);
                        //aumetamos el contador de movimientos
                        numeroMovimientos++;
                        //mostramos el numero de movimientos 
                        txtMovimientos.setText(String.valueOf(numeroMovimientos));
                        //verifica el estado del juego por cada movimiento
                        ganarJuego();
                    } else if ( i+1 <  filas && tablero[i+1][j].getIcon()!=null) {
                        tablero[i+1][j].setText(tablero[i][j].getText());
                        tablero[i][j].setIcon(imagen);
                        tablero[i][j].setText(null);
                        tablero[i+1][j].setIcon(null);
                        //aumetamos el contador de movimientos
                        numeroMovimientos++;
                        //mostramos el numero de movimientos 
                        txtMovimientos.setText(String.valueOf(numeroMovimientos));
                        //verifica el estado del juego por cada movimiento
                        ganarJuego();
                    }
                }
            }
        }
    }//fin del action preformed
    
    //crea el tablero con las dimensiones indicadas
    void crearTablero(JPanel panel,int f,int c){
        int numero = (f*c)-1;
        for(int i=0;i<f;i++) { 
            for(int j=0;j<c;j++){
                tablero[i][j]=new JButton();
                if (numeroAleatorios[i][j] != numero) {
                    tablero[i][j].setBackground(Color.WHITE);
                    tablero[i][j].setText(String.valueOf(numeroAleatorios[i][j]+1));
                } else {
                    tablero[i][j].setBackground(Color.WHITE);
                    tablero[i][j].setIcon(imagen);
                }
                panel.add(tablero[i][j]);
                //Asibnamos accion cada vez ue precionamos un boton
                tablero[i][j].addActionListener(this);
            }
        }
    }//fin del método crearTablero 
    
    //regresa el tablero al estado inicial
    void reiniciarJuego(){
        for(int i=0;i<filas;i++) { 
            for(int j=0;j<columnas;j++){
                if (numeroAleatorios[i][j] != (filas*columnas-1)) {
                    if (tablero[i][j].getIcon()!=null) {
                        tablero[i][j].setIcon(null);
                        tablero[i][j].setBackground(Color.WHITE);
                        tablero[i][j].setText(String.valueOf(numeroAleatorios[i][j]+1));                        
                    } else{
                        tablero[i][j].setBackground(Color.WHITE);
                        tablero[i][j].setText(String.valueOf(numeroAleatorios[i][j]+1));    
                    }
                } else {
                    tablero[i][j].setText(null);
                    tablero[i][j].setBackground(Color.WHITE);
                    tablero[i][j].setIcon(imagen);
                }
            }            
        }
        txtMovimientos.setText("0");//borra los movimientos del jugador
        numeroMovimientos = 0;//reinicia el contador de movimientos
    }//fin del metodo reiniciarJuego
    
    //ordena la lista de los jugadores de menor a mayor
    public static void ordenarLista(Jugador[] arreglo) {
        int in;
        for (int i = 1 ; i < arreglo.length ; i++) {
            if (listaJugadores[i] != null) {
                Jugador aux = arreglo[i];
                in = i;//inicia el desplazamiento en i
                while (in > 0 && arreglo[in - 1].getCantidadMovimientos() > aux.getCantidadMovimientos()) {
                    arreglo[in] = arreglo[in - 1];//desplaza el elemento hacia la derecha
                    --in;
                }
                arreglo[in] = aux;//inserta elemento
            }
        }
    }//fin del metodo ordenarLista
    
   //generamos numeros aleatorios
    void generarAleatorio (){
        int x = 0;
        int y = 0;
        int numero = -1;
        double x1; 
        double y1;
        
        //Pone la matriz de las fichas a -1. El número -1 indica que no tiene ficha
        for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                numeroAleatorios[i][j]=-1;
            }		  	
        }
        
        //Crear Numeros aleatorios. 
        for (int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                //Genera una posición aleatoria dentro de la matriz dada
                do{
                    x1=Math.random()*filas;
                    y1=Math.random()*columnas;
                    x=(int)x1;
                    y=(int)y1;
                }while(numeroAleatorios[x][y]!=-1);
                numero++;
                if (numero == filas*columnas) {
                    numero=0;
                }
                numeroAleatorios[x][y]=numero;
            }
        }
    }//fin del metodo genAleatorio
    
    //verifica por medio de comparacion de matrices ue el juego ha sido ganado
    void ganarJuego(){
        int aciertos = 0;//cuenta el numero de aciertos
        //recorremos el arreglo
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String temp = String.valueOf(numerosOrdenados[i][j]);
                if (tablero[i][j].getIcon()== null) {
                    if (temp.endsWith(tablero [i][j].getText())) {
                        aciertos++;
                    }
                }
            }
        }
        
        if (aciertos == filas*columnas-1) {
            if (cantidadJugadores <= 9) {
                JOptionPane.showMessageDialog(null,"FELICIDADES HAS GANADO!!!!!"+
                    "\nTotal movimientos: "+numeroMovimientos,"Mensaje",JOptionPane.INFORMATION_MESSAGE);
                //agrega los datos del jugador al ganar el juego
                listaJugadores[cantidadJugadores] = new Jugador(nombre,
                        filas+" X "+columnas,numeroMovimientos);
                ordenarLista(listaJugadores);
                cantidadJugadores++;//aumenta el numero de jugadores
                marcoTablero.dispose();
                btnReiniciar.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null,"La lista está llena",
                        "Advertencia",JOptionPane.WARNING_MESSAGE);
            }//fin del if...else           
        }        
    }//fin del metodo ganarJuego     
}//fin de la clase Tablero