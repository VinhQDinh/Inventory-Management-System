package model;

/*
 * Constructor for creating an InHouse part.
 *
 * @param id        the ID of the part
 * @param name      the name of the part
 * @param price     the price of the part
 * @param stock     the current stock level of the part
 * @param min       the minimum stock level allowed for the part
 * @param max       the maximum stock level allowed for the part
 * @param machineId the machine ID for in-house parts
 */

public class InHouse extends Part {

    int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }
}
