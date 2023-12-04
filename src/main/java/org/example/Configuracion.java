package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Configuracion extends Application {

    @Override
    public void start(Stage stage) {
        // Titulos para las diferentes opciones
        Text tituloComida = new Text("Comida");
        Text tituloVelocidad = new Text("Velocidad");
        Text tituloGeneracionComida = new Text("Generación");
        Text tituloTemas = new Text("Tema");
        // Estilo para los titulos de las diferentes opciones
        String estiloTitulos = "-fx-text-fill: white; -fx-font-size: 22";
        tituloComida.setStyle(estiloTitulos);
        tituloComida.setFill(Color.WHITE);
        tituloVelocidad.setStyle(estiloTitulos);
        tituloVelocidad.setFill(Color.WHITE);
        tituloGeneracionComida.setStyle(estiloTitulos);
        tituloGeneracionComida.setFill(Color.WHITE);
        tituloTemas.setStyle(estiloTitulos);
        tituloTemas.setFill(Color.WHITE);
        // Combobox de las diferentes frutas
        ComboBox<Image> imagenesComida = new ComboBox<>();
        imagenesComida.getItems().addAll(
                new Image("file:./src/imagenes/manzana.png"),
                new Image("file:./src/imagenes/naranja.png"),
                new Image("file:./src/imagenes/pera.png"),
                new Image("file:./src/imagenes/cereza.png"),
                new Image("file:./src/imagenes/fresa.png"),
                new Image("file:./src/imagenes/limon.png"),
                new Image("file:./src/imagenes/uvas.png"),
                new Image("file:./src/imagenes/frutaAleatoria.png")
        );
        imagenesComida.setButtonCell(new ImageListCell());
        imagenesComida.setCellFactory(imageListView -> new ImageListCell());
        // Combobox de las diferentes velocidades
        ComboBox<Image> imagenesVelocidad = new ComboBox<>();
        imagenesVelocidad.getItems().addAll(
                new Image("file:./src/imagenes/gusano.png"),
                new Image("file:./src/imagenes/tortuga.png"),
                new Image("file:./src/imagenes/conejo.png")
        );
        imagenesVelocidad.setButtonCell(new ImageListCell());
        imagenesVelocidad.setCellFactory(imageListView -> new ImageListCell());
        // Combobox para las diferentes generaciones de comida
        ComboBox<Image> imagenesGeneracion = new ComboBox<>();
        imagenesGeneracion.getItems().addAll(
                new Image("file:./src/imagenes/punto.png"),
                new Image("file:./src/imagenes/dosPuntos.png"),
                new Image("file:./src/imagenes/tresPuntos.png"),
                new Image("file:./src/imagenes/puntoAleatorio.png")
        );
        imagenesGeneracion.setButtonCell(new ImageListCell());
        imagenesGeneracion.setCellFactory(imageListView -> new ImageListCell());
        // Combobox para los diferentes temas
        ComboBox<Image> imagenesTemas = new ComboBox<>();
        imagenesTemas.getItems().addAll(
                new Image("file:./src/imagenes/defecto.png"),
                new Image("file:./src/imagenes/tRojo.png"),
                new Image("file:./src/imagenes/tVerde.png"),
                new Image("file:./src/imagenes/tAzul.png"),
                new Image("file:./src/imagenes/tMarron.png"),
                new Image("file:./src/imagenes/tGris.png"),
                new Image("file:./src/imagenes/tAzul2.png"),
                new Image("file:./src/imagenes/tRojo2.png")
        );
        imagenesTemas.setButtonCell(new ImageListCell());
        imagenesTemas.setCellFactory(imageListView -> new ImageListCell());
        // Organizamos las diferentes opciones
        HBox contenedorComida = new HBox(5,tituloComida,imagenesComida);
        contenedorComida.setAlignment(Pos.CENTER);
        HBox contenedorVelocidad = new HBox(5,tituloVelocidad,imagenesVelocidad);
        contenedorVelocidad.setAlignment(Pos.CENTER);
        HBox contenedorGeneracionC = new HBox(5,tituloGeneracionComida,imagenesGeneracion);
        contenedorGeneracionC.setAlignment(Pos.CENTER);
        HBox contenedorTemas = new HBox(5,tituloTemas,imagenesTemas);
        contenedorTemas.setAlignment(Pos.CENTER);
        // Organizamos los diferentes ComboBox
        VBox general = new VBox(5,contenedorComida,contenedorVelocidad,contenedorGeneracionC,contenedorTemas);
        VBox layoutInicio = new VBox(5,general);
        layoutInicio.setBackground(new Background(new BackgroundFill(Color.web("#26205C"), CornerRadii.EMPTY, Insets.EMPTY)));
        // Escena para mostrar el contenido de la ventana
        Scene escena = new Scene(layoutInicio,500,500);
        stage.setScene(escena);
        stage.setTitle("Snake Game - Adrian Rodriguez Garcia");
        stage.show();
    }

    // Metodo para que se visualicen las imagenes de los combobox
    private static class ImageListCell extends ListCell<Image> {
        private final ImageView imageView = new ImageView();

        @Override
        protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage(item);
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
                setGraphic(imageView);
            }
        }
    }
}
