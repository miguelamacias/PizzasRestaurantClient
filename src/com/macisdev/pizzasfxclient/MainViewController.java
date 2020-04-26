
package com.macisdev.pizzasfxclient;

import com.macisdev.pizzasfxclient.models.Order;
import com.macisdev.pizzasfxclient.utils.ParserXML;
import com.macisdev.pizzasfxclient.webservicereference.PizzaService;
import com.macisdev.pizzasfxclient.webservicereference.PizzaService_Service;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("BusyWait")
public class MainViewController implements Initializable {
	private static final ObservableList<Order> orderList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Order> orderTable;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//WebService objects
		PizzaService_Service service = new PizzaService_Service();
		PizzaService pizzaService = service.getPizzaServicePort();
		
		//Thread that runs in the background updating the list of orders
		new Thread(() -> {
			//Arraylist to store the parsed orders
			ArrayList<Order> ordersListFromWebService = new ArrayList<>();
			//TODO: Delete this after testing.
			try{
				orderList.add(ParserXML.parseXmlToOrder("<?xml version=\"1.0\" encoding=\"UTF-8\"?><order><order_info><order_id>1587922864850</order_id><customer_name>Miguel Angel Macias</customer_name><customer_phone>649425570</customer_phone><delivery_method>Envío a domicilio</delivery_method><customer_address>C/Valerito 36</customer_address><payment_method>Tarjeta</payment_method><total_price>31.20</total_price></order_info><pizza><code>1</code><name>Monster</name><size>Mediana</size><extras>EXTRA: Champiñones </extras><price>8.5</price></pizza><pizza><code>4</code><name>Barbacoa</name><size>Familiar</size><extras>EXTRA: Ternera </extras><price>16.2</price></pizza><pizza><code>8</code><name>Hawaiana</name><size>Mediana</size><extras>SIN: Piña </extras><price>6.5</price></pizza></order>"));
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
		
		
		//Creates the columns of the table
		TableColumn orderIdColumn = new TableColumn("Cod. pedido");
		orderIdColumn.setCellValueFactory(new PropertyValueFactory("orderId"));

		TableColumn orderDateTimeColumn = new TableColumn("Fecha - Hora");
		orderDateTimeColumn.setCellValueFactory(new PropertyValueFactory("orderDateTime"));
		
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

		//Adds the created columns to the table
		orderTable.getColumns().addAll(orderIdColumn, orderDateTimeColumn, customerNameColumn, customerPhoneColumn,
				customerAddressColumn, deliveryMethodColumn, paymentMethodColumn, totalPriceColumn);

		//Sets the datasource for the table
		orderTable.setItems(orderList);
	}	
	
	//Opens a window that shows the details of the selected order.
	@FXML
    void openOrderDetailsWindow(MouseEvent event) {
		if (event.getClickCount() == 2) {
			try {
				//Creates a new window and sets its fxml file
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderDetailsView.fxml"));
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
				Order order = orderTable.getSelectionModel().getSelectedItem();
				detailsWindowController.setOrder(order);
				detailsWindowController.loadOrder();
				
				//Shows the new window
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
	
	/* Marks the order as finished.
	 * Now it only deletes it from the table, but in the future
	 * it should be added to an archive of completed orders
	 */
	public static void finalizeOrder(Order order) {
		orderList.remove(order);
	}
}
