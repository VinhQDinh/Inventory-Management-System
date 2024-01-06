package wgu.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import model.*;

import java.io.IOException;

/**
 * The MainMenuApplication class is the main entry point for the application.
 * It extends the JavaFX Application class and initializes the main menu of the inventory management system.
 */
public class MainMenuApplication extends Application {

    /**
     * The start method is called by the JavaFX application thread to start the application.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If an exception occurs during the application start.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inventory = new Inventory();
        addTestData(inventory);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    /**
     * Adds test data to the inventory for demonstration purposes.
     *
     * @param inv The inventory to which test data will be added.
     */
    void addTestData(Inventory inv) {
        Inventory.addPart(new InHouse(1, "Car Part 1", 1999.99, 10, 1, 50, 1001));
        Inventory.addPart(new InHouse(2, "Truck Part 2", 2999.99, 20, 1, 50, 1002));
        Inventory.addPart(new InHouse(3, "Motorcycle Part 3", 399.99, 5, 1, 50, 1003));
        Inventory.addPart(new InHouse(4, "Bicycle Part 4", 499.99, 8, 1, 50, 1004));
        Inventory.addPart(new InHouse(5, "Scooter Part 5", 599.99, 15, 1, 50, 1005));
        Inventory.addPart(new Outsourced(6, "Bus Part 6", 6999.99, 30, 1, 50, "Vehicle Co."));
        Inventory.addPart(new Outsourced(7, "Boat Part 7", 7999.99, 25, 1, 50, "Marine Co."));
        Inventory.addPart(new Outsourced(8, "Plane Part 8", 8999.99, 18, 1, 50, "Aviation Co."));
        Inventory.addPart(new Outsourced(9, "Jet Part 9", 9999.99, 22, 1, 50, "Aerospace Co."));
        Inventory.addPart(new Outsourced(10, "Spaceship Part 10", 10999.99, 12, 1, 50, "Space Co."));

        Product prod1 = new Product(100, "Basic Vehicle 1", 5999.99, 15, 1, 50);
        prod1.addAssociatedPart(new InHouse(1, "Car Part 1", 1999.99, 10, 1, 50, 1001));
        prod1.addAssociatedPart(new Outsourced(6, "Bus Part 6", 6999.99, 30, 1, 50, "Vehicle Co."));
        Inventory.addProduct(prod1);

        Product prod2 = new Product(200, "Improved Vehicle 2", 8999.99, 25, 1, 50);
        prod2.addAssociatedPart(new InHouse(2, "Truck Part 2", 2999.99, 20, 1, 50, 1002));
        prod2.addAssociatedPart(new Outsourced(7, "Boat Part 7", 7999.99, 25, 1, 50, "Marine Co."));
        Inventory.addProduct(prod2);

        Product prod3 = new Product(300, "Super Vehicle 3", 12999.99, 30, 1, 50);
        prod3.addAssociatedPart(new InHouse(3, "Motorcycle Part 3", 399.99, 5, 1, 50, 1003));
        prod3.addAssociatedPart(new Outsourced(8, "Plane Part 8", 8999.99, 18, 1, 50, "Aviation Co."));
        Inventory.addProduct(prod3);

        Product prod4 = new Product(400, "Wild Vehicle 4", 15999.99, 20, 1, 50);
        prod4.addAssociatedPart(new InHouse(4, "Bicycle Part 4", 499.99, 8, 1, 50, 1004));
        prod4.addAssociatedPart(new Outsourced(9, "Jet Part 9", 9999.99, 22, 1, 50, "Aerospace Co."));
        Inventory.addProduct(prod4);

        Product prod5 = new Product(500, "Insane Vehicle 5", 19999.99, 25, 1, 50);
        prod5.addAssociatedPart(new InHouse(5, "Scooter Part 5", 599.99, 15, 1, 50, 1005));
        prod5.addAssociatedPart(new Outsourced(10, "Spaceship Part 10", 10999.99, 12, 1, 50, "Space Co."));
        Inventory.addProduct(prod5);
    }


    public static void main(String[] args) {
        launch();
    }

}