package edu.insightr.gildedrose;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public AnchorPane anchor;
    @FXML
    ListView<String> listView;
    @FXML
    Button updateButton;

    Inventory inventory;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        inventory = new Inventory();
        updateListView();
        updateButton.setOnAction((event) -> pushedButton());
    }

    private List<String> getItems() {
        List<String> list = new ArrayList<String>();
        for (Item item : inventory.getItems())
        {
            list.add(item.getName() + " " + "sell in:"+item.getSellIn() + " quality:" + item.getQuality());
        }
        return list;
    }

    private void updateListView()
    {
        ObservableList<String> items = FXCollections.observableArrayList(getItems());
        listView.setItems(items);
    }

    private void pushedButton()
    {
        inventory.updateQuality();
        updateListView();
    }

}
