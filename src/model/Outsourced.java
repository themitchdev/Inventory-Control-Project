package model;

/** Represent part from an outside source. Inherits from Parts class.
 *
 */
public class Outsourced extends Part {
    private String companyName;

    /**Constructor for Oursourced class.
     *
     * @param id unique id, must be integer.
     * @param name name for the part.
     * @param price price for the part, must be a double.
     * @param stock how many parts exits for the part, must be integer.
     * @param min   minimum amount of stock, must be integer.
     * @param max   maximum amount of stock. must be integer.
     * @param companyName company name from where the part was sourced.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Fetches the company name.
     *
     * @return current company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**Sets the company name.
     *
     * @param companyName company name from where the part was sourced.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
