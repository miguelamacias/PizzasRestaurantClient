/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macisdev.pizzasfxclient;

import com.macisdev.pizzasfxclient.models.Order;
import com.macisdev.pizzasfxclient.models.OrderElement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class OrderDetailsViewController implements Initializable {
	private ObservableList<OrderElement> orderElementsList = FXCollections.observableArrayList();
	
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
    private TableView<OrderElement> elementsTable;
	
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {		
		//Config the table
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
		
		elementsTable.getColumns().addAll(codeColumn, nameColumn, sizeColumn, extrasColumn, priceColumn);
	}	
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	//Loads the data of the order selected in the main table
	public void loadOrder() {
		tfOrderId.setText(order.getOrderId());
		tfCustomerName.setText(order.getCustomerName());
		tfCustomerPhone.setText(order.getCustomerPhone());
		tfCustomerAddress.setText(order.getCustomerAddress());
		tfPaymentMethod.setText(order.getPaymentMethod());
		tfTotalPrice.setText(String.valueOf(order.getTotalPrice()));
		
		//Loads the data of the table
		orderElementsList.addAll(order.getOrderElements());
		elementsTable.setItems(orderElementsList);		
	}
	
}
