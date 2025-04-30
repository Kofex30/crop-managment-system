import java.util.List;

public class Fruit extends Crop implements Comparable<Fruit> {

    // Attributes
    public String season;
    public String taste;
    public double price;
    public long cropKeeperID; // Can be Store or Supplier


    // Constructor
    public Fruit(String name, String type, double weight, String season, String taste, double price, long cropKeeperID) {
        super(name, type, weight);  // Calling the parent (Crop) constructor
        this.season = season;
        this.taste = taste;
        this.price = price;
        this.cropKeeperID = cropKeeperID;
    }

    // Getters
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCropKeeperID() {
        return cropKeeperID;
    }

    public void setCropKeeperID(long cropKeeperID) {
        this.cropKeeperID = cropKeeperID;
    }



    @Override
    public String toString() {
        return super.toString() + ", season=" + season + ", taste=" + taste + ", price=" + price + ", cropKeeperID=" + cropKeeperID + "]";
    }

    @Override
    public void consumeIt() {
        System.out.println("Eating the " + getName() + " fruit.");
    }

    @Override
    public void storeIt() {
        System.out.println("Storing the " + getName() + " fruit in big refrigerators.");
    }

    @Override
    public int compareTo(Fruit other) {
        if (this.getName().equals(other.getName()) && this.taste.equals(other.getTaste())) {
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