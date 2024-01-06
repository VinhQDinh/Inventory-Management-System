package wgu.c482;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import model.Inventory;
import model.Part;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddProductController class controls the logic and behavior of the Add Product screen.
 * It allows users to add new products to the inventory, associating them with parts.
 */
public class AddProductController implements Initializable {

    Parent scene;
    Stage stage;


    @FXML
    private TextField addProductSearch;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partInvCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableColumn<?, ?> associatedPartIdCol;

    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    @FXML
    private TableColumn<?, ?> associatedPartInvCol;

    @FXML
    private TableColumn<?, ?> associatedPartPriceCol;

    @FXML
    private Button addPartToAssociated;

    @FXML
    private Button removeAssociatedPart;

    @FXML
    private Button saveAddProduct;

    @FXML
    private Button cancelAddProduct;

    @FXML
    private TextField minTxt;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Parses the integer from a TextField and throws an exception if the input is not a valid integer.
     *
     * @param textField The TextField containing the integer value.
     * @param fieldName The name of the field for better error messages.
     * @return The parsed integer value.
     * @throws NumberFormatException If the input is not a valid integer.
     */
    private int parseInteger(TextField textField, String fieldName) throws NumberFormatException {
        try {
            return Integer.parseInt(textField.getText().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " requires a number");
        }
    }

    /**
     * Generates a unique product ID by finding the maximum ID in the existing parts and incrementing it.
     *
     * @return A unique product ID.
     */
    private int generateUniqueProductId() {
        int tempid = (int) (Math.random() * 345);
        for (Part part : Inventory.getAllParts()) {
            if (tempid <= part.getId()) {
                tempid = part.getId() + 1;
            }
        }
        return tempid;
    }

    /**
     * Handles the action when the "Add" button is clicked to associate a part with the product.
     */
    @FXML
    private void addPartToAssociated(ActionEvent event) {
        // Logic for handling the Add button click
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            associatedPartTable.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the action when the "Remove" button is clicked to remove an associated part from the product.
     */
    @FXML
    private void removeAssociatedPart(ActionEvent event) {
        // Logic for handling the Remove Associated Part button click
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.remove(selectedPart);
            associatedPartTable.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the action when the "Save" button is clicked to save the new product to the inventory.
     *
     * @param event The action event triggered by clicking the Save button.
     * @throws IOException If an error occurs during the transition to another scene.
     */
    @FXML
    private void saveAddProduct(ActionEvent event) throws IOException {
        // Logic for handling the Save button click

        try {
            String productName = nameTxt.getText().trim();
            if (productName.isEmpty()) {
                throw new Exception("You must enter a name");
            }

            int productInventory = parseInteger(invTxt, "Inventory");
            double productPrice;
            try {
                productPrice = Double.parseDouble(priceTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Please enter value in decimal form.");
            }
            int productMin = parseInteger(minTxt, "Minimum");
            int productMax = parseInteger(maxTxt, "Maximum");

            if (productMin > productMax) {
                throw new Exception("Minimum value must be lower than maximum value.");
            }
            Product product = new Product(generateUniqueProductId(), productName, productPrice, productInventory, productMin, productMax);
            Inventory.addProduct(product);
            cancelAddProduct(event);
        } catch (Exception invalid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Error");
            alert.setContentText(invalid.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Handles the action when the "Cancel" button is clicked to return to the main menu.
     *
     * @param event The action event triggered by clicking the Cancel button.
     * @throws IOException If an error occurs during the transition to another scene.
     */
    @FXML
    private void cancelAddProduct (ActionEvent event) throws IOException {
        // Logic for handling the Cancel button click
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the action when the Enter key is pressed in the search field to filter parts.
     *
     * @param event The action event triggered by pressing the Enter key in the search field.
     */
    @FXML
    private void OnActionAddProductSearch(ActionEvent event) {
        String search = addProductSearch.getText().trim();
        ObservableList<Part> result = Inventory.lookupPart(search);
        partsTable.setItems(result);

        if (result.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Part not found.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTable.setItems(associatedParts);
    }
}