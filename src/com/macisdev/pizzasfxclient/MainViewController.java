
package com.macisdev.pizzasfxclient;

import com.macisdev.pizzasfxclient.utils.OrderConverter;
import com.macisdev.pizzasfxclient.utils.ParserXML;

import java.io.IOException;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.macisdev.orders.Order;
import com.macisdev.pizzasfxclient.webservicereference.PizzaShopService;
import com.macisdev.pizzasfxclient.webservicereference.PizzaShopWebService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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

	private PizzaShopService pizzaService;

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
		pizzaService = service.getPizzaShopServicePort();
		
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
		//Formats the price properly
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		totalPriceColumn.setCellFactory(tc -> new TableCell<Order, Double>() {

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
						viewOrder.setOnAction(event -> openOrderDetailsWindow(row.getItem(),true));
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
			openOrderDetailsWindow(order, true);
		}
    }

    //Opens a windows that shows the details of the selected orders
    void openOrderDetailsWindow(Order order, boolean currentOrder) {
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
			detailsWindowController.loadOrder(currentOrder);

			//Shows the new window
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Opens a new window that shows the list of orders specified
	void openFiledOrdersWindow(List<Order> filedOrdersList) {
		try {
			//Creates a new window and sets its fxml file
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FiledOrdersView.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));

			//configures the new window
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pizza_icon.png")));
			stage.setResizable(false);
			stage.setTitle("Listado de pedidos archivados");
			stage.initModality(Modality.NONE);
			stage.initStyle(StageStyle.DECORATED);

			//Gets the controller associated to the fxml to pass it the list of orders to be shown
			FiledOrdersViewController filedOrdersViewController = fxmlLoader.getController();
			filedOrdersViewController.setFiledOrdersList(filedOrdersList);

			//Shows the new window
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Opens a dialog to search an order in the database using its ID
	@FXML
	void searchOrderById(ActionEvent event) {
		String orderId = "";
		//Shows an input dialog for the user to inter the desired order
		TextInputDialog inputDialogOrderId = new TextInputDialog("1597938444192");
		inputDialogOrderId.setTitle("Consultar Pedido");
		inputDialogOrderId.setHeaderText("Consultar pedido por código");
		inputDialogOrderId.setContentText("Introduzca el código del pedido a consultar:");
		Optional<String> answer = inputDialogOrderId.showAndWait();
		if (answer.isPresent()) {
			orderId = answer.get();

			//retrieves the order form the server and converts it to an order of the local type
			Order retrievedOrder = OrderConverter.convertOrder(pizzaService.getStoredOrder(orderId));

			//checks if the order have been retrieved
			if (retrievedOrder != null) {
				openOrderDetailsWindow(retrievedOrder, false);
			} else {
				Alert dialogOrderNotFound = new Alert(AlertType.ERROR);
				dialogOrderNotFound.setTitle("Pedido no encontrado");
				dialogOrderNotFound.setHeaderText("No se ha encontrado el pedido con el código introducido.");
				dialogOrderNotFound.setContentText("Compruebe que ha introducido el código correctamente.");
				dialogOrderNotFound.showAndWait();
			}
		}
	}

	//Opens a dialog to search orders by the phone number associated to them
	@FXML
	void searchOrdersByPhoneNumber(ActionEvent event) {
		String phone = "";
		//Shows an input dialog for the user to inter the desired phone number
		TextInputDialog inputDialogPhoneNumber = new TextInputDialog("649425570");
		inputDialogPhoneNumber.setTitle("Consultar Pedidos");
		inputDialogPhoneNumber.setHeaderText("Consultar pedido por número de teléfono");
		inputDialogPhoneNumber.setContentText("Introduzca el número de teléfono a consultar:");
		Optional<String> answer = inputDialogPhoneNumber.showAndWait();

		//if the user press OK the execution continues
		if (answer.isPresent()) {
			phone = answer.get();

			//retrieves the list of orders form the server and converts it to a list of the local type
			List<Order> ordersRetrieved =
					OrderConverter.convertOrderList(pizzaService.getStoredOrdersByPhoneNumber(phone));

			//checks if the order have been retrieved
			if (!ordersRetrieved.isEmpty()) {
				openFiledOrdersWindow(ordersRetrieved);
			} else {
				Alert dialogOrderNotFound = new Alert(AlertType.ERROR);
				dialogOrderNotFound.setTitle("Teléfono no encontrado");
				dialogOrderNotFound.setHeaderText("No se ha encontrado ningún pedido asociado a este teléfono");
				dialogOrderNotFound.setContentText("Compruebe que ha introducido el teléfono correctamente.");
				dialogOrderNotFound.showAndWait();
			}
		}
	}

	@FXML
	void searchAllFiledOrders(ActionEvent event) {
		//retrieves the list of orders form the server and converts it to a list of the local type
		List<Order> ordersRetrieved =
				OrderConverter.convertOrderList(pizzaService.getAllStoredOrders());

		//checks if the order have been retrieved
		if (!ordersRetrieved.isEmpty()) {
			openFiledOrdersWindow(ordersRetrieved);
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
