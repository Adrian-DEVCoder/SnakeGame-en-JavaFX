package org.example;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends Application {

    @Override
    public void start(Stage pStage) {
        // Creación de un StackPane que servirá como contenedor principal
        StackPane panelInicio = new StackPane();
        panelInicio.setStyle("-fx-background-color: #26205C"); // Establece el color de fondo del StackPane

        // Carga y configura la imagen del logotipo del juego
        Image logo = new Image("file:./src/imagenes/logoSnake.gif");
        ImageView contenedorImagen = new ImageView(logo);
        contenedorImagen.setFitWidth(320); // Establece el ancho de la imagen
        contenedorImagen.setPreserveRatio(true); // Conserva la proporción original de la imagen

        //Creacion de un input para el nombre de usuario
        TextField txfUsuario = new TextField();

        //Estilo para el input del nombre de usuario
        String estiloTextField = "-fx-border-width: 2px; -fx-border-color: #413C70; -fx-border-radius: 6px;-fx-background-color: #26205C; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 15px";
        txfUsuario.setStyle(estiloTextField);
        txfUsuario.setPromptText("Nombre de Usuario");
        txfUsuario.setAlignment(Pos.CENTER);
        txfUsuario.setFocusTraversable(false);
        txfUsuario.setMaxWidth(200);

        // Creación de botones "Jugar" y "Salir del Juego"
        Button btnJugar = new Button("Jugar");
        Button btnEstadisticas = new Button("Estadísticas");
        Button btnConfiguracion = new Button("Configuración");
        Button btnCerrar = new Button("Cerrar Juego");

        // Estilo de los botones
        String estiloBotones = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 15px;";
        btnJugar.setStyle(estiloBotones);
        btnEstadisticas.setStyle(estiloBotones);
        btnConfiguracion.setStyle(estiloBotones);
        btnCerrar.setStyle(estiloBotones);
        btnJugar.setPrefWidth(150);
        btnEstadisticas.setPrefWidth(150);
        btnConfiguracion.setPrefWidth(150);
        btnCerrar.setPrefWidth(150);

        // Acciones al hacer clic en los botones
        btnJugar.setOnAction(event -> {
            String usuario = txfUsuario.getText();
            if (!usuario.isEmpty()) {
                iniciarJuego(pStage, usuario);
            } else {
                txfUsuario.setStyle("-fx-border-color: red; -fx-background-color: #26205C; -fx-border-width: 2px; -fx-border-radius: 6px; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 15px");
                // Crear una animación de translación horizontal para el campo de texto
                TranslateTransition tt = new TranslateTransition(Duration.millis(100), txfUsuario);
                tt.setByX(10); // Mover el campo de texto 10 píxeles a la derecha
                tt.setCycleCount(6); // Repetir la animación 6 veces
                tt.setAutoReverse(true); // Hacer que la animación se revierta automáticamente
                // Reproducir la animación
                tt.play();
            }
        });

        btnEstadisticas.setOnAction(event -> iniciarEstadisticas(pStage));
        btnConfiguracion.setOnAction(event -> iniciarConfiguracion(pStage));
        btnCerrar.setOnAction(actionEvent -> pStage.close()); // Cierra la ventana al hacer clic en "Salir del Juego"

        // Organizar los botones horizontalmente con un espacio de 20 entre ellos
        HBox botones = new HBox(20, btnJugar, btnEstadisticas);
        botones.setAlignment(Pos.CENTER); // Centrar los botones horizontalmente
        botones.setPadding(new Insets(20,20,20,20)); // Añadir relleno alrededor de los botones
        // Organizar los botones de configuracion y cerrar con un espacio de 20 entre ellos
        HBox botonesInferior = new HBox(20,btnConfiguracion,btnCerrar);
        botonesInferior.setAlignment(Pos.CENTER);
        botonesInferior.setPadding(new Insets(5,20,5,20));
        // Organizar el boton de cerrar juego horizontalmente debajo de los otros dos botones
        VBox cuerpo = new VBox(4,txfUsuario,botones,botonesInferior);
        cuerpo.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(40,20,10,20));
        // VBox para contener la imagen y los botones en la parte superior
        VBox vboxSuperior = new VBox(20, contenedorImagen);
        vboxSuperior.setAlignment(Pos.TOP_CENTER); // Centrar elementos verticalmente
        vboxSuperior.setPadding(new Insets(60, 20, 40, 20)); // Añadir relleno alrededor del VBox superior

        // Alineación del VBox superior en el StackPane
        StackPane.setAlignment(vboxSuperior, Pos.CENTER);

        // VBox para el layout principal que contiene la imagen y los botones
        VBox layoutInicio = new VBox(5, vboxSuperior, cuerpo);
        layoutInicio.setBackground(new Background(new BackgroundFill(Color.web("#26205C"), CornerRadii.EMPTY, Insets.EMPTY))); // Establecer el color de fondo del VBox principal

        // Crear la escena y configurarla en el Stage
        Scene escenaInicio = new Scene(layoutInicio, 500, 500); // Crear una escena con el VBox principal
        pStage.setScene(escenaInicio); // Establecer la escena en el Stage
        pStage.setTitle("Snake Game - Adrian Rodriguez Garcia"); // Establecer el título del Stage
        pStage.show(); // Mostrar el Stage con la escena configurada
    }

    private void iniciarJuego(Stage pStage,String usuario) {
        SnakeGame snakeGame = new SnakeGame(usuario);
        snakeGame.start(pStage);
    }

    private void iniciarEstadisticas(Stage stage) {
        Estadisticas estadisticas = new Estadisticas();
        estadisticas.start(stage);
    }

    private void iniciarConfiguracion(Stage stage) {
        Configuracion configuracion = new Configuracion();
        configuracion.start(stage);
    }
}