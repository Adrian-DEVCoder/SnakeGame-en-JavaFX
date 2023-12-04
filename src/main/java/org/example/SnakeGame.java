package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SnakeGame extends Application {
    private static final int tamanoSerpiente = 20;
    private static final int ancho = 25;
    private static final int alto = 25;
    private Direccion direccion = Direccion.RIGHT;
    private List<Position> serpiente = new ArrayList<>();
    private boolean direccionCambiada;
    private boolean derrota;
    private Position comida;
    private int marcador;
    private Text textoMarcador;
    private Text maximaPuntuacion;
    private int puntuacion = 0;
    private String nombreUsuario;

    public SnakeGame(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) {
        // Inicializamos el tablero, textos de marcadores, escenas, listener para las teclas y movimientos
        // ademas iniciamos el juego y el bucle principal de este.
        Canvas tablero = new Canvas(ancho * tamanoSerpiente, alto * tamanoSerpiente);
        // Text para el marcador con la puntuacion
        textoMarcador = new Text();
        textoMarcador.setFill(Color.WHITE);
        textoMarcador.setFont(Font.font("verdana", FontPosture.REGULAR,15));
        actualizarMarcador();
        // Text para el marcador con la puntuacion mas alta
        maximaPuntuacion = new Text();
        maximaPuntuacion.setFill(Color.WHITE);
        maximaPuntuacion.setFont(Font.font("verdana", FontPosture.REGULAR,15));
        actualizarPuntuacionMaxima();
        // Grid para colocar los texts
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, textoMarcador);
        GridPane.setMargin(textoMarcador, new Insets(0, 60, 0, 0));
        gridPane.addColumn(1, maximaPuntuacion);
        GridPane.setMargin(maximaPuntuacion, new Insets(0, 0, 0, 20));
        // Set de escena, paneles y contextos
        GraphicsContext contextTablero = tablero.getGraphicsContext2D();
        StackPane panel = new StackPane(tablero,gridPane);
        Scene escena = new Scene(panel);
        // Color de fondo ventana
        panel.setStyle("-fx-background-color: #26205C");
        escena.setOnKeyReleased(keyEvent -> {
            if (!direccionCambiada) {
                List<String> teclas = obtenerTeclasConfiguracion();

                String arribaT = teclas.get(0);
                String izquierdaT = teclas.get(1);
                String derechaT = teclas.get(2);
                String abajoT = teclas.get(3);

                String teclaPresionada = keyEvent.getCode().toString();

                boolean movValido = true;

                if ((direccion == Direccion.UP && teclaPresionada.equals(abajoT)) ||
                        (direccion == Direccion.DOWN && teclaPresionada.equals(arribaT)) ||
                        (direccion == Direccion.LEFT && teclaPresionada.equals(derechaT)) ||
                        (direccion == Direccion.RIGHT && teclaPresionada.equals(izquierdaT))) {
                    movValido = false;
                }

                if (movValido) {
                    if (teclaPresionada.equals(arribaT)) {
                        direccion = Direccion.UP;
                    } else if (teclaPresionada.equals(abajoT)) {
                        direccion = Direccion.DOWN;
                    } else if (teclaPresionada.equals(izquierdaT)) {
                        direccion = Direccion.LEFT;
                    } else if (teclaPresionada.equals(derechaT)) {
                        direccion = Direccion.RIGHT;
                    }
                    direccionCambiada = true;
                }
            }
        });


        // Titulo de la ventana y set de la escena a mostrar
        stage.setScene(escena);
        stage.setTitle("Snake Game - Adrián Rodríguez García");
        stage.show();

        // Iniciamos el juego
        iniciarJuego();
        iniciarBuclePrincipal(contextTablero,stage);
    }

    // Metodo para actualizar el marcador de la puntuacion maxima
    private void actualizarPuntuacionMaxima() {
        maximaPuntuacion.setText("\t\tMáxima puntuacion: "+puntuacion);
    }

    // Metodo para actualizar el marcador
    private void actualizarMarcador() {
        textoMarcador.setText("\tMarcador: "+marcador);
    }

    // Metodo para iniciar el bucle principal del juego
    private void iniciarBuclePrincipal(GraphicsContext gc,Stage stage) {
        new AnimationTimer() {

            long ultimaActualizacion = 0;

            // Metodo para la actualizacion del juego
            @Override
            public void handle(long now) {
                if(now - ultimaActualizacion >= 100_000_000){
                    ultimaActualizacion = now;
                    if(!derrota){
                        direccionCambiada = false;
                        try {
                            actualizar(stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        dibujar(gc);
                    } else {
                        stop();
                    }
                }
            }
        }.start();

    }

    // Metodo para iniciar el juego
    private void iniciarJuego(){
        marcador = 0;
        actualizarMarcador();
        serpiente.clear();
        serpiente.add(new Position(ancho/2,alto/2));
        direccion = Direccion.RIGHT;
        spawnComida();
        derrota = false;
        direccionCambiada = false;
    }

    // Metodo para spawnear comida
    private void spawnComida(){
        int x = (int) (Math.random() * ancho);
        int y = (int) (Math.random() * alto);
        comida = new Position(x,y);
    }

    // Metodo para actualizar dentro del juego (movimientos, tamaño, derrota)
    private void actualizar(Stage stage) throws Exception {
        Position cabeza = serpiente.get(0).copiar();
        switch (direccion){
            case UP:
                cabeza.y--;
                break;
            case DOWN:
                cabeza.y++;
                break;
            case LEFT:
                cabeza.x--;
                break;
            case RIGHT:
                cabeza.x++;
                break;
        }
        // Condicion para cuando la cabeza pase por la comida que aumente el tamaño de la serpiente
        if(cabeza.equals(comida)){
            serpiente.add(0, cabeza);
            marcador++;
            if(marcador > puntuacion){
                puntuacion = marcador;
                actualizarPuntuacionMaxima();
            }
            actualizarMarcador();
            spawnComida();
        } else {
            // Condicion para cuando la serpiente choca con las colisiones
            if(cabeza.x < 0 || cabeza.x >= ancho || cabeza.y < 0 || cabeza.y >= alto){
                derrota = true;
                iniciarDerrota(stage,marcador,derrota);
                return;
            }

            // Condicion para cuando la serpiente choca con su propio cuerpo
            for(Position cuerpo : serpiente){
                if(cabeza.equals(cuerpo)){
                    derrota = true;
                    iniciarDerrota(stage,marcador,derrota);
                    return;
                }
            }

            serpiente.remove(serpiente.size() - 1);
            serpiente.add(0, cabeza);

        }
    }

    // Metodo para dibujar la comida y la serpiente
    private void dibujar(GraphicsContext gc) {
        gc.clearRect(0,0,ancho * tamanoSerpiente, alto * tamanoSerpiente);
        gc.setFill(Color.RED);
        gc.fillOval(comida.x * tamanoSerpiente, comida.y * tamanoSerpiente, tamanoSerpiente, tamanoSerpiente);
        gc.setFill(Color.valueOf("#24694D"));
        for(Position cuerpo : serpiente){
            gc.fillOval(cuerpo.x * tamanoSerpiente, cuerpo.y * tamanoSerpiente, tamanoSerpiente, tamanoSerpiente);
        }
    }

    private void iniciarDerrota(Stage stage, int puntos, boolean derrotado) throws Exception {
        Derrota derrota1 = new Derrota(nombreUsuario);
        derrota1.start(stage);
        if (derrotado) {
            // Crear un objeto RegistroPuntuacion con el nombre de usuario y la puntuación
            RegistroPuntuacion registro = new RegistroPuntuacion(nombreUsuario, puntos);

            // Obtener la lista de puntuaciones existente o una lista vacía si el archivo no existe
            List<RegistroPuntuacion> puntuaciones = obtenerPuntuacionesJSON();

            // Agregar el nuevo registro a la lista
            puntuaciones.add(registro);

            // Ordenar la lista por puntajes en orden descendente
            puntuaciones.sort(Collections.reverseOrder());

            // Limitar la lista a los 10 primeros elementos si es más grande
            if (puntuaciones.size() > 10) {
                puntuaciones = puntuaciones.subList(0, 10);
            }

            // Escribir la lista actualizada al archivo JSON
            guardarPuntuacionesJSON(puntuaciones);
        }
    }
    // Metodo para obtener las puntuaciones dentro del archivo JSON
    private List<RegistroPuntuacion> obtenerPuntuacionesJSON() {
        Gson gson = new Gson();
        File archivo = new File("marcadores.json");

        if (archivo.exists()) {
            try (FileReader reader = new FileReader(archivo)) {
                Type tipoLista = new TypeToken<List<RegistroPuntuacion>>(){}.getType();
                return gson.fromJson(reader, tipoLista);
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de puntuaciones.");
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }
    // Metodo para guardar las puntuaciones dentro del archivo JSON
    private void guardarPuntuacionesJSON(List<RegistroPuntuacion> puntuaciones){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter fw = new FileWriter("marcadores.json")){
            gson.toJson(puntuaciones, fw);
        } catch (IOException e) {
            System.out.println("No se ha podido crear el archivo.");
        }
    }

    private List<String> obtenerTeclasConfiguracion(){
        Properties properties = new Properties();
        List<String> teclasAsignadas = new ArrayList<>();
        try(InputStream is = new FileInputStream("configuracion.properties")){
            properties.load(is);
            String arriba = properties.getProperty("teclaArriba");
            String izquierda = properties.getProperty("teclaIzquierda");
            String derecha = properties.getProperty("teclaDerecha");
            String abajo = properties.getProperty("teclaAbajo");
            teclasAsignadas.add(arriba);
            teclasAsignadas.add(izquierda);
            teclasAsignadas.add(derecha);
            teclasAsignadas.add(abajo);
                    } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return teclasAsignadas;
    }

}
