package edu.insightr.gildedrose;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    Inventory inventaire = new Inventory();
    Stage window;
    TableView<Item> table;


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Gilded Rose INN interface");

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name :");
        nameColumn.setMinWidth(400);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

        TableColumn<Item, String> sellinColumn = new TableColumn<>("SellIn :");
        sellinColumn.setMinWidth(50);
        sellinColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("sellIn"));

        TableColumn<Item, String> qualityColumn = new TableColumn<>("Quality :");
        qualityColumn.setMinWidth(50);
        qualityColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("quality"));

        //Button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateButtonClicked());

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().add(updateButton);


        table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(inventaire.getItems()));
        table.getColumns().addAll(nameColumn, sellinColumn, qualityColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, hbox);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.show();
    }

    public void updateButtonClicked(){
        inventaire.updateQuality();
        table.refresh() ;
    }


    public static void main(String[] args) {
        launch(args);
    }
}