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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Loads all the classes and FXML components
 *
 */
public class ModifyPart implements Initializable {

    @FXML private Button buttonCancel3;
    @FXML private Button saveButton;
    @FXML private ToggleGroup toggle;
    @FXML private RadioButton toggleInHouse;
    @FXML private RadioButton toggleOutsourced;
    @FXML private Label labelMachineIdOrCompanyName;
    @FXML private TextField partIdText;
    @FXML private TextField partInvText;
    @FXML private TextField partNameText;
    @FXML private TextField partPriceText;
    @FXML private TextField partMaxText;
    @FXML private TextField partMachineId;
    @FXML private TextField partMinText;

    Part part;
    boolean isNotDouble = true;
    /** Checks for correct text and numeric input, saves
     *  the product object into the Inventory product's array
     * and takes the user back to the main screen.
     * @param event This parameter is not used
     */
    public void saveModifiedPart(ActionEvent event) throws IOException {
        try{
            double d=Double.parseDouble(partPriceText.getText());
            isNotDouble = false;
        }catch(NumberFormatException e){
            isNotDouble=true;
        }
        if (partNameText.getText().replaceAll("\\s+", "").isEmpty()) {
            buttonSaveAlert("Name field must be entered!");
        } else if (partInvText.getText().replaceAll("\\s+", "").isEmpty() || !partInvText.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Inventory field must be a number!");
        }else if(partPriceText.getText().replaceAll("\\s+", "").isEmpty() || isNotDouble) {
            buttonSaveAlert("Price/Cost field must be a number in dollars and cents!");
        }else if(partMaxText.getText().replaceAll("\\s+", "").isEmpty() || !partMaxText.getText().chars().allMatch(Character::isDigit)){
            buttonSaveAlert("Max field must be a number!");
        }else if (partMinText.getText().replaceAll("\\s+", "").isEmpty() || !partMinText.getText().chars().allMatch(Character::isDigit)) {
            buttonSaveAlert("Min field must be a number!");
        }else if (this.toggle.getSelectedToggle().equals(this.toggleInHouse) && !partMachineId.getText().chars().allMatch(Character::isDigit) || partMachineId.getText().replaceAll("\\s+", "").isEmpty()) {
            buttonSaveAlert("Machine ID must be a number!");
        }else if (this.toggle.getSelectedToggle().equals(this.toggleOutsourced) && partNameText.getText().replaceAll("\\s+", "").isEmpty()){
            buttonSaveAlert("Company Name field must be entered!");
        }else if (Integer.parseInt(partMaxText.getText()) < Integer.parseInt(partMinText.getText())) {
            buttonSaveAlert("Max field must be larger than Min field!");
        }else if (Integer.parseInt(partMaxText.getText()) < Integer.parseInt(partMinText.getText())) {
            buttonSaveAlert("Min field must be smaller than Max field!");
        }else if (Integer.parseInt(partInvText.getText()) > Integer.parseInt(partMaxText.getText()) || Integer.parseInt(partInvText.getText()) < Integer.parseInt(partMinText.getText())){
            buttonSaveAlert("Inventory field must be between Max field and Min field");
        }else {
            part.setId(Integer.parseInt(partIdText.getText()));
            part.setName(partNameText.getText());
            part.setMax(Integer.parseInt(partMaxText.getText()));
            part.setMin(Integer.parseInt(partMinText.getText()));
            part.setStock(Integer.parseInt(partInvText.getText()));
            Inventory.updatePart(Integer.parseInt(partIdText.getText()) - 1, part);

            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1463, 600);
            addPartStage.setTitle("Inventory Management System");
            addPartStage.setScene(scene);
            addPartStage.show();
        }
    }

    /** Displays a dialog box Alert of type INFORMATION
     @param string The text content of the Alert dialog box.
     */
    public void buttonSaveAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Modified Part");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    /** Populates text boxes with selected part from main view.
     * @param partVar part object transferred from main view.
     */
    public void getPart(Part partVar){
        part = partVar;
        partIdText.setText(String.valueOf(part.getId()));
        partMinText.setText(String.valueOf(part.getMin()));
        partMaxText.setText(String.valueOf(part.getMax()));
        partPriceText.setText(String.valueOf(part.getPrice()));
        partInvText.setText(String.valueOf(part.getStock()));
        partNameText.setText(part.getName());

        if (part instanceof InHouse ) {
            partMachineId.setText(String.valueOf(((InHouse)part).getMachineId()));
            toggleInHouse.setSelected(true);
        }else{
            partMachineId.setText(((Outsourced)part).getCompanyName());
            toggleOutsourced.setSelected(true);
        }
    }

    /** Sets the Machine ID/Company Name label to
     match the label of selected radio button.
     */
    public void radioButton(){
        if (this.toggle.getSelectedToggle().equals(this.toggleInHouse)) {
            labelMachineIdOrCompanyName.setText("Machine Id");
        }else{
            labelMachineIdOrCompanyName.setText("Company Name");
        }
    }

    /** Takes the user to the main screen.
     * @param event This parameter is not used
     */
    public void toMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage addPartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1463, 600 );
        addPartStage.setTitle("Inventory Management System");
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }




}