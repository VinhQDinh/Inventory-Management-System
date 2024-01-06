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
import javafx.fxml.Initializable;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.Product;
import model.Inventory;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Modify Product screen.
 * Handles user interactions and manages the modification of product information.
 */

public class ModifyProductController implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private TextField modifySearch;

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
    private TableView<Part> modifyProductPartTable;

    @FXML
    private TableColumn<?, ?> modifyProductPartIdCol;

    @FXML
    private TableColumn<?, ?> modifyProductPartNameCol;

    @FXML
    private TableColumn<?, ?> modifyProductInvCol;

    @FXML
    private TableColumn<?, ?> modifyProductPriceCol;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<?, ?> associatedModifyProductPartIdCol;

    @FXML
    private TableColumn<?, ?> associatedModifyProductPartNameCol;

    @FXML
    private TableColumn<?, ?> associatedModifyProductPartInvCol;

    @FXML
    private TableColumn<?, ?> associatedModifyProductPartPriceCol;

    @FXML
    private Button addAssociatedPartButton;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private Button saveModifyProductButton;

    @FXML
    private Button cancelModifyPartButton;

    @FXML
    private TextField minTxt;


    @FXML
    private int prodIndex;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Parses an integer from a TextField.
     *
     * @param textField The TextField to parse.
     * @param fieldName The name of the field for error messages.
     * @return The parsed integer.
     * @throws NumberFormatException If the text cannot be parsed as an integer.
     */
    private int parseInteger(TextField textField, String fieldName) throws NumberFormatException {
        try {
            return Integer.parseInt(textField.getText().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + "requires a number.");
        }
    }

    /**
     * Generates a unique product ID.
     *
     * @return The generated unique product ID.
     */
    private int generateUniqueProductId() {
        int tempid = (int)(Math.random() * 345);
        for (Product product : Inventory.getAllProducts()) {
            if (tempid <= product.getId()) {
                tempid = product.getId() + 1;
            }
        }
        return tempid;
    }


    /**
     * Adds an associated part to the product.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void addAssociatedPart(ActionEvent event) {
        // Logic for handling the Add button click
        Part selectedPart = modifyProductPartTable.getSelectionModel().getSelectedItem();
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
     * Removes an associated part from the product.
     *
     * @param event The ActionEvent triggered by the button click.
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
     * Saves the modified product information.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void saveModifyProduct(ActionEvent event) throws IOException{
        // Logic for handling the Save button click
        int tempid = generateUniqueProductId();

        try {
            int productId;
            try {
                productId = Integer.parseInt(idTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Product ID must be a number.");
            }
            String productName = nameTxt.getText().trim();
            if (productName.isEmpty()) {
                throw new Exception("You must enter a neame.");
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
                throw new Exception("Minimum value must be lower that maximum value.");
            }

            Inventory.updateProduct(prodIndex, new Product(productId, productName, productPrice, productInventory, productMin, productMax));
            cancelModifyPart(event);
        } catch (Exception invalid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Error");
            alert.setContentText(invalid.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Cancels the modification of the product.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void cancelModifyPart(ActionEvent event) throws IOException {
        // Logic for handling the Cancel button click
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the search action when the user presses Enter in the search TextField.
     *
     * @param event The ActionEvent triggered by the Enter key press.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void OnActionModifySearch(ActionEvent event) throws IOException {
        String search = modifySearch.getText().trim();
        ObservableList<Part> result = Inventory.lookupPart(search);
        modifyProductPartTable.setItems(result);

        if (result.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Part not found.");
            alert.showAndWait();
        }
    }

    /**
     * Parses the data of the selected product to initialize the modification screen.
     *
     * @param selectedProduct The selected
     */
    public void parseProductData (Product selectedProduct) {
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
        associatedPartTable.setItems(associatedParts);

        idTxt.setText(Integer.toString(selectedProduct.getId()));
        nameTxt.setText(selectedProduct.getName());
        invTxt.setText(Integer.toString(selectedProduct.getStock()));
        priceTxt.setText(Double.toString(selectedProduct.getPrice()));
        maxTxt.setText(Integer.toString(selectedProduct.getMax()));
        minTxt.setText(Integer.toString(selectedProduct.getMin()));
        prodIndex = Inventory.getAllProducts().indexOf(selectedProduct);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic
        modifyProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductPartTable.setItems(Inventory.getAllParts());

        associatedModifyProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedModifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedModifyProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedModifyProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTable.setItems(associatedParts);
    }

}
