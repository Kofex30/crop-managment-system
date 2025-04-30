public abstract class Crop implements CropKeeper {

    // Attributes
    public String name;
    public double weight;
    public String type; // in kilo


    // Constructor
    public Crop(String name, String type, double weight) {
        this.name = name;
        this.weight = weight;
        this.type = type;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Abstract Methods
    @Override
    public String toString() {
        return getName() + ", " + getType() + ", " + getWeight() + " kg";
    }

    public abstract void consumeIt();

    public abstract void storeIt() throws CanNotBeStoredException;
}