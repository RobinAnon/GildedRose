package edu.insightr.gildedrose;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Hashtable;

public class Main extends Application {

    Inventory inventaire = new Inventory();
    Stage window;
    TableView<Item> table;
    PieChart chart = new PieChart();


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
        vbox.getChildren().addAll(chart);
        updatePieChart();

        Scene scene = new Scene(vbox);
        window.setScene(scene);

        window.show();
    }

    public void updateButtonClicked(){
        inventaire.updateQuality();
        updatePieChart();
        table.refresh();
    }

    public void updatePieChart()
    {
        Hashtable<String, Integer> quantities = new Hashtable<>();
        String[] tab = { "+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros",
                "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake" };
        for (String type : tab )
        {
            int quantity = 0;
            for (Item item : inventaire.getItems())
            {
                if (item.getName().equals(type))
                    quantity++;
            }
            quantities.put(type, quantity);
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Vest", quantities.get("+5 Dexterity Vest")),
                        new PieChart.Data("Aged Brie", quantities.get("Aged Brie")),
                        new PieChart.Data("Elixir", quantities.get("Elixir of the Mongoose")),
                        new PieChart.Data("Sulfuras", quantities.get("Sulfuras, Hand of Ragnaros")),
                        new PieChart.Data("Backstage passes", quantities.get("Backstage passes to a TAFKAL80ETC concert")),
                        new PieChart.Data("Mana Cake", quantities.get("Conjured Mana Cake")));
        chart.setData(pieChartData);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
