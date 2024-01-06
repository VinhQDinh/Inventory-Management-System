package wgu.c482;

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
import model.Inventory;
import model.Part;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The MainMenuController class controls the main menu of the inventory management system.
 * It handles actions related to parts and products, such as adding, modifying, and deleting.
 */
public class MainMenuController implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private TextField searchProductsTxt;

    @FXML
    private TextField searchPartsTxt;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Part, Integer> partIdTableCol;

    @FXML
    private TableColumn<Part, String> partNameTableCol;

    @FXML
    private TableColumn<Part, Integer> partInvTableCol;

    @FXML
    private TableColumn<Part, Double> partPriceCostTableCol;

    @FXML
    private TableColumn<Product, Integer> productIdTableCol;

    @FXML
    private TableColumn<Product, String> productNameTableCol;

    @FXML
    private TableColumn<Product, Integer> productInvTableCol;

    @FXML
    private TableColumn<Product, Double> productPriceCostTableCol;

    @FXML
    private Button addPartsButton;

    @FXML
    private Button modifyPartsButton;

    @FXML
    private Button deletePartsButton;

    @FXML
    private Button addProductsButton;

    @FXML
    private Button modifyProductsButton;

    @FXML
    private Button deleteProductsButton;

    @FXML
    private Button exitButton;

    @FXML
    private void addParts(ActionEvent event) throws IOException {
        // Implementation for the Add Parts button action
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void modifyParts(ActionEvent event) throws IOException{
        // Implementation for the Modify Parts button action
        Part partSelected = partsTable.getSelectionModel().getSelectedItem();
        if(partSelected != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            Parent root = loader.load();
            ModifyPartController controller = loader.getController();

            if(controller != null) {
                controller.parseData(partSelected);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("Controller Error");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select item to modify");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteParts(ActionEvent event) {
        // Implementation for the Delete Parts button action
        Part partDelete = partsTable.getSelectionModel().getSelectedItem();
        if (partDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete " + partsTable.getSelectionModel().getSelectedItem().getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;
            }
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(partDelete);
                partsTable.setItems(Inventory.getAllParts());
                for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
                    if (Inventory.getAllProducts().get(i).getAllAssociatedParts().contains(partDelete)) {
                        Inventory.getAllProducts().get(i).deleteAssociatedPart(partDelete);
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    @FXML
    private void partSearch(ActionEvent event) throws IOException {
        // Searches the table
        String search = searchPartsTxt.getText().trim();
        ObservableList<Part> result = Inventory.lookupPart(search);
        partsTable.setItems(result);

        if (result.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Product was not found.");
            alert.showAndWait();
        }
    }

    @FXML
    private void addProducts(ActionEvent event) throws IOException {
        // Implementation for the Add Products button action
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    private void modifyProducts(ActionEvent event) throws IOException {
        // Implementation for the Modify Products button action
        Product productSelected = productsTable.getSelectionModel().getSelectedItem();
        if (productSelected != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            Parent root = loader.load();
            ModifyProductController controller = loader.getController();

            if(controller != null) {
                controller.parseProductData(productSelected);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("Controller Error");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select item to modify");
            alert.showAndWait();
            }
        }



    @FXML
    private void deleteProducts(ActionEvent event) {
        // Implementation for the Delete Products button action
        Product productDelete = productsTable.getSelectionModel().getSelectedItem();
        if (productDelete != null) {
            if (productDelete.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Do you want to delete " + productDelete.getName() + " ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(productDelete);
                    productsTable.setItems(Inventory.getAllProducts());
                }
            } else {
                Alert cautionAlert = new Alert(Alert.AlertType.WARNING, "Deleting product is not allowed. It is associated with at least one part.");
                cautionAlert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product.");
            alert.showAndWait();
        }
    }

    @FXML
    private void searchProductsTxt(ActionEvent event) throws IOException {
        // Searches the table
        String search = searchProductsTxt.getText().trim();
        ObservableList<Product> result = Inventory.lookupProduct(search);
        productsTable.setItems(result);

        if (result.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Product was not found.");
            alert.showAndWait();
        }
    }


    @FXML
    private void exitInventory(ActionEvent event) {
        // Implementation for the Exit Inventory button action
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        partIdTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvTableCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostTableCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());
        productIdTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvTableCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCostTableCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.setItems(Inventory.getAllProducts());
    }

    public void addNewPart(Part newPart) {
        Inventory.addPart(newPart);
        partsTable.setItems(Inventory.getAllParts());
    }
}