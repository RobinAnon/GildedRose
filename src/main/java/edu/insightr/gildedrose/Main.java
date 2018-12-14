package edu.insightr.gildedrose;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        Button importButton = new Button("Import");
        importButton.setOnAction(e -> importButtonClicked());

        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");

        ComboBox combo= new ComboBox<String>();
        combo.getItems().addAll(
                "+5 Dexterity Vest",
                "Aged Brie","Elixir of the Mongoose",
                "Sulfuras, Hand of Ragnaros",
                "Backstage passes to a TAFKAL80ETC concert",
                "Conjured Mana Cake");
        combo.setPromptText("What item add ?");

        TextField sellInInput= new TextField();
        TextField qualityInput= new TextField();
        sellInInput.setPromptText("sell in");
        qualityInput.setPromptText("quality");

        DatePicker datePicker = new DatePicker();

        addButton.setOnAction(e -> addButtonClicked(combo,sellInInput,qualityInput, datePicker.getValue()));
        removeButton.setOnAction(e -> removeButtonClicked(datePicker.getValue()));

        Button barChartDateButton = new Button("barChartDate");
        barChartDateButton.setOnAction(e-> barChartDateButtonClicked());

        Button barChartSellIn = new Button("barChartSellIn");
        barChartSellIn.setOnAction(e-> barChartSellInButtonClicked());

        Button barChartNumberOfBuy= new Button("barChartNumberOfBuy");
        barChartNumberOfBuy.setOnAction(e-> barChartNumberOfBuyClicked());

        Button barChartNumberOfSell= new Button("barChartNumberOfSell");
        barChartNumberOfSell.setOnAction(e-> barChartNumberOfSellClicked());

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(importButton, updateButton, addButton, removeButton, sellInInput, qualityInput,datePicker,barChartDateButton,barChartSellIn, barChartNumberOfBuy,barChartNumberOfSell);

        table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(inventaire.getItems()));
        table.getColumns().addAll(nameColumn, sellinColumn, qualityColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, hbox, combo, chart);
        updatePieChart();


        Scene scene = new Scene(vbox);
        window.setScene(scene);

        window.show();
    }

    private void importButtonClicked() {
        Item[] inv = JSONReaderClass.readJSON("D:\\ESILV\\Matieres\\Semestre_7\\Design Pattern\\json_invntory.json");
        int taille = inv.length;
        for (int i = 0; i < taille; i++) {
            inventaire.AddItem(inv[i]);
        }
        table.refresh();
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

    public void addButtonClicked(ComboBox<String> combo, TextField sellInInput, TextField qualityInput, LocalDate date)
    {
        inventaire.AddItem(combo.getValue(),Integer.parseInt(sellInInput.getText()), Integer.parseInt(qualityInput.getText()), date);
        table.setItems(FXCollections.observableArrayList(inventaire.getItems()));

        sellInInput.clear();
        qualityInput.clear();
        table.refresh();
    }

    public void removeButtonClicked(LocalDate date)
    {
        ObservableList<Item> itemSelected = table.getSelectionModel().getSelectedItems();
        itemSelected.get(0).setDate(date);
        inventaire.RemoveItem(itemSelected.get(0));

        table.setItems(FXCollections.observableArrayList(inventaire.getItems()));
        table.refresh();
    }

    public void barChartDateButtonClicked()
    {
        Stage stage=new Stage();

        stage.setTitle("BarChart Date");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart=new BarChart<String,Number>(xAxis,yAxis);
        barChart.setTitle("Number Of Items VS Creation date");
        xAxis.setLabel("Creation Date");
        yAxis.setLabel("Number of items");


        XYChart.Series series2=new XYChart.Series();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        //String formattedString = localDate.format(formatter);

        Item[] items=inventaire.getItems();

        ArrayList listDate= new ArrayList();
        ArrayList listNumber= new ArrayList();



        for(int i=0;i<items.length;i++)
        {
            listDate.add(items[i].getDate());
            listNumber.add(inventaire.NumberElementsDatei(items[i].getDate()));
        }

        for(int i=0; i<listDate.size();i++)
        {
            for(int j=i+1; j<listDate.size();j++)
            {
                if(listDate.get(i)==listDate.get(j))
                {
                    listDate.remove(j);
                    listNumber.remove(j);
                }
            }
        }


        for(int i=0;i<listDate.size();i++)
        {
            LocalDate li=(LocalDate)(listDate.get(i));
            series2.getData().add(new XYChart.Data( li.format(formatter), listNumber.get(i)));
        }



        Scene scene  = new Scene(barChart,800,600);
        barChart.getData().addAll(series2);
        stage.setScene(scene);
        stage.show();



    }



    public void barChartSellInButtonClicked()
    {
        Stage stage=new Stage();

        stage.setTitle("BarChart SellIn");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart=new BarChart<String,Number>(xAxis,yAxis);
        barChart.setTitle("Number of items VS SellIn");
        xAxis.setLabel("SellIn");
        yAxis.setLabel("Number of items");


        XYChart.Series series1=new XYChart.Series();


        Item[] items=inventaire.getItems();


        for(int i=0;i<items.length;i++)
        {
            series1.getData().add(new XYChart.Data( String.valueOf(items[i].getSellIn()), inventaire.NumberElementsSellIn(items[i].getSellIn())));
        }



        Scene scene  = new Scene(barChart,800,600);
        barChart.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();



    }

    public void barChartNumberOfBuyClicked()
    {
        Stage stage=new Stage();

        stage.setTitle("BarChart Number Of Buy");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart=new BarChart<String,Number>(xAxis,yAxis);
        barChart.setTitle("Number of buy VS Add date");
        xAxis.setLabel("Add date");
        yAxis.setLabel("Number of buy");


        XYChart.Series series3=new XYChart.Series();


        Item[] items=inventaire.getItems().clone();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        LocalDate min;
        Item temp;
        int indicemin;
        for(int i=0;i<items.length;i++)
        {
            min=items[i].getDate();
            indicemin=i;
            for(int j=i+1;j<items.length;j++)
            {
                if(min.compareTo(items[j].getDate())>0)
                {
                    min=items[j].getDate();
                    indicemin=j;
                }
            }

            temp=items[i];
            items[i]=items[indicemin];
            items[indicemin]=temp;

        }
        Number [] tabNumberElementsBuyDated = new Number[items.length];
        for(int i=0;i<items.length;i++)
        {
            tabNumberElementsBuyDated[i]=inventaire.NumberElementsDatei(items[i].getDate());
        }

        for(int i=0;i<items.length;i++)
        {
            for(int j=i+1;j<items.length;j++)
            {
                if(items[i]!=null & items[j]!=null)
                {
                    if((items[i].getDate()).compareTo(items[j].getDate())==0)
                    {
                        items[j]=null;
                        tabNumberElementsBuyDated[j]=0;
                    }
                }
            }
        }

        int num=0;
        for(int i=0;i<items.length;i++)
        {
            if(items[i]!=null)
            {
                num+=(Integer)tabNumberElementsBuyDated[i];
                series3.getData().add(new XYChart.Data((items[i].getDate()).format(formatter) , num));
            }
        }

        Scene scene  = new Scene(barChart,800,600);
        barChart.getData().addAll(series3);
        stage.setScene(scene);
        stage.show();
    }

    public void barChartNumberOfSellClicked()
    {
        Stage stage=new Stage();

        stage.setTitle("BarChart Number Of Sell");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart=new BarChart<String,Number>(xAxis,yAxis);
        barChart.setTitle("Number of sell VS Sell_date");
        xAxis.setLabel("Sell date");
        yAxis.setLabel("Number of sell");


        XYChart.Series series4=new XYChart.Series();


        Item[] itemsSell=inventaire.getItemsSell().clone();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        LocalDate min;
        Item temp;
        int indicemin;
        for(int i=0;i<itemsSell.length;i++)
        {
            min=itemsSell[i].getDate();
            indicemin=i;
            for(int j=i+1;j<itemsSell.length;j++)
            {
                if(min.compareTo(itemsSell[j].getDate())>0)
                {
                    min=itemsSell[j].getDate();
                    indicemin=j;
                }
            }

            temp=itemsSell[i];
            itemsSell[i]=itemsSell[indicemin];
            itemsSell[indicemin]=temp;

        }
        Number [] tabNumberElementsSellDated = new Number[itemsSell.length];
        for(int i=0;i<itemsSell.length;i++)
        {
            tabNumberElementsSellDated[i]=inventaire.NumberElementsSellDated(itemsSell[i].getDate());
        }

        for(int i=0;i<itemsSell.length;i++)
        {
            for(int j=i+1;j<itemsSell.length;j++)
            {
                if(itemsSell[i]!=null & itemsSell[j]!=null)
                {
                    if((itemsSell[i].getDate()).compareTo(itemsSell[j].getDate())==0)
                    {
                        itemsSell[j]=null;
                        tabNumberElementsSellDated[j]=0;
                    }
                }
            }
        }

        int num=0;
        for(int i=0;i<itemsSell.length;i++)
        {
            if(itemsSell[i]!=null)
            {
                num+=(Integer)tabNumberElementsSellDated[i];
                series4.getData().add(new XYChart.Data((itemsSell[i].getDate()).format(formatter) , num));
            }
        }

        Scene scene  = new Scene(barChart,800,600);
        barChart.getData().addAll(series4);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
