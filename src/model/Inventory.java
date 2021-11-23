package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Represents the inventory for the business
 *
  */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds part object to the parts array.
     *
      * @param newPart Part object.
     */
    public static void addParts(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds product object to the products array.
     *
     * @param newProduct Product object.
     */
    public static void addProducts(Product newProduct){

        allProducts.add(newProduct);
    }

    /** Returns part object by matching the part's index number to
     * part id number. Since arrays are 0 based we must subtract 1
     * from part id number.
     *
      * @param partId unique part id created when part object is constructed.
     * @return  parts object by i
     */
    public static Part lookupPart(int partId){
        return allParts.get(partId-1);
    }

    /** Searches and finds product object by matching the product's index number to
     * product's id number. Since arrays are 0 based we must subtract 1
     * from the product's id number.
     *
     * @param productId unique part id created when part object is constructed.
     * @return  parts object by i
     */
    public static Product lookupProduct(int productId){

        return allProducts.get(productId-1);
    }

    /** Finds all parts in the parts array that contain the search string.
     * in its name.
     *
     * @param partName the search string.
     * @return returns an array of all parts found that contain the search string.
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part onePart : allParts) {
            if (onePart.getName().contains(partName)) {
                partsFound.add(onePart);
            }
        }
        return partsFound;
    }

    /** Finds all products in the products array that contain the search string.
     * in its name.
     *
     * @param productName the search string.
     * @return returns an array of all products found that contain the search string.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> prodFound = FXCollections.observableArrayList();
        for (Product onePart : allProducts) {
            if (onePart.getName().contains(productName)) {
                prodFound.add(onePart);
            }
        }
        return prodFound;
    }

    /**Replaces a part object in the part array at the specified index location.
     *
     * @param index index of part object to be replaced.
     * @param selectedPart part object that will be inserted at the specified index.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**Replaces a product object in the product array at the specified index location.
     *
     * @param index index of product object to be replaced.
     * @param newProduct product object that will be inserted at the specified index.
     */
    public static void updateProduct(int index, Product newProduct){

        allProducts.set(index, newProduct);
    }

    /** Removes specified part object from array,
     *
     * @param selectedPart part object to be removed from array.
     * @return true if part object was removed, else returns false.
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);

    }

    /** Removes specified product object from array,
     *
     * @param selectedProduct product object to be removed from array.
     * @return true if product object was removed, else returns false.
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /** Fetches all objects in the parts array.
     *
     * @return array for part objects
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /** Fetches all objects in the products array.
     *
     * @return array for products objects
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }


}

