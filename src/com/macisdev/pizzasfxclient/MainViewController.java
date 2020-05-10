
package com.macisdev.pizzasfxclient;

import com.macisdev.pizzasfxclient.models.Order;
import com.macisdev.pizzasfxclient.utils.ParserXML;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.macisdev.pizzasfxclient.webservicereference.PizzaShopService;
import com.macisdev.pizzasfxclient.webservicereference.PizzaShopWebService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

@SuppressWarnings("BusyWait")
public class MainViewController implements Initializable {
	private static final ObservableList<Order> orderList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Order> orderTable;

	@FXML
	private TextField tfWaitingTime;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//sets the default waiting time
		tfWaitingTime.setText("30");
		//WebService objects
		PizzaShopWebService service = new PizzaShopWebService();
		PizzaShopService pizzaService = service.getPizzaShopServicePort();
		
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
					for (String order : pizzaService.getOrders(
							Integer.parseInt(tfWaitingTime.getText()))) { //arg0: time expected for the order to be ready
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

		//Adds a contextual menu for the table
		orderTable.setRowFactory(
				new Callback<TableView<Order>, TableRow<Order>>() {
					@Override
					public TableRow<Order> call(TableView<Order> tableView) {
						final TableRow<Order> row = new TableRow<>();
						//Creates the menu
						final ContextMenu rowMenu = new ContextMenu();

						//Configures the menu
						MenuItem viewOrder = new MenuItem("Abrir pedido");
						viewOrder.setOnAction(event -> openOrderDetailsWindow(row.getItem()));
						MenuItem finalizeOrder = new MenuItem("Finalizar pedido");
						finalizeOrder.setOnAction(event -> showFinalizeConfirmDialog(null, row.getItem()));
						rowMenu.getItems().addAll(viewOrder, finalizeOrder);

						//Only shows the menu on non-null rows
						row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty()))
						.then(rowMenu).otherwise((ContextMenu) null));
						return row;
					}
				}
		);
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
			detailsWindowController.setOrder(order);
			detailsWindowController.loadOrder();

			//Shows the new window
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean showFinalizeConfirmDialog(Event event, Order order){
		//Creates an alert dialog and configures it
		Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationDialog.setTitle("Finalizar Pedido");
		confirmationDialog.setHeaderText("Finalizar pedido");
		confirmationDialog.setContentText("¿Desea finalizar este pedido? Esto lo" +
				" borrará de la lista de pedidos pendientes.");
		//Sets the owner of the dialog, inheriting its icon
		if (event == null) { //event is null when this method is called from the context menu
			confirmationDialog.initOwner(PizzasRestaurantClient.getPrimaryStage());
		} else { //event is not null when called from the order details windows
			confirmationDialog.initOwner(((Node) event.getSource()).getScene().getWindow());
		}

		//Shows the dialog and get the user input
		Optional userAnswer = confirmationDialog.showAndWait();

		//If user confirmed the dialog the operation is done
		if (userAnswer.get() == ButtonType.OK) {
			MainViewController.finalizeOrder(order);
			return true;
		}
		return false;
	}
	
	/* Marks the order as finished.
	 * Now it only deletes it from the table, but in the future
	 * it should be added to an archive of completed orders
	 */
	public static void finalizeOrder(Order order) {
		orderList.remove(order);
	}
}
