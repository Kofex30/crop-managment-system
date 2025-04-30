import java.util.ArrayList;
import java.util.List;

public class Supplier implements CropKeeper {

    // Attributes
    public String id;
    public String name;
    public double budget; // in TL
    public List<Crop> cropList; // List of crops (fruits and vegetables)

    // Constructor
    public Supplier(String name, String id, double budget) {
        if (!id.startsWith("1")) {
            throw new IllegalArgumentException("Supplier ID must start with the digit 1.");
        }
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.cropList = new ArrayList<>();
    }

    @Override
    public void howToStoreNew(Fruit fruit) {
        System.out.println("f");
    }

    @Override
    public void howToStoreNew(Vegetable vegetable) {
        System.out.println("v");

    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public List<Crop> getCropList() {
        return cropList;
    }

    // Setters
    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void addFruit(Fruit fruit) {
        if (fruit != null) {
            cropList.add(fruit);
            System.out.println("Fruit added to supplier: " + fruit.getName());
        }

        howToStoreNew(fruit);
    }

    public void addVegetable(Vegetable vegetable) {
        if (vegetable != null) {
            cropList.add(vegetable);
            System.out.println("Vegetable added to supplier: " + vegetable.getName());
        }

        howToStoreNew(vegetable);
    }

    private void addVegetableToStore(Vegetable vegetable, Store store) {
        if (store!= null && store.getAvailableCapacity() >= vegetable.getWeight()) {
            store.addVegetable(vegetable);
            System.out.println("Vegetable added to store.");
        } else {
            System.out.println("Not enough capacity in the store.");
        }
    }

    // CropKeeper method - howToStore
    @Override
    public void howToStore(Supplier supplier) {
        System.out.println("Fruits are stored in big refrigerators.");
        System.out.println("Vegetables are stored in field booths.");
    }

    @Override
    public void howToStore(Store store) {

    }

    // Method to buy a crop (fruit)
    public void buyCrop(Crop c, Store store) throws SupplierHasNotEnoughMoneyException, FruitNotAvailableException, FruitNotFoundException {
        if (c instanceof Vegetable) {
            throw new IllegalArgumentException("Supplier cannot buy vegetables.");
        }

        // Check if crop is available in the store
        if (store.canBeStored((Fruit) c)) {
            throw new FruitNotAvailableException("Fruit is not available in the store.");
        }

        double totalCost = c.getWeight() * ((Fruit) c).getPrice();

        // Check if the supplier has enough money to buy the crop
        if (budget < totalCost) {
            throw new SupplierHasNotEnoughMoneyException("Not enough money to buy this fruit.");
        }

        // Update fields: buy the crop, update budget and add it to supplier's list
        store.exportCrop((Fruit) c);
        cropList.add(c);
        budget -= totalCost;
    }

    // Method to sell a crop (fruit)
    public void sellCrop(Crop c, Store store) throws FruitNotFoundException, CapacityNotEnoughException {
        if (!(c instanceof Fruit)) {
            throw new IllegalArgumentException("Only fruits can be sold.");
        }

        // Check if the crop exists in the supplier's cropList
        if (!cropList.contains(c)) {
            throw new FruitNotFoundException("This fruit is not found in the supplier's list.");
        }

        // Remove the crop from supplier's list, update the budget, and store the fruit
        cropList.remove(c);
        budget += c.getWeight() * ((Fruit) c).getPrice();
        store.importCrop((Fruit) c);
    }

    // Method to store crop (e.g., store the fruit in store)
    @Override
    public void storeCrop(Fruit fruit) {
        cropList.add(fruit);
    }

    // Method to export a fruit (remove it from the supplier and store it in the store)
    public void exportFruit(Fruit fruit, Store store) throws FruitNotFoundException {
        if (!cropList.contains(fruit)) {
            throw new FruitNotFoundException("Supplier does not have this fruit.");
        }
        cropList.remove(fruit);
        store.storeCrop(fruit);
    }

    // Method to import fruit (move it from store to supplier's cropList)
    public void importFruit(Fruit fruit, Store store) throws FruitNotFoundException {
        store.exportCrop(fruit);
        cropList.add(fruit);
    }

    public void exportFruit(Crop crop) throws FruitNotFoundException {
        // Tedarikçinin ürün listesinde crop var mı diye kontrol ediyoruz.
        if (!cropList.contains(crop)) {
            throw new FruitNotFoundException("Fruit not found in the supplier’s list.");
        }

        // Ürünü tedarikçinin listesinden çıkarıyoruz.
        cropList.remove(crop);

    }
}
