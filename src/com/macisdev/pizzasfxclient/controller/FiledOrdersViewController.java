
package com.macisdev.pizzasfxclient.controller;

import com.macisdev.orders.Order;
import com.macisdev.pizzasfxclient.PizzasRestaurantClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FiledOrdersViewController implements Initializable {
	private static final ObservableList<Order> filedOrdersList = FXCollections.observableArrayList();

	@FXML
	private TableView<Order> orderTable;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		MainViewController.configureTable(orderTable, filedOrdersList, true);
	}

	void setFiledOrdersList (List<Order> orders) {
		filedOrdersList.clear();
		filedOrdersList.addAll(orders);
	}

	//Opens a window that shows the details of the selected order when
	//double click is done in a row of the table
	@FXML
    void openOrderDetailsWindow(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Order order = orderTable.getSelectionModel().getSelectedItem();
			openOrderDetailsWindow(order);
		}
    }

    //Opens a windows that shows the details of the selected orders
    void openOrderDetailsWindow(Order order) {
		try {
			//Creates a new window and sets its fxml file
			FXMLLoader fxmlLoader = new FXMLLoader(PizzasRestaurantClient.class.getResource("view/OrderDetailsView.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));

			//configures the new window
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pizza_icon.png")));
			stage.setResizable(false);
			stage.setTitle("Detalles del pedido");
			stage.initModality(Modality.NONE);
			stage.initStyle(StageStyle.DECORATED);

			//Gets the controller associated to the fxml to pass it the order selected
			OrderDetailsViewController detailsWindowController = fxmlLoader.getController();
			detailsWindowController.setOrder(order);
			detailsWindowController.loadOrder(false);

			//Shows the new window
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
