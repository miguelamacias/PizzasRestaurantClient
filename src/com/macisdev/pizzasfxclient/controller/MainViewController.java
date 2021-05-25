
package com.macisdev.pizzasfxclient.controller;

import com.macisdev.orders.Order;
import com.macisdev.pizzasfxclient.PizzasRestaurantClient;
import com.macisdev.pizzasfxclient.utils.ParserXML;
import com.macisdev.pizzasfxclient.webservicereference.PizzaShopService;
import com.macisdev.pizzasfxclient.webservicereference.PizzaShopWebService;
import javafx.application.Platform;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("BusyWait")
public class MainViewController implements Initializable {
	private static final ObservableList<Order> orderList = FXCollections.observableArrayList();

	private static PizzaShopService pizzaService;

	@FXML
	private TableView<Order> orderTable;

	@FXML
	private Label statusLabel;

	@FXML
	private TextField tfWaitingTime;
	private int waitingTime = 30;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//sets the default waiting time
		tfWaitingTime.setText("30");
		getServerConnection();
		startServerThread();
		configureTable(orderTable, orderList, false);
	}

	public void getServerConnection() {
		//WebService objects
		PizzaShopWebService service;
		try {
			service = new PizzaShopWebService();
			pizzaService = service.getPizzaShopServicePort();
		} catch (WebServiceException e) {
			System.out.println("ERROR: The server is down!");
		}
	}

	public void startServerThread() {
		//Thread that runs in the background updating the list of orders
		new Thread(() -> {
			//Arraylist to store the parsed orders
			ArrayList<Order> ordersListFromWebService = new ArrayList<>();

			try {
				//Retrieves the pending orders on startup
				for (String order : pizzaService.getUnfinishedOrders()) { //arg0: time expected for the order to be ready
					System.out.println(order);
					ordersListFromWebService.add(ParserXML.parseXmlToOrder(order, ParserXML.RESTAURANT));
				}

				//Retrieves the new orders
				while (true) {//It runs forever with a delay of 5 seconds between cycles
					//Gets the orders from the web service and parses them
					for (String order : pizzaService.getOrders(waitingTime)) { //arg0: time expected for the order to be ready
						System.out.println(order);
						ordersListFromWebService.add(ParserXML.parseXmlToOrder(order, ParserXML.RESTAURANT));
					}
					//Add the parsed orders to the observableList used to store the table data
					orderList.addAll(ordersListFromWebService);
					System.out.println("order gotten: " + ordersListFromWebService.size());
					ordersListFromWebService.clear();

					Thread.sleep(5000);
				}
			} catch (Exception e) {
				System.err.println("Exception: The background thread has stopped unexpectedly");

				//Inform the user that there is a problem with the connection
				Platform.runLater(() -> { //GUI cannot be updated from the bg thread
					statusLabel.setText("⚠ Desconectado");
					statusLabel.setTextFill(Color.RED);

					//Creates an alert dialog and configures it
					Alert reconnectionDialog = new Alert(AlertType.ERROR, "",
							ButtonType.YES, ButtonType.NO);
					reconnectionDialog.setTitle("Error de conexión");
					reconnectionDialog.setHeaderText("Error en la conexión con el servidor");
					reconnectionDialog.setContentText("¿Quiere reintentar la conexión?");
					reconnectionDialog.initOwner(PizzasRestaurantClient.getPrimaryStage());

					//Shows the dialog and get the user input
					Optional userAnswer = reconnectionDialog.showAndWait();

					//If user confirmed the dialog the operation is done
					if (userAnswer.isPresent() && userAnswer.get() == ButtonType.YES) {
						startServerThread();
					} else {
						Alert offlineModeDialog = new Alert(AlertType.INFORMATION);
						offlineModeDialog.setTitle("Error de conexión");
						offlineModeDialog.setHeaderText("Trabajando en modo sin conexión");
						offlineModeDialog.setContentText("Puede seguir trabajando los pedidos actuales pero " +
								"no recibirá ningún nuevo pedido. Por favor intente restablecer la conexión.");
						offlineModeDialog.initOwner(PizzasRestaurantClient.getPrimaryStage());
						offlineModeDialog.showAndWait();
					}

				});
			}
		}).start();
		System.out.println("Background thread started successfully");
		//Show in the GUI that the connection with the server is working
		statusLabel.setText("✓ Conectado");
		statusLabel.setTextFill(Color.GREEN);

	}

	@FXML
	private void reconnectToServer() {
		startServerThread();
	}

	@FXML
	private void closeApplication() {
		System.exit(0);
	}

	@FXML
	private void openAboutWindow() {
		Alert dialogOrderNotFound = new Alert(AlertType.INFORMATION);
		dialogOrderNotFound.setTitle("Acerca de...");
		dialogOrderNotFound.setHeaderText("Gestión Pizzería App v1.0");
		dialogOrderNotFound.setContentText("Miguel Ángel Macías\nGPL-3.0 License\n2021");
		dialogOrderNotFound.showAndWait();
	}

	public static void configureTable(TableView<Order> tableToConfigure,
									  ObservableList<Order> dataSource, boolean filedView) {
		//Creates the columns of the table
		TableColumn<Order, String> orderIdColumn = new TableColumn<>("Cod. pedido");
		orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

		TableColumn<Order, String> orderDateTimeColumn = new TableColumn<>("Fecha - Hora");
		orderDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("orderDateTime"));

		TableColumn<Order, String> customerNameColumn = new TableColumn<>("Nombre cliente");
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

		TableColumn<Order, String> customerPhoneColumn = new TableColumn<>("Teléfono");
		customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

		TableColumn<Order, String> customerAddressColumn = new TableColumn<>("Dirección");
		customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

		TableColumn<Order, String> deliveryMethodColumn = new TableColumn<>("Tipo pedido");
		deliveryMethodColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryMethod"));

		TableColumn<Order, String> paymentMethodColumn = new TableColumn<>("Tipo pago");
		paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

		TableColumn<Order, Double> totalPriceColumn = new TableColumn<>("Importe");
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
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
		//noinspection unchecked
		tableToConfigure.getColumns().addAll(orderIdColumn, orderDateTimeColumn, customerNameColumn, customerPhoneColumn,
				customerAddressColumn, deliveryMethodColumn, paymentMethodColumn, totalPriceColumn);

		//Sets the datasource for the table
		tableToConfigure.setItems(dataSource);

		//Adds a contextual menu for the table
		tableToConfigure.setRowFactory(
				tableView -> {
					final TableRow<Order> row = new TableRow<>();
					//Creates the menu
					final ContextMenu rowMenu = new ContextMenu();

					//Configures the menu
					MenuItem viewOrder = new MenuItem("Abrir pedido");
					viewOrder.setOnAction(event -> openOrderDetailsWindow(row.getItem(),true));
					MenuItem finalizeOrder = new MenuItem("Finalizar pedido");
					finalizeOrder.setOnAction(event -> showFinalizeConfirmDialog(null, row.getItem()));
					MenuItem generateInvoice = new MenuItem("Generar Factura");
					generateInvoice.setOnAction(event -> OrderDetailsViewController.generateInvoice(
							row.getItem().getOrderId()));


					if (filedView) {
						rowMenu.getItems().addAll(viewOrder, generateInvoice);
					} else {
						rowMenu.getItems().addAll(viewOrder, finalizeOrder, generateInvoice);
					}

					//Only shows the menu on non-null rows
					row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty()))
							.then(rowMenu).otherwise((ContextMenu) null));
					return row;
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
    static void openOrderDetailsWindow(Order order, boolean currentOrder) {
		try {
			//Creates a new window and sets its fxml file
			FXMLLoader fxmlLoader = new FXMLLoader(PizzasRestaurantClient.class.getResource("view/OrderDetailsView.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));

			//configures the new window
			stage.getIcons().add(new Image(MainViewController.class.getResourceAsStream("/res/pizza_icon.png")));
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
			FXMLLoader fxmlLoader = new FXMLLoader(PizzasRestaurantClient.class.getResource("view/FiledOrdersView.fxml"));
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
	void searchOrderById() {
		String orderId;
		//Shows an input dialog for the user to inter the desired order
		TextInputDialog inputDialogOrderId = new TextInputDialog("1597938444192");
		inputDialogOrderId.setTitle("Consultar Pedido");
		inputDialogOrderId.setHeaderText("Consultar pedido por código");
		inputDialogOrderId.setContentText("Introduzca el código del pedido a consultar:");
		Optional<String> answer = inputDialogOrderId.showAndWait();
		if (answer.isPresent()) {
			orderId = answer.get();

			//retrieves the order form the server and converts it to an order of the local type
			Order retrievedOrder = null;
			try {
				retrievedOrder = ParserXML.parseXmlToOrder(pizzaService.getStoredOrder(orderId), ParserXML.RESTAURANT);
				System.out.println(pizzaService.getStoredOrder(orderId));
			} catch (Exception e) {
				e.printStackTrace();
			}

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
	void searchOrdersByPhoneNumber() {
		String phone;
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
					ParserXML.convertStringToOrderList(pizzaService.getStoredOrdersByPhoneNumber(phone), ParserXML.RESTAURANT);

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
	void searchAllFiledOrders() {
		//retrieves the list of orders form the server and converts it to a list of the local type
		List<Order> ordersRetrieved =
				ParserXML.convertStringToOrderList(pizzaService.getAllStoredOrders(), ParserXML.RESTAURANT);

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
		if (userAnswer.isPresent() && userAnswer.get() == ButtonType.OK) {
			MainViewController.finalizeOrder(order);
			return true;
		}
		return false;
	}

	@FXML
	void changeWaitingTime() {
		try {
			waitingTime = Integer.parseInt(tfWaitingTime.getText());
		} catch (NumberFormatException e) {
			Alert dialogOrderNotFound = new Alert(AlertType.ERROR);
			dialogOrderNotFound.setTitle("Tiempo inválido");
			dialogOrderNotFound.setHeaderText("El valor introducido no es válido.");
			dialogOrderNotFound.setContentText("Por favor, introduzca el tiempo de espera estimado en minutos.");
			dialogOrderNotFound.showAndWait();
		}
	}
	
	//Marks the order as finished and deletes from the table
	public static void finalizeOrder(Order order) {
		pizzaService.finalizeOrder(order.getOrderId());
		orderList.remove(order);
	}
}
