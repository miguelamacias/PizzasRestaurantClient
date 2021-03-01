/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macisdev.pizzasfxclient.controller;

import com.macisdev.orders.Order;
import com.macisdev.orders.OrderElement;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class OrderDetailsViewController implements Initializable {
	//The list of elements of the order
	private final ObservableList<OrderElement> orderElementsList = FXCollections.observableArrayList();

	//Contains the current order of this window
	private Order order;

	@FXML
	private Button btnFinishOrder;

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
		TableColumn codeColumn = new TableColumn("Cod.");
		codeColumn.setCellValueFactory(new PropertyValueFactory("code"));
		codeColumn.setPrefWidth(63);
		
		TableColumn nameColumn = new TableColumn("Nombre");
		nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
		nameColumn.setPrefWidth(105);
		
		TableColumn sizeColumn = new TableColumn("Tamaño");
		sizeColumn.setCellValueFactory(new PropertyValueFactory("size"));
		sizeColumn.setPrefWidth(105);
		
		TableColumn extrasColumn = new TableColumn("Extras");
		extrasColumn.setCellValueFactory(new PropertyValueFactory("extras"));
		extrasColumn.setPrefWidth(442);

		//Shows a tooltip if the text is longer than the cell
		extrasColumn.setCellFactory(col -> new TableCell<Object, String>()
		{
			@Override
			protected void updateItem(final String item, final boolean empty)
			{
				super.updateItem(item, empty);
				setText(item);
				TableColumn tableCol = (TableColumn) col;

				if (item != null && tableCol.getWidth() < new Text(item + "  ").getLayoutBounds().getWidth())
				{
					tooltipProperty().bind(Bindings.when(Bindings.or(emptyProperty(), itemProperty().isNull())).then((Tooltip) null).otherwise(new Tooltip(item)));
				} else
				{
					tooltipProperty().bind(Bindings.when(Bindings.or(emptyProperty(), itemProperty().isNull())).then((Tooltip) null).otherwise((Tooltip) null));
				}

			}
		});
		
		TableColumn priceColumn = new TableColumn("Precio");
		priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
		priceColumn.setPrefWidth(70);

		//Formats the price properly
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		priceColumn.setCellFactory(tc -> new TableCell<Order, Double>() {

			@Override
			protected void updateItem(Double price, boolean empty) {
				super.updateItem(price, empty);
				if (empty) {
					setText(null);
				} else {
					setText(currencyFormat.format(price));
				}
			}
		});

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
	public void loadOrder(boolean currentOrder) {
		//Loads the info to the textfields
		tfOrderId.setText(order.getOrderId());
		tfCustomerName.setText(order.getCustomerName());
		tfCustomerPhone.setText(order.getCustomerPhone());
		tfCustomerAddress.setText(order.getCustomerAddress());
		tfPaymentMethod.setText(order.getPaymentMethod());
		tfTotalPrice.setText(String.format("%.2f €", order.getTotalPrice()));
		tfDate.setText(order.getOrderDateTime().substring(0, 10));
		tfTime.setText(order.getOrderDateTime().substring(13, 21));
		
		//Loads the elements of the order in the table
		orderElementsList.addAll(order.getOrderElements());
		elementsTable.setItems(orderElementsList);

		//controls the visibility of the finish order button according to the kind of order
		btnFinishOrder.setVisible(currentOrder);

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
