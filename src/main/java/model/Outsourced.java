package model;

/**
 * The Outsourced class represents a part that is obtained from an external company.
 * It extends the Part class and includes information about the company supplying the part.
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructs a new Outsourced part with the specified attributes.
     *
     * @param id            The unique identifier of the part.
     * @param name          The name of the part.
     * @param price         The price of the part.
     * @param stock         The current stock level of the part.
     * @param min           The minimum allowed stock level of the part.
     * @param max           The maximum allowed stock level of the part.
     * @param companyName   The name of the company supplying the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the name of the company supplying the part.
     *
     * @param companyName The name of the company.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the name of the company supplying the part.
     *
     * @return The name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }
}
