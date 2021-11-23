package model;

/** Represents a part
 *
 */
public class InHouse extends Part{
    private int machineId;

    /** Constructor for Inhouse part object
     *
     * @param id unique id, must be integer.
     * @param name name for the part.
     * @param price price for the part, must be a double.
     * @param stock how many parts exits for the part, must be integer.
     * @param min   minimum amount of stock, must be integer.
     * @param max   maximum amount of stock. must be integer.
     * @param machineId machine id is the serial number for the machine.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Fetches the serial number assigned to machine id.
     *
     * @return integer, machine's serial number
     */
    public int getMachineId() {
        return machineId;
    }

    /** Saves integer as machine id.
     *
     * @param machineId serial number for machine.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
