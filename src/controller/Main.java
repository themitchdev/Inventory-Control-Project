package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
/**FUTURE ENHANCEMENT
 * Display part image when adding associated parts to product.
  */
/** Starts the application and loads the main view
 *
 */
public class Main extends Application {

    @Override
    /** Loads the main view.
     *
     */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, primaryStage.getHeight(), primaryStage.getWidth()));
        primaryStage.show();
    }

    /** Initializes the application and loads sample data.*/
    public static void main(String[] args) {
        Inventory.addParts(new InHouse(1, "Bicycle", 10.99, 5, 1,5,555 ));
        Inventory.addParts(new InHouse(2, "Tricycle", 15.99, 5, 1,5,556 ));
        Inventory.addParts(new InHouse(3, "Fourcycle", 13.99, 5, 1,5,554 ));
        Inventory.addParts(new InHouse(4, "Motorcycle", 17.99, 5, 1,5,565 ));
        Inventory.addParts(new Outsourced(5, "Bike", 100.99, 5, 1,5,"ACME LLC" ));

        Inventory.addProducts(new Product(1, "Unicycle", 11.99, 5, 1,5));
        Inventory.addProducts(new Product(2, "cycle", 110.99, 5, 1,5));
        Inventory.addProducts(new Product(3, "Ocicle", 13.99, 5, 1,5));
        Inventory.addProducts(new Product(4, "Whatever", 14.99, 5, 1,5));

        launch(args);
    }
}
