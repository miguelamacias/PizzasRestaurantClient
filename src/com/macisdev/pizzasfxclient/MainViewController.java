
package com.macisdev.pizzasfxclient;

import com.macisdev.pizzasfxclient.models.Order;
import com.macisdev.pizzasfxclient.models.OrderElement;
import com.macisdev.pizzasfxclient.utils.ParserXML;
import com.macisdev.pizzasfxclient.webservicereference.PizzaService;
import com.macisdev.pizzasfxclient.webservicereference.PizzaService_Service;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainViewController implements Initializable {
	private ObservableList<Order> orderList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Order> orderTable;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//WebService objects
		PizzaService_Service service = new PizzaService_Service();
		PizzaService pizzaService = service.getPizzaServicePort();
		
		//Thread that runs in the backgorund updating the list of orders
		new Thread(() -> {
			//Arraylist to store the parsed orders
			ArrayList<Order> ordersListFromWebService = new ArrayList<>();
			//TODO: Delete this after testing.
			try{
				orderList.add(ParserXML.parseXmlToOrder("<?xml version=\"1.0\" encoding=\"UTF-8\"?><order><order_info><order_id>07f03d3d-4184-4947-a39c-3153a965dd19</order_id><customer_name>Miguel Angel Macias</customer_name><customer_phone>649425570</customer_phone><delivery_method>Envío a domicilio</delivery_method><customer_address>C/Valerito 36</customer_address><payment_method>Tarjeta</payment_method><total_price>31.20</total_price></order_info><pizza><code>1</code><name>Monster</name><size>Mediana</size><extras>EXTRA: Champiñones </extras><price>8.5</price></pizza><pizza><code>4</code><name>Barbacoa</name><size>Familiar</size><extras>EXTRA: Ternera </extras><price>16.2</price></pizza><pizza><code>8</code><name>Hawaiana</name><size>Mediana</size><extras>SIN: Piña </extras><price>6.5</price></pizza></order>"));
			} catch(Exception e) {
				e.printStackTrace();
			}			
			//****************************
			try {
				while (true) {//It runs forever with a delay of 5 seconds between cycles				
					//Gets the orders from the web service and parses them
					for (String order : pizzaService.getOrders()) {
						ordersListFromWebService.add(ParserXML.parseXmlToOrder(order));
					}
					//Add the parsed orders to the observableList used to store the table data
					orderList.addAll(ordersListFromWebService);
					System.out.println("order gotten: " + ordersListFromWebService.size());
					ordersListFromWebService.clear();
					
					Thread.sleep(5000);
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
		
		//Config the table
		TableColumn orderIdColumn = new TableColumn("Cod. pedido");
		orderIdColumn.setCellValueFactory(new PropertyValueFactory("orderId"));
		
		TableColumn customerNameColumn = new TableColumn("Nombre cliente");
		customerNameColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
		
		TableColumn customerPhoneColumn = new TableColumn("Teléfono");
		customerPhoneColumn.setCellValueFactory(new PropertyValueFactory("customerPhone"));
		
		TableColumn customerAddressColumn = new TableColumn("Dirección");
		customerAddressColumn.setCellValueFactory(new PropertyValueFactory("customerAddress"));
		
		TableColumn deliveryMethodColumn = new TableColumn("Tipo pedido");
		deliveryMethodColumn.setCellValueFactory(new PropertyValueFactory("deliveryMethod"));
		
		TableColumn paymentMethodColumn = new TableColumn("Tipo pago");
		paymentMethodColumn.setCellValueFactory(new PropertyValueFactory("paymentMethod"));
		
		TableColumn totalPriceColumn = new TableColumn("Importe");
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory("totalPrice"));
		
		orderTable.setItems(orderList);
		orderTable.getColumns().addAll(orderIdColumn, customerNameColumn, customerPhoneColumn, customerAddressColumn,
				deliveryMethodColumn, paymentMethodColumn, totalPriceColumn);
		
	}	
	
	//Opens a new window that shows the details for the selected order.
	@FXML
    void openOrderDetailsWindow(MouseEvent event) {
		if (event.getClickCount() == 2) {
			try {
				//Creates a new window and loads its fxml file
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderDetailsView.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				
				//configures the new window
				stage.setResizable(false);
				stage.setTitle("Detalles del pedido");
				stage.initModality(Modality.NONE);
				stage.initStyle(StageStyle.UTILITY);
				
				//Gets the controller asociated to the fxml to pass it the order selected
				OrderDetailsViewController newWindowController = fxmlLoader.getController();
				Order order = orderTable.getSelectionModel().getSelectedItem();
				newWindowController.setOrder(order);
				newWindowController.loadOrder();
				
				//Shows the new window
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
	
	//Written as a simple test, but it can be useful as a stub for the real method
	@FXML
	private void getPizzasFromServer(ActionEvent event) {
		PizzaService_Service service = new PizzaService_Service();
		PizzaService pizzaService = service.getPizzaServicePort();
		List<String> pizzaList = pizzaService.getOrders();
		System.out.println("Starting client");
		System.out.println(pizzaList);
		
		for (String order : pizzaList) {
			System.out.println(order);
		}
	}
	
	
	
}
