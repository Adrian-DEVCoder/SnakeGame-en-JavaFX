package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Estadisticas extends Application {

    @Override
    public void start(Stage stage) {
        // Boton para volver al menu principal
        Button btnVolver = new Button();
        Image imagenBoton = new Image("file:volver.png");
        ImageView conImagenBoton = new ImageView(imagenBoton);
        conImagenBoton.setPreserveRatio(true);
        conImagenBoton.setFitWidth(20);
        btnVolver.setGraphic(conImagenBoton);
        btnVolver.setOnAction(event -> iniciarMenuPrincipal(stage));
        String estiloBoton = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;-fx-padding: 12px 12px;-fx-background-radius: 30;";
        btnVolver.setStyle(estiloBoton);
        // Titulo de marcadores
        Text titulo = new Text("MARCADORES");
        titulo.setFill(Color.WHITE);
        String estiloTituloE = "-fx-text-fill: #fff; -fx-font-size: 48";
        titulo.setStyle(estiloTituloE);
        // Contenedor para la parte superior de la ventana
        HBox superior = new HBox(20);
        superior.setAlignment(Pos.CENTER); // Alineación centrada horizontal
        superior.getChildren().addAll(btnVolver, titulo);
        superior.setPadding(new Insets(20, 20, 10, 20));
        // Lista para almacenar las puntuaciones del usuario
        List<RegistroPuntuacion> puntuaciones = obtenerPuntuacionesJSON();
        // Mostrar las 10 mejores puntuaciones en la interfaz gráfica
        VBox puntuacionesBox = new VBox();
        puntuacionesBox.setAlignment(Pos.CENTER);
        puntuacionesBox.setSpacing(8);
        String estiloMarcadores = "-fx-font-size: 24";
        puntuacionesBox.setStyle(estiloMarcadores);
        // Contenedor para la ventana
        VBox layoutInicio = new VBox(5, superior,puntuacionesBox);
        layoutInicio.setBackground(new Background(new BackgroundFill(Color.web("#26205C"), CornerRadii.EMPTY, Insets.EMPTY)));
        // Obtener las 10 mejores puntuaciones o menos si hay menos disponibles
        // Obtener las 10 mejores puntuaciones o menos si hay menos disponibles
        int numPuntuacionesAMostrar = Math.min(puntuaciones.size(), 10);
        for (int i = 0; i < numPuntuacionesAMostrar; i++) {
            RegistroPuntuacion puntuacion = puntuaciones.get(i);
            Text textPuntuacion = new Text((i + 1) + ". " + puntuacion.getNombreUsuario() + " - " + puntuacion.getPuntuacion());
            textPuntuacion.setFill(Color.WHITE);
            puntuacionesBox.getChildren().add(textPuntuacion);
        }

        // Escena para mostrar el contenido de la ventana
        Scene escena = new Scene(layoutInicio,500,500);
        stage.setScene(escena);
        stage.setTitle("Snake Game - Adrian Rodriguez Garcia");
        stage.show();
    }

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

    private void iniciarMenuPrincipal(Stage stage){
        Main main = new Main();
        main.start(stage);
    }
}
