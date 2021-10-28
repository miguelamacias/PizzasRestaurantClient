package com.macisdev.pizzasfxclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PizzasRestaurantClient extends Application {
	private static Stage primaryStage;

	@Override
	public void start(Stage stage) {

		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
		} catch (Throwable e) {
			e.printStackTrace(System.out);
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Gestión Pizzería Alpha");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pizza_icon.png")));
		stage.setMaximized(true);
		stage.show();
		primaryStage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	//Necessary to stop the background process at exit
	@Override
	public void stop() throws Exception {
		super.stop();
		System.exit(0);
	}

	//Returns the primaryStage to be used all across the app
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
