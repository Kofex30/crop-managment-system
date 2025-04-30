import java.util.ArrayList;
import java.util.List;

public class Store implements CropKeeper {

    // Attributes
    public String id; // Start with digit 5
    public String name;
    public double maxCapacityArea; // in square meters
    public double usedCapacityArea; // in square meters
    public static double KG_PER_SQUARE_METER = 10.0;

    public List<Fruit> fruitList;
    public List<Vegetable> vegetableList;

    // Constructor
    public Store(String name, String id, double maxCapacityArea, double KG_PER_SQUARE_METER) {
        if (!id.startsWith("5")) {
            throw new IllegalArgumentException("Store ID must start with the digit 5.");
        }
        this.id = id;
        this.name = name;
        this.maxCapacityArea = maxCapacityArea;
        this.usedCapacityArea = 0;
        this.fruitList = new ArrayList<>();
        this.vegetableList = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxCapacityArea() {
        return maxCapacityArea;
    }

    public double getUsedCapacityArea() {
        return usedCapacityArea;
    }

    public double getAvailableCapacity() {
        return maxCapacityArea - usedCapacityArea;
    }



    public List<Fruit> getFruitList() {
        return this.fruitList;
    }

    // CropKeeper method
    @Override
    public void storeCrop(Fruit fruit) {
        try {
            importCrop(fruit);
        } catch (CapacityNotEnoughException e) {
            System.out.println(e.getMessage());
        }
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

    // Methods
    public boolean canBeStored(Fruit fruit) {
        double requiredSpace = fruit.getWeight() / KG_PER_SQUARE_METER;
        return !(requiredSpace <= getAvailableCapacity());
    }

    public void importCrop(Fruit fruit) throws CapacityNotEnoughException {
        if (canBeStored(fruit)) {
            throw new CapacityNotEnoughException("Not enough capacity to store the fruit.");
        }

        for (Fruit f : fruitList) {
            if (f.getName().equals(fruit.getName()) && f.getTaste().equals(fruit.getTaste())) {
                f.setWeight(f.getWeight() + fruit.getWeight());
                usedCapacityArea += fruit.getWeight() / KG_PER_SQUARE_METER;
                return;
            }
        }

        fruitList.add(fruit);
        usedCapacityArea += fruit.getWeight() / KG_PER_SQUARE_METER;
    }

    public void exportCrop(Fruit fruit) throws FruitNotFoundException {
        for (Fruit f : fruitList) {
            if (f.getName().equals(fruit.getName()) && f.getTaste().equals(fruit.getTaste())) {
                fruitList.remove(f);
                usedCapacityArea -= f.getWeight() / KG_PER_SQUARE_METER;
                return;
            }
        }
        throw new FruitNotFoundException("Fruit not found in the store.");
    }

    static void addFruitToStore(Fruit fruit, Store store) {

        if (store != null && store.getAvailableCapacity() >= fruit.getWeight()) {
            store.addFruit(fruit);
            System.out.println(fruit.getName() + " added to store.");
        } else {
            System.out.println("Not enough capacity in the store.");
        }
    }


    public void addFruit(Fruit fruit) {
        fruitList.add(fruit);
    }


    public void addVegetable (Vegetable vegetable) {
        vegetableList.add(vegetable);
    }
}
