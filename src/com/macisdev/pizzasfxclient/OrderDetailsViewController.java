/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macisdev.pizzasfxclient;

import com.macisdev.pizzasfxclient.models.Order;
import com.macisdev.pizzasfxclient.models.OrderElement;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrderDetailsViewController implements Initializable {
	//The list of elements of the order
	private final ObservableList<OrderElement> orderElementsList = FXCollections.observableArrayList();
	//Contains the current order of this window
	private Order order;
	
	@FXML
    private TextField tfCustomerAddress;

    @FXML
    private TextField tfCustomerPhone;

    @FXML
    private TextField tfTotalPrice;

    @FXML
    private TextField tfOrderId;

    @FXML
    private TextField tfCustomerName;

    @FXML
    private TextField tfPaymentMethod;

	@FXML
	private TextField tfDate;

	@FXML
	private TextField tfTime;
	
	@FXML
    private TableView<OrderElement> elementsTable;

	@Override
	public void initialize(URL url, ResourceBundle rb) {		
		//Creates the columns of the table
		TableColumn codeColumn = new TableColumn("Cod. producto");
		codeColumn.setCellValueFactory(new PropertyValueFactory("code"));
		
		TableColumn nameColumn = new TableColumn("Nombre");
		nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
		
		TableColumn sizeColumn = new TableColumn("Tamaño");
		sizeColumn.setCellValueFactory(new PropertyValueFactory("size"));
		
		TableColumn extrasColumn = new TableColumn("Extras");
		extrasColumn.setCellValueFactory(new PropertyValueFactory("extras"));
		
		TableColumn priceColumn = new TableColumn("Precio");
		priceColumn.setCellValueFactory(new PropertyValueFactory("price"));

		//Adds the columns created to the table
		elementsTable.getColumns().addAll(codeColumn, nameColumn, sizeColumn, extrasColumn, priceColumn);
	}	

	//Used by the main window to inform the order selected
	public void setOrder(Order order) {
		this.order = order;
	}
	
	/* Loads the data of the order selected in the main table.
	 * This can't be done in the initialize method because that
	 * method is called	before the controller knows what order to load.
	 */
	public void loadOrder() {
		//Loads the info to the textfields
		tfOrderId.setText(order.getOrderId());
		tfCustomerName.setText(order.getCustomerName());
		tfCustomerPhone.setText(order.getCustomerPhone());
		tfCustomerAddress.setText(order.getCustomerAddress());
		tfPaymentMethod.setText(order.getPaymentMethod());
		tfTotalPrice.setText(order.getTotalPrice());
		tfDate.setText(order.getOrderDateTime().substring(0, 10));
		tfTime.setText(order.getOrderDateTime().substring(13, 21));
		
		//Loads the elements of the order in the table
		orderElementsList.addAll(order.getOrderElements());
		elementsTable.setItems(orderElementsList);		
	}

	//Sets the order as finalized in the main controller
	@FXML
	private void btnOrderFinalized(ActionEvent event) {
		boolean finalized = MainViewController.showFinalizeConfirmDialog(event, order);
		if (finalized) {
			btnCloseWindow(event);
		}
	}

	//Closes the current window
	@FXML
	private void btnCloseWindow(ActionEvent event) {
		Node button = (Node) event.getSource();
		Stage window = (Stage) button.getScene().getWindow();
		window.close();
	}
	
}
