package wgu.c482;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ModifyPartController class controls the modification of parts in the inventory management system.
 * It handles actions related to modifying parts, such as updating part information and canceling modifications.
 */
public class ModifyPartController implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceCostTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label machineIdLabel;

    @FXML
    private ToggleGroup radioGroup;

    public int partIndex;

    /**
     * Handles the action when the Outsourced radio button is selected.
     * Changes the label to "Company Name".
     */
    @FXML
    private void handleOutsourcedRadioAction() {
        machineIdLabel.setText("Company Name");
    }

    /**
     * Handles the action when the InHouse radio button is selected.
     * Changes the label to "Machine ID".
     */
    @FXML
    private void handleInHouseRadioAction() {
        machineIdLabel.setText("Machine ID");
    }

    /**
     * Parses an integer from a TextField and handles exceptions.
     *
     * @param textField The TextField to parse.
     * @param fieldName The name of the field for error messages.
     * @return The parsed integer.
     * @throws NumberFormatException If the input is not a valid integer.
     */
    private int parseInteger(TextField textField, String fieldName) throws NumberFormatException {
        try {
            return Integer.parseInt(textField.getText().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " requires a number.");
        }
    }

    /**
     * Generates a unique part ID for a new part.
     *
     * @return The generated unique part ID.
     */
    private int generateUniquePartId() {
        int tempid = (int)(Math.random() * 345);
        for (Part part : Inventory.getAllParts()) {
            if (tempid <= part.getId()) {
                tempid = part.getId() + 1;
            }
        }
        return tempid;
    }

    /**
     * Handles the action when the Save button is clicked to save modifications to a part.
     *
     * @param event The ActionEvent associated with the Save button click.
     */
    @FXML
    private void modifyPartSave(ActionEvent event) {
        int tempid = generateUniquePartId();

        try {
            int partId;
            try {
                partId = Integer.parseInt(idTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Part ID must be a number");
            }
            String partName = nameTxt.getText().trim();
            if (partName.isEmpty()) {
                throw new Exception("You must enter a name");
            }

            int partInventory = parseInteger(invTxt, "Inventory");
            double partPrice;
            try {
                partPrice = Double.parseDouble(priceCostTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Please enter value in decimal");
            }
            int partMin = parseInteger(minTxt, "Minimum");
            int partMax = parseInteger(maxTxt, "Maximum");

            if (partMin > partMax) {
                throw new Exception("Minimum value must be lower than maximum value.");
            }

            if (inHouseRadio.isSelected()) {
                int machId = parseInteger(machineIdTxt, "Machine ID");
                InHouse inHousePart = new InHouse(tempid, partName, partPrice, partInventory, partMin, partMax, machId);
                Inventory.updatePart(partIndex, new InHouse(partId, partName, partPrice, partInventory, partMin, partMax, machId));
            } else {
                String companyName = machineIdTxt.getText().trim();
                if (companyName.isEmpty()) {
                    throw new Exception("Company name cannot be empty");
                }
                Inventory.updatePart(partIndex, new Outsourced(partId, partName, partPrice, partInventory, partMin, partMax, companyName));
                /*Outsourced outsourcedPart = new Outsourced(tempid, partName, partPrice, partInventory, partMin,partMax, companyName);
                Inventory.addPart(outsourcedPart); **/
            }
            modifyPartCancel(event);
        } catch (Exception invalid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Error");
            alert.setContentText(invalid.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Parses and displays data from a selected part for modification.
     *
     * @param selectedPart The selected part to be modified.
     */
    public void parseData(Part selectedPart) {
        if(selectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            handleInHouseRadioAction();
            machineIdTxt.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
        } else if (selectedPart instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            handleOutsourcedRadioAction();
            machineIdTxt.setText(((Outsourced) selectedPart).getCompanyName());
        }
        idTxt.setText(Integer.toString(selectedPart.getId()));
        nameTxt.setText(selectedPart.getName());
        invTxt.setText(Integer.toString(selectedPart.getStock()));
        priceCostTxt.setText(Double.toString(selectedPart.getPrice()));
        maxTxt.setText(Integer.toString(selectedPart.getMax()));
        minTxt.setText(Integer.toString(selectedPart.getMin()));
        partIndex = Inventory.getAllParts().indexOf(selectedPart);
    }

    /**
     * Handles the action when the Cancel button is clicked to cancel part modification.
     *
     * @param event The ActionEvent associated with the Cancel button click.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void modifyPartCancel(ActionEvent event) throws IOException {
        // Logic for handling the Cancel button click
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic
    }
}
