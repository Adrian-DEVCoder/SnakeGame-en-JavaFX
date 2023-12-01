package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Derrota extends Application {
    private String nombreUsuario;
    public Derrota(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    @Override
    public void start(Stage stage) throws Exception {
        // Titulo para la derrota
        Text titulo = new Text("HAS PERDIDO");
        String estiloTitulo = "-fx-text-fill: white; -fx-stroke: black; -fx-stroke-width: 1.5; -fx-font-family: 'Lucida Sans Unicode'; -fx-font-size: 54; -fx-font-weight: bold";
        titulo.setFill(Color.WHITE);
        titulo.setStyle(estiloTitulo);

        Button btnReiniciar = new Button("Reiniciar");
        Button btnVolver = new Button("Menu Principal");
        btnReiniciar.setPrefWidth(150);
        btnVolver.setPrefWidth(150);
        btnReiniciar.setOnAction(event -> iniciarJuego(stage,nombreUsuario));
        btnVolver.setOnAction(event -> iniciarMenuPrincipal(stage));
        String estiloBotones = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 15px;";
        btnReiniciar.setStyle(estiloBotones);
        btnVolver.setStyle(estiloBotones);

        VBox vboxSuperior = new VBox(40, titulo);
        vboxSuperior.setAlignment(Pos.TOP_CENTER); // Centrar elementos verticalmente
        vboxSuperior.setPadding(new Insets(105, 20, 20, 20)); // Añadir relleno alrededor del VBox superior

        // Alineación del VBox superior en el StackPane
        StackPane.setAlignment(vboxSuperior, Pos.CENTER);

        HBox botones = new HBox(20,btnReiniciar,btnVolver);
        botones.setAlignment(Pos.BOTTOM_CENTER);
        botones.setPadding(new Insets(20));

        VBox layoutDerrota = new VBox(40,vboxSuperior,botones);
        layoutDerrota.setBackground(new Background(new BackgroundFill(Color.valueOf("#26205C"),CornerRadii.EMPTY,Insets.EMPTY)));
        Scene escena = new Scene(layoutDerrota,500,500);
        stage.setScene(escena);
        stage.setTitle("Snake Game - Adrian Rodriguez Garcia");
        stage.show();
    }

    private void iniciarJuego(Stage stage,String usuario){
        SnakeGame snakeGame = new SnakeGame(usuario);
        snakeGame.start(stage);
    }

    private void iniciarMenuPrincipal(Stage stage){
        Main main = new Main();
        main.start(stage);
    }
}
