package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**Load all classes and FXML components.
 *
 */
public class ModifyProduct implements Initializable {

    @FXML private Button buttonCancel4;
    @FXML private TableView<Part> mainPartTable;
    @FXML private TableView<Part> bottomPartTable;
    @FXML private TableColumn<Part, Integer> partsPartIDCol;
    @FXML private TableColumn<Part, String> partsPartNameCol;
    @FXML private TableColumn<Part, Integer> partsInvLevelCol;
    @FXML private TableColumn<Part, Double> partsPriceCol;
    @FXML private TableColumn<Part, Integer> bottomPartIDCol;
    @FXML private TableColumn<Part, String> bottomPartNameCol;
    @FXML private TableColumn<Part, Integer> bottomInvLevelCol;
    @FXML private TableColumn<Part, Double> bottomPriceCol;
    @FXML private TextField addPartSearch;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button saveButton;
    @FXML private TextField prodIdText;
    @FXML private TextField prodMinText;
    @FXML private TextField prodMaxText;
    @FXML private TextField prodPriceText;
    @FXML private TextField prodInvText;
    @FXML private TextField prodNameText;

    Product prod2;
    boolean isNotDouble = true;
    private ObservableList<Part> copyOfAllParts = Inventory.getAllParts();

    /** Deletes a selected associated part from the associated parts table.
     * @param event This parameter is not used
     */
    public void removeAssociatedPart(ActionEvent event){
        if(bottomPartTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this part");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Part partSelected = bottomPartTable.getSelectionModel().getSelectedItem();
                prod2.deleteAssociatedPart(partSelected);
            }
        }
    }

    /** Displays a dialog box Alert of type INFORMATION
     @param string The text content of the Alert dialog box.
     */
    public void buttonSaveAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Part");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    /**RUNTIME ERROR
     * Runtimerror was being generate by not being able to validate user input
     * for price text box, which is of type double. The way I was going about it was
     * unnecessarily complex. Solved the issue by assigning a price input
     * to a variable inside of a try catch block. If the price input was not a double
     * then it would not be parsable into a double and would throw an exception.
     */
    /** When Save button is clicked, this method checks for correct text and
     * numeric input, saves the product object into the Inventory product's array
     * and takes the user back to the main screen.
     * @param event This parameter is not used.
     */
    public void saveModifiedProd(ActionEvent event) throws IOException {
        try{
            double d=Double.parseDouble(prodPriceText.getText());
            isNotDouble = false;
        }catch(NumberFormatException e){
            isNotDouble=true;
        }
        if (prodNameText.getText().replaceAll("\\s+", "").isEmpty()) {
            buttonSaveAlert("Name field must be entered!");
        } else if (prodInvText.getText().replaceAll("\\s+", "").isEmpty() || !prodInvText.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Inventory field must be a number!");
        }else if(prodPriceText.getText().replaceAll("\\s+", "").isEmpty() || isNotDouble) {
            buttonSaveAlert("Price/Cost field must be a number in dollars and cents!");
        }else if(prodMaxText.getText().replaceAll("\\s+", "").isEmpty() || !prodMaxText.getText().chars().allMatch(Character::isDigit)){
            buttonSaveAlert("Max field must be a number!");
        }else if (prodMinText.getText().replaceAll("\\s+", "").isEmpty() || !prodMinText.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Min field must be a number!");
        }else if (Integer.parseInt(prodMaxText.getText()) < Integer.parseInt(prodMinText.getText())) {
            buttonSaveAlert("Max field must be larger than Min field!");
        }else if (Integer.parseInt(prodMaxText.getText()) < Integer.parseInt(prodMinText.getText())) {
            buttonSaveAlert("Min field must be smaller than Max field!");
        }else if (Integer.parseInt(prodInvText.getText()) > Integer.parseInt(prodMaxText.getText()) || Integer.parseInt(prodInvText.getText()) < Integer.parseInt(prodMinText.getText())){
            buttonSaveAlert("Inventory field must be between Max field and Min field");
        }else {
            prod2.setId(Integer.parseInt(prodIdText.getText()));
            prod2.setName(prodNameText.getText());
            prod2.setMax(Integer.parseInt(prodMaxText.getText()));
            prod2.setMin(Integer.parseInt(prodMinText.getText()));
            prod2.setStock(Integer.parseInt(prodInvText.getText()));
            Inventory.updateProduct(Integer.parseInt(prodIdText.getText()) - 1, prod2);

            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1463, 600);
            addPartStage.setTitle("Inventory Management System");
            addPartStage.setScene(scene);
            addPartStage.show();
        }

    }

    /** Populates text boxes with selected product from main view.
     * @param product The product object being transferred from the main view.
     */
    public void getProduct(Product product){
        prod2 = product;
        prodIdText.setText(String.valueOf(prod2.getId()));
        prodMinText.setText(String.valueOf(prod2.getMin()));
        prodMaxText.setText(String.valueOf(prod2.getMax()));
        prodPriceText.setText(String.valueOf(prod2.getPrice()));
        prodInvText.setText(String.valueOf(prod2.getStock()));
        prodNameText.setText(prod2.getName());
        bottomPartTable.setItems(prod2.getAllAssociatedParts());

    }

    /** Takes the user to the main screen.
     * @param event This parameter is not used
     */
    public void toMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage addPartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1463, 600);
        addPartStage.setTitle("Inventory Management System");
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    /** Adds the part selected from the main part table to the
     * associated parts table.
     * @param event This parameter is not used
     */
    public void addPartToAssociatedPartTable (ActionEvent event){

        if(mainPartTable.getSelectionModel().getSelectedItem() != null) {
            Part partSelected = mainPartTable.getSelectionModel().getSelectedItem();
            prod2.addAssociatedPart(partSelected);
            bottomPartTable.setItems(prod2.getAllAssociatedParts());
        }
    }

    /** Populates the main parts table with the search result obtained from
     * the input in the Search box.
     * @param event This parameter is not used
     */
    public void getPartSearch(ActionEvent event){
        String searchPart = addPartSearch.getText();
        ObservableList<Part> searchResult =  Inventory.lookupPart(searchPart);
        if(searchResult.size() == 0){
            int partId = Integer.parseInt(searchPart);
            Part onePart = Inventory.lookupPart(partId);
            if (onePart!=null)
                searchResult.add(onePart);
        }
        mainPartTable.setItems(searchResult);
        addPartSearch.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mainPartTable.setItems(copyOfAllParts);


        bottomPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bottomPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}