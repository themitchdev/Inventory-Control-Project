package controller;

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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Loads all the classes and FXML components
 *
 */
public class AddProduct implements Initializable {

    @FXML private Button buttonCancel2;
    @FXML private Button buttonAddParts;
    @FXML private TextField idTextBox;
    @FXML private TextField minTextBox;
    @FXML private TextField maxTextBox;
    @FXML private TextField priceTextBox;
    @FXML private TextField invTextBox;
    @FXML private TextField nameTextBox;
    @FXML private TextField addPartSearch;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> invCol;
    @FXML private TableColumn<Part, Double> priceCol;
    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML private TableColumn<Part, String> associatedPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedInvCol;
    @FXML private TableColumn<Part, Double> associatedPriceCol;
    @FXML private Button addButton;
    @FXML private Button removeAssociatedButton;
    @FXML private Button saveButton;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> copyOfAllParts = Inventory.getAllParts();
    private Product prod = new Product(0,"",0.0,0,0,0);
    boolean isNotDouble = true;

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
        partsTable.setItems(searchResult);
        addPartSearch.setText("");
    }

    /** Adds the part selected from the main part table to the
     * associated parts table.
     * @param event This parameter is not used
     */
    public void addPartToAssociatedPartTable (ActionEvent event){
        if(partsTable.getSelectionModel().getSelectedItem() != null) {
            Part partSelected = partsTable.getSelectionModel().getSelectedItem();
            prod.addAssociatedPart(partSelected);
            associatedPartsTable.setItems(prod.getAllAssociatedParts());
        }

    }

    /** Deletes a selected associated part from the associated parts table.
     * @param event This parameter is not used
     */
    public void removeAssociatedPart(ActionEvent event){
        if(associatedPartsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this part");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Part partSelected = associatedPartsTable.getSelectionModel().getSelectedItem();
                prod.deleteAssociatedPart(partSelected);
            }
        }
    }

    /** Takes the user back to the main screen when the cancel button is clicked.
     * @param event parameter not used.
     * @throws IOException input output exception.
     */
    public void toMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage addPartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1463,600);
        addPartStage.setTitle("Inventory Management System");
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    /** Displays a dialog box Alert of type INFORMATION.
     * The title of the dialog box is set to "Save Product".
     @param string The text content of the Alert dialog box.
     */
    public void buttonSaveAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Product");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    /** When Save button is clicked, this method checks for correct text and
     * numeric input, saves the product object into the Inventory product's array
     * and takes the user back to the main screen.
     * @param event parameter not used.
     * @throws IOException input output exception.
     */
    public void buttonSave(ActionEvent event)throws IOException {
        try{
            double d=Double.parseDouble(priceTextBox.getText());
            isNotDouble = false;
        }catch(NumberFormatException e){
            isNotDouble=true;
        }
        if (nameTextBox.getText().replaceAll("\\s+", "").isEmpty()) {
            buttonSaveAlert("Name field must be entered!");
        } else if (invTextBox.getText().replaceAll("\\s+", "").isEmpty() || !invTextBox.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Inventory field must be a number!");
        }else if(priceTextBox.getText().replaceAll("\\s+", "").isEmpty() || isNotDouble) {
            buttonSaveAlert("Price/Cost field must be a number in dollars and cents!");
        }else if(maxTextBox.getText().replaceAll("\\s+", "").isEmpty() || !maxTextBox.getText().chars().allMatch(Character::isDigit)){
            buttonSaveAlert("Max field must be a number!");
        }else if (minTextBox.getText().replaceAll("\\s+", "").isEmpty() || !minTextBox.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Min field must be a number!");
        }else if (Integer.parseInt(maxTextBox.getText()) < Integer.parseInt(minTextBox.getText())) {
            buttonSaveAlert("Max field must be larger than Min field!");
        }else if (Integer.parseInt(maxTextBox.getText()) < Integer.parseInt(minTextBox.getText())) {
            buttonSaveAlert("Min field must be smaller than Max field!");
        }else if (Integer.parseInt(invTextBox.getText()) > Integer.parseInt(maxTextBox.getText()) || Integer.parseInt(invTextBox.getText()) < Integer.parseInt(minTextBox.getText())){
            buttonSaveAlert("Inventory field must be between Max field and Min field");
        }else {
            int id = Inventory.getAllProducts().size() + 1;
            String name = nameTextBox.getText();
            double price = Double.parseDouble(priceTextBox.getText());
            int stock = Integer.parseInt(invTextBox.getText());
            int min = Integer.parseInt(minTextBox.getText());
            int max = Integer.parseInt(maxTextBox.getText());

            Inventory.addProducts(new Product(id, name, price, stock, min, max));

            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1463, 600);
            addPartStage.setTitle("Inventory Management System");
            addPartStage.setScene(scene);
            addPartStage.show();
        }
    }

    @Override
    /** Initializes the view and sets the tables with the values from the corresponding arrays.
     */
    public void initialize(URL url, ResourceBundle rb){

        partsTable.setItems(copyOfAllParts);

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedParts);

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }







}
