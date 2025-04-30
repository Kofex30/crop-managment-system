public class Vegetable extends Crop implements Comparable<Vegetable> {


    // Attributes
    public String cityName;
    public long cropKeeperID; // Can be Store or Supplier

    // Constructor
    public Vegetable(String name, String type, double weight, String cityName, long cropKeeperID) {
        super(name, type, weight);  // Calling the parent (Crop) constructor
        this.cityName = cityName;
        this.cropKeeperID = cropKeeperID;
    }

    // Implement abstract methods
    @Override
    public String toString() {
        return super.toString() + ", cityName=" + cityName + ", cropKeeperID=" + cropKeeperID + "]";
    }

    @Override
    public void consumeIt() {
        System.out.println("Eating the " + getName() + " vegetable.");
    }

    @Override
    public void storeIt() {
        System.out.println("Storing the " + getName() + " vegetable in field booths.");
    }

    // Comparable implementation
    @Override
    public int compareTo(Vegetable other) {
        if (this.getName().equals(other.getName())) {
            return 0;
        }
        return Double.compare(this.getWeight(), other.getWeight());
    }

    @Override
    public void storeCrop(Fruit fruit) {

    }

    @Override
    public void howToStore(Supplier supplier) {

    }

    @Override
    public void howToStore(Store store) {

    }

    @Override
    public void howToStoreNew(Fruit fruit) {

    }

    @Override
    public void howToStoreNew(Vegetable vegetable) {

    }
}