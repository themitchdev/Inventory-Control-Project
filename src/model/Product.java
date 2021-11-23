package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swt.FXCanvas;

/** Represents a single product.
 *
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**Creates a product object.
     *
     * @param id unique id, must be integer.
     * @param name name for the product.
     * @param price price for the product, must be a double.
     * @param stock how many products exits for the product, must be integer.
     * @param min   minimum amount of stock, must be integer.
     * @param max   maximum amount of stock. must be integer.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /** Sets part number id.
     *
     * @param id part number id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Fetches the product name.
     *
     * @return product name.
     */
    public String getName() {
        return name;
    }

    /** Sets the product name.
     *
     * @param name product name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Fetches the product price.
     *
     * @return product price.
     */
    public double getPrice() {
        return price;
    }

    /** Sets the price for the product.
     *
     * @param price product price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Fetches the product stock quantity.
     *
     * @return product stock amount.
     */
    public int getStock() {
        return stock;
    }

    /** Sets the product stock amount.
     *
     * @param stock product stock amount.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Fetches the product's minimum stock.
     *
      * @return minimum amount of stock.
     */
    public int getMin() {
        return min;
    }

    /** Sets the minimum amount of stock for product.
     *
     * @param min minimum amount of stock.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Fetches the max amount of stock for that specific product.
     *
     * @return maximum stock of product.
     */
    public int getMax() {
        return max;
    }

    /** Sets the maximum stock of product.
     *
     * @param max stock amount for product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds part to array of associated parts.
     *
     * @param part part associated to particular product,
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** Removes part object from array of associated parts.
     *
     * @param selectedAssociatedPart part to be deleted.
     * @return  true if part was removed else false.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /** Fetches all associated parts.
     *
     * @return array of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }

}
