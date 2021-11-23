package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** Loads all the classes and FXML components
 *
 */
public class AddPart implements Initializable {

    @FXML
    private ToggleGroup toggle2;
    @FXML
    private RadioButton toggleInHouse;
    @FXML
    private RadioButton toggleOutsource;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonSave;
    @FXML
    private Label labelMachineIdOrCompanyName;
    @FXML
    private TextField addPartIdText;
    @FXML
    private TextField addPartInvText;
    @FXML
    private TextField addPartNameText;
    @FXML
    private TextField addPartPriceText;
    @FXML
    private TextField addPartMaxText;
    @FXML
    private TextField addPartMachineOrCompanyName;
    @FXML
    private TextField addPartMinText;

    boolean isNotDouble = true;

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

    /** Sets the Machine ID/Company Name label to
        match the label of selected radio button.
     */
    public void radioButton() {
        if (this.toggle2.getSelectedToggle().equals(this.toggleInHouse)) {
            labelMachineIdOrCompanyName.setText("Machine Id");
        } else {
            labelMachineIdOrCompanyName.setText("Company Name");
        }
    }

    /** When the Save button is clicked, this method checks for correct text and
     * numeric input then saves the part object into the Inventory parts array
     * and takes the user to the main screen.
     * @param event parameter not used.
     * @throws IOException input output exception.
     */
    public void buttonSave(ActionEvent event) throws IOException {
        try{
            double d=Double.parseDouble(addPartPriceText.getText());
            isNotDouble = false;
        }catch(NumberFormatException e){
            isNotDouble=true;
        }
        if (addPartNameText.getText().replaceAll("\\s+", "").isEmpty()) {
            buttonSaveAlert("Name field must be entered!");
        } else if (addPartInvText.getText().replaceAll("\\s+", "").isEmpty() || !addPartInvText.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Inventory field must be a number!");
        }else if(addPartPriceText.getText().replaceAll("\\s+", "").isEmpty() || isNotDouble) {
            buttonSaveAlert("Price/Cost field must be a number in dollars and cents!");
        }else if(addPartMaxText.getText().replaceAll("\\s+", "").isEmpty() || !addPartMaxText.getText().chars().allMatch(Character::isDigit)){
            buttonSaveAlert("Max field must be a number!");
        }else if (addPartMinText.getText().replaceAll("\\s+", "").isEmpty() || !addPartMinText.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Min field must be a number!");
        }else if (this.toggle2.getSelectedToggle().equals(this.toggleInHouse) && !addPartMachineOrCompanyName.getText().chars().allMatch(Character::isDigit) || addPartMachineOrCompanyName.getText().replaceAll("\\s+", "").isEmpty()) {
            buttonSaveAlert("Machine ID must be a number!");
        }else if (this.toggle2.getSelectedToggle().equals(this.toggleOutsource) && addPartNameText.getText().replaceAll("\\s+", "").isEmpty()){
            buttonSaveAlert("Company Name field must be entered!");
        }else if (Integer.parseInt(addPartMaxText.getText()) < Integer.parseInt(addPartMinText.getText())) {
            buttonSaveAlert("Max field must be larger than Min field!");
        }else if (Integer.parseInt(addPartMaxText.getText()) < Integer.parseInt(addPartMinText.getText())) {
            buttonSaveAlert("Min field must be smaller than Max field!");
        }else if (Integer.parseInt(addPartInvText.getText()) > Integer.parseInt(addPartMaxText.getText()) || Integer.parseInt(addPartInvText.getText()) < Integer.parseInt(addPartMinText.getText())){
            buttonSaveAlert("Inventory field must be between Max field and Min field");
        }else {
            int id = Inventory.getAllParts().size() + 1;
            String name = addPartNameText.getText();
            double price = Double.parseDouble(addPartPriceText.getText());
            int stock = Integer.parseInt(addPartInvText.getText());
            int min = Integer.parseInt(addPartMinText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());

            if (this.toggle2.getSelectedToggle().equals(this.toggleInHouse)) {
                int machineId = Integer.parseInt(addPartMachineOrCompanyName.getText());
                Inventory.addParts(new InHouse(id, name, price, stock, min, max, machineId));
            } else {
                String companyName = addPartMachineOrCompanyName.getText();
                Inventory.addParts(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1463, 600);
            addPartStage.setTitle("Inventory Management System");
            addPartStage.setScene(scene);
            addPartStage.show();
        }
    }

    /** Takes the user to the main screen.
     * @param event parameter not used.
     * @throws IOException input output exception.
     */
    public void toMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage addPartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1463, 600);
        addPartStage.setTitle("Inventory Management System");
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }






}