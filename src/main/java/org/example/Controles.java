package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.*;
import java.util.Properties;

public class Controles extends Application {

    @Override
    public void start(Stage stage) {
        StackPane panelInicio = new StackPane();
        panelInicio.setStyle("-fx-background-color: #26205C");
        // Boton para volver al menu principal
        Button btnVolver = new Button();
        Image imagenBoton = new Image("file:./src/imagenes/volver.png");
        ImageView conImagenBoton = new ImageView(imagenBoton);
        conImagenBoton.setPreserveRatio(true);
        conImagenBoton.setFitWidth(20);
        btnVolver.setGraphic(conImagenBoton);
        btnVolver.setOnAction(event -> iniciarMenuPrincipal(stage));
        String estiloBotonVolver = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 10px;-fx-background-radius: 30;";
        btnVolver.setStyle(estiloBotonVolver);
        // Titulo y estilo de este
        String estiloTitulo = "-fx-text-fill: #fff; -fx-font-size: 30";
        Text titulo = new Text("Configuración de Teclas:");
        titulo.setFill(Color.WHITE);
        titulo.setStyle(estiloTitulo);
        // Contenedor para el titulo y el boton de volver
        HBox contenedorTitulo = new HBox(20,btnVolver,titulo);
        contenedorTitulo.setAlignment(Pos.CENTER);
        // Text y estilo de los text asociados a los field para los movimientos
        String estiloMovimientosText = "-fx-text-fill: #fff; -fx-font-size: 22";
        Text arribaText = new Text("Arriba: ");
        arribaText.setFill(Color.WHITE);
        Text izquierdaText = new Text("Izquierda: ");
        izquierdaText.setFill(Color.WHITE);
        Text derechaText = new Text("Derecha: ");
        derechaText.setFill(Color.WHITE);
        Text abajoText = new Text("Abajo: ");
        abajoText.setFill(Color.WHITE);
        arribaText.setStyle(estiloMovimientosText);
        izquierdaText.setStyle(estiloMovimientosText);
        derechaText.setStyle(estiloMovimientosText);
        abajoText.setStyle(estiloMovimientosText);
        //TextField y estilo de los textfield asociados a los movimientos requeriran un escaner para detectar la tecla pulsado
        String estiloMovimientosField = "-fx-border-width: 2px; -fx-border-color: #413C70; -fx-border-radius: 6px;-fx-background-color: #26205C; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 10px";
        TextField arribaField = new TextField();
        TextField izquierdaField = new TextField();
        TextField derechaField = new TextField();
        TextField abajoField = new TextField();
        arribaField.setStyle(estiloMovimientosField);
        izquierdaField.setStyle(estiloMovimientosField);
        derechaField.setStyle(estiloMovimientosField);
        abajoField.setStyle(estiloMovimientosField);
        arribaField.setOnMouseClicked(event -> arribaField.clear());
        izquierdaField.setOnMouseClicked(event -> izquierdaField.clear());
        derechaField.setOnMouseClicked(event -> derechaField.clear());
        abajoField.setOnMouseClicked(event -> abajoField.clear());
        // Propiedades para los field
        Properties properties = new Properties();
        try(InputStream is = new FileInputStream("configuracion.properties")){
            properties.load(is);
            arribaField.setText(properties.getProperty("teclaArriba"));
            izquierdaField.setText(properties.getProperty("teclaIzquierda"));
            derechaField.setText(properties.getProperty("teclaDerecha"));
            abajoField.setText(properties.getProperty("teclaAbajo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Estilo y Boton para aplicar y resetear los cambios en la configuracion de teclas para el movimiento
        String estiloBoton = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 15px;";
        Button btnAplicar = new Button("Aplicar Cambios");
        Button btnRestaurar = new Button("Restaurar valores predeterminados");
        btnAplicar.setStyle(estiloBoton);
        btnAplicar.setPrefWidth(180);
        btnRestaurar.setStyle(estiloBoton);
        btnRestaurar.setPrefWidth(330);
        btnAplicar.setOnAction(event -> guardarTeclasConfiguracion(arribaField.getText(),izquierdaField.getText(),derechaField.getText(),abajoField.getText()));
        btnRestaurar.setOnAction(event -> restaurarValoresPredeterminados(arribaField,izquierdaField,derechaField,abajoField));
        // Contenedor vertical para los nombres de movimientos
        VBox contenedorMovimientosText = new VBox(36,arribaText,izquierdaText,derechaText,abajoText);
        contenedorMovimientosText.setAlignment(Pos.CENTER);
        // Contenedor vertical para los field donde introducimos las teclas de movimiento
        VBox contenedorMovimientoField = new VBox(20,arribaField,izquierdaField,derechaField,abajoField);
        contenedorMovimientoField.setAlignment(Pos.CENTER);
        // Contenedor para los movimientos tanto field ocmo text
        HBox contenedorMovimientos = new HBox(20,contenedorMovimientosText,contenedorMovimientoField);
        contenedorMovimientos.setAlignment(Pos.CENTER);
        // Contenedor horizontal para los botones en la ventana o vertical (Hacer prueba de distribucion)
        VBox contenedorBotones = new VBox(20,btnAplicar,btnRestaurar);
        contenedorBotones.setAlignment(Pos.CENTER);
        // Contenedor vertical para la ventana
        VBox contenedor = new VBox(20,contenedorTitulo,contenedorMovimientos,contenedorBotones);
        contenedor.setBackground(new Background(new BackgroundFill(Color.web("#26205C"), CornerRadii.EMPTY, Insets.EMPTY)));
        contenedor.setAlignment(Pos.CENTER);
        // Escena para mostrar el contenido de la ventana
        Scene escena = new Scene(contenedor,500,500);
        escena.setOnKeyReleased(event -> {
            String tecla = event.getCode().toString();
            TextField textField = (TextField) escena.getFocusOwner();
            if(textField != null){
                textField.setText(tecla);
            }
        });
        stage.setScene(escena);
        stage.setTitle("Snake Game - Adrián Rodriguez Garcia");
        stage.show();
    }

    private void iniciarMenuPrincipal(Stage stage) {
        Main main = new Main();
        main.start(stage);
    }

    private void guardarTeclasConfiguracion(String arriba, String izquierda, String derecha, String abajo){
        Properties properties = new Properties();
        try(OutputStream os = new FileOutputStream("configuracion.properties")){
            properties.setProperty("teclaArriba",arriba);
            properties.setProperty("teclaIzquierda",izquierda);
            properties.setProperty("teclaDerecha",derecha);
            properties.setProperty("teclaAbajo",abajo);
            properties.store(os,"configuracion de teclas personalizadas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void restaurarValoresPredeterminados(TextField arriba, TextField izquierda, TextField derecha, TextField abajo){
        Properties properties = new Properties();
        try(OutputStream os = new FileOutputStream("configuracion.properties")){
            properties.setProperty("teclaArriba","UP");
            properties.setProperty("teclaIzquierda","LEFT");
            properties.setProperty("teclaDerecha","RIGHT");
            properties.setProperty("teclaAbajo","DOWN");
            properties.store(os,"Valores predeterminados restaurados");
            arriba.setText(properties.getProperty("teclaArriba"));
            izquierda.setText(properties.getProperty("teclaIzquierda"));
            derecha.setText(properties.getProperty("teclaDerecha"));
            abajo.setText(properties.getProperty("teclaAbajo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
