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
import model.Inventory;
import model.InHouse;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddPartController class controls the logic and behavior of the Add Part screen.
 * It allows users to add new parts to the inventory, specifying whether the part is In-House or Outsourced.
 */
public class AddPartController implements Initializable{

    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup radioGroup;

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
    private TextField minTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label machineIdLabel;

    /**
     * Handles the action when the Outsourced radio button is selected.
     * Changes the label to display "Company Name" for the machine id.
     */
    @FXML
    private void handleOutsourcedRadioAction() {
        machineIdLabel.setText("Company Name");
    }


    /**
     * Handles the action when the InHouse radio button is selected.
     * Changes the label to display "Machine ID" for the machine id.
     */
    @FXML
    private void handleInHouseRadioAction() {
        machineIdLabel.setText("Machine ID");
    }

    private int parseInteger(TextField textField, String fieldName) throws NumberFormatException {
        try {
            return Integer.parseInt(textField.getText().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " requires a number.");
        }
    }

    private int generateUniquePartId() {
        int tempid = (int) (Math.random() * 345);
        for (Part part : Inventory.getAllParts()) {
            if (tempid <= part.getId()) {
                tempid = part.getId() + 1;
            }
        }
        return tempid;
    }

    /**
     * Handles the action when the Save button is clicked.
     * Validates the input fields and adds a new part to the inventory.
     *
     * @param event The action event triggered by clicking the Save button.
     * @throws IOException If an error occurs during the transition to another scene.
     */
    @FXML
    private void addPartSave(ActionEvent event) throws IOException {
        int tempid = generateUniquePartId();

        try {
            String partName = nameTxt.getText().trim();
            if (partName.isEmpty()) {
                throw new Exception("You must enter a name");
            }

            int partInventory = parseInteger(invTxt, "Inventory");
            double partPrice;
            try {
                partPrice = Double.parseDouble(priceCostTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Please enter value in decimal for");
            }
            int partMin = parseInteger(minTxt, "Minimum");
            int partMax = parseInteger(maxTxt, "Maximum");

            if (partMin > partMax) {
                throw new Exception("Minimum value must be lower than maximum value.");
            }

            if (inHouseRadio.isSelected()) {
                int machId = parseInteger(machineIdTxt, "Machine ID");
                InHouse inHousePart = new InHouse(tempid, partName, partPrice, partInventory, partMin, partMax, machId);
                Inventory.addPart(inHousePart);
            } else {
                String companyName = machineIdTxt.getText().trim();
                if (companyName.isEmpty()) {
                    throw new Exception("Company name cannot be empty");
                }
                Outsourced outsourcedPart = new Outsourced(tempid, partName, partPrice, partInventory, partMin,partMax, companyName);
                Inventory.addPart(outsourcedPart);
            }
            addPartCancel(event);
        } catch (Exception invalid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Error");
            alert.setContentText(invalid.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Handles the action when the Cancel button is clicked.
     * Transitions back to the main menu screen.
     *
     * @param event The action event triggered by clicking the Cancel button.
     * @throws IOException If an error occurs during the transition to another scene.
     */
    @FXML
    public void addPartCancel(ActionEvent event) throws IOException {
        // Logic for handling the Cancel button click
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

