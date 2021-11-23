package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/** Loads all the classes and FXML components
 *
 */
public class mainController implements Initializable {

    @FXML
    private Button buttonPartAdd;
    @FXML
    private Button buttonPartModify;
    @FXML
    private Button buttonProductAdd;
    @FXML
    private Button buttonProductModify;
    @FXML
    private Button buttonExit;
    @FXML
    private Button buttonPartDelete;
    @FXML
    private Button buttonProdDelete;
    @FXML
    private TableView<Part> mainPartTable;
    @FXML
    private TableView<Product> mainProductTable;
    @FXML
    private TableColumn<Part, Integer> partsPartIDCol;
    @FXML
    private TableColumn<Part, String> partsPartNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInvLevelCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
    @FXML
    private TableColumn<Product, Integer> productsPartIDCol;
    @FXML
    private TableColumn<Product, String> productsProdNameCol;
    @FXML
    private TableColumn<Product, Integer> productsInvLevelCol;
    @FXML
    private TableColumn<Product, Double> productsPriceCol;
    @FXML
    private TextField addPartSearch;
    @FXML
    private TextField addProductSearch;

    /** Deletes a selected part from the main parts table. If user has
     * not selected a part it will display a dialog box.
     * @param event Parameter not used.
     */
    public void partDelete(ActionEvent event) {
        if(mainPartTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this part");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Part partSelected = mainPartTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(partSelected);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText(null);
            alert.setContentText("You must select a part before deleting");
            alert.showAndWait();
        }
    }

    /** Deletes a selected product item from the products table. Prevents
     * user from deleting a product with associated parts. If no product
     * is selected then displays a dialog box.
     * @param event Parameter not used.
     */
    public void prodDelete(ActionEvent event) {
        Product prod = mainProductTable.getSelectionModel().getSelectedItem();
        if(prod.getAllAssociatedParts().size() != 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Product with Associated Parts");
            alert.setHeaderText(null);
            alert.setContentText("You must delete associated parts before deleting product");
            alert.showAndWait();
        }
        else {
            if (mainProductTable.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Product");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Product prodSelected = mainProductTable.getSelectionModel().getSelectedItem();
                    Inventory.deleteProduct(prodSelected);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Part");
                alert.setHeaderText(null);
                alert.setContentText("You must select a product before deleting");
                alert.showAndWait();
            }
        }
    }

    /** Populates main parts table with list of search results.
     * @param event Parameter not used.
     */
    public void getPartSearch(ActionEvent event) {
        try {
            String searchPart = addPartSearch.getText();
            ObservableList<Part> searchResult = Inventory.lookupPart(searchPart);
            if (searchResult.size() == 0) {
                int partId = Integer.parseInt(searchPart);
                Part onePart = Inventory.lookupPart(partId);
                if (onePart != null)
                    searchResult.add(onePart);
            }
            mainPartTable.setItems(searchResult);
            addPartSearch.setText("");
        }
        catch(RuntimeException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setHeaderText(null);
            alert.setContentText("Part not found!!");
            alert.showAndWait();

        }

    }

    /** Populates main products table with list of search results.
     *
      * @param event Parameter not used.
     */
    public void getProdSearch(ActionEvent event) {
        try {
            String searchPart = addProductSearch.getText();
            ObservableList<Product> searchResult = Inventory.lookupProduct(searchPart);
            if (searchResult.size() == 0) {
                int partId = Integer.parseInt(searchPart);
                Product oneProduct = Inventory.lookupProduct(partId);
                if (oneProduct != null)
                    searchResult.add(oneProduct);
            }
            mainProductTable.setItems(searchResult);
            addProductSearch.setText("");

        }
        catch(RuntimeException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setHeaderText(null);
            alert.setContentText("Product not found!!");
            alert.showAndWait();

        }
    }

    /** Sends user to ADDPart scene.
     *
     * @param event Parameter not used.
     */
    public void toAddPartScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    /** Sends user to ADDProduct scene.
     *
     * @param event Parameter not used.
     */
    public void toAddProductScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 800);
        stage.setTitle("Add Products");
        stage.setScene(scene);
        stage.show();
    }

    /** Sends user and object selected from main parts table to ModifyPart
     * scene and text boxes. Shows user dialog box if part is not selected
     * prior to sending user to ModifyPart scene.    *
     *
     * @param event Parameter not used.
     * @throws RuntimeException if user does not select a part before attempting to go to the modify scene.
     */
    public void toModifyPartScene(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPart mpController = loader.getController();
            mpController.getPart(mainPartTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(RuntimeException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Part");
            alert.setHeaderText(null);
            alert.setContentText("You must select a part before clicking the Modify button");
            alert.showAndWait();

        }

    }
    /** Sends user and object selected
     * from main products table to ModifyPart scene.
     *
     * @param event Parameter not used.
     * @exception RuntimeException if user does not select a product before attempting to go to the modify scene.
     */
    public void toModifyProductScene(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProduct mpController = loader.getController();
            mpController.getProduct(mainProductTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(RuntimeException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Part");
            alert.setHeaderText(null);
            alert.setContentText("You must select a product before clicking the Modify button");
            alert.showAndWait();

        }

    }

    /** Exits the application with code 0.
     *
     * @param event parameter not used.
     */
    public void actionEventExit(ActionEvent event) {
        System.exit(0);
    }
    @Override
    /**Initializes and populates the tables with corresponding data
     *
     */
    public void initialize(URL url, ResourceBundle rb){



        mainPartTable.setItems(Inventory.getAllParts());
        mainProductTable.setItems(Inventory.getAllProducts());

        partsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}