package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class manages the collection of parts and products in the system.
 * Provides methods for adding, looking up, updating, and deleting parts and products.
 */
public class Inventory {

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Adds a part to the inventory.
     *
     * @param part The part to be added.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     * Adds a product to the inventory.
     *
     * @param product The product to be added.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     * Looks up a part by its ID.
     *
     * @param partId The ID of the part to look up.
     * @return The found part, or null if not found.
     */
    public static Part lookupPart(int partId) {
        for (Part part: allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up a part by its ID.
     *
     * @param productId The ID of the part to look up.
     * @return The found part, or null if not found.
     */
    public static Product lookupProduct(int productId) {
        for (Product product: allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Looks up parts by name, case-insensitive.
     *
     * @param partName The name of the part or a part of the name to search for.
     * @return A list of matching parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
               matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**
     * Looks up products by name, case-insensitive.
     *
     * @param productName The name of the product or a part of the name to search for.
     * @return A list of matching products.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     * Updates a part in the inventory at the specified index.
     *
     * @param index         The index of the part to update.
     * @param selectedPart  The updated part.
     */
    public static void updatePart(int index, Part selectedPart) {

        if (index >= 0 && index < allParts.size()) {
            allParts.set(index, selectedPart);
        } else {

            System.err.println("Invalid Update");
        }
    }

    /**
     * Updates a product in the inventory at the specified index.
     *
     * @param index             The index of the product to update.
     * @param selectedProduct   The updated product.
     */
    public static void updateProduct(int index, Product selectedProduct) {

        if (index >= 0 && index < allProducts.size()) {
            allProducts.set(index, selectedProduct);
        } else {

            System.err.println("Invalid Update");
        }
    }

    /**
     * Deletes a part from the inventory.
     *
     * @param selectedPart The part to be deleted.
     * @return True if the part is successfully deleted, false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart != null) {
            return allParts.remove(selectedPart);
        } else {
            System.err.println("Invalid part to delete");
            return false;
        }
    }

    /**
     * Deletes a product from the inventory.
     *
     * @param selectedProduct The product to be deleted.
     * @return True if the product is successfully deleted, false otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct != null) {
            return allProducts.remove(selectedProduct);
        } else {
            System.err.println("Invalid part to delete");
            return false;
        }
    }

    /**
     * Gets the list of all parts in the inventory.
     *
     * @return The list of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets the list of all products in the inventory.
     *
     * @return The list of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
