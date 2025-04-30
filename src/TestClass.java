import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestClass {

    // Lists to store suppliers, stores, and crops
    private static List<Supplier> suppliers = new ArrayList<>();
    private static List<Store> stores = new ArrayList<>();
    private static List<Crop> crops = new ArrayList<>();

    public static void main(String[] args) throws FruitNotFoundException, CapacityNotEnoughException, IOException {
        // Load data from files
        loadSuppliersFromFile();
        loadStoresFromFile();
        loadCropsFromFile();

        // Display main menu
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMain Menu:");
            System.out.println("Please make a choice (0 to exit):");
            System.out.println("1 - Display all suppliers and their crop list");
            System.out.println("2 - Display all stores and their fruit list");
            System.out.println("3 - Buy a fruit crop for a Supplier and add it to the store");
            System.out.println("4 - Sell a fruit crop of a Supplier and add it to the store");
            System.out.println("5 - Remove a fruit from a store");
            System.out.println("6 - Remove a crop from a supplier");
            System.out.println("7 - Add crop to a store or supplier");
            System.out.println("8 - Show remaining budget of a supplier");
            System.out.println("9 - Show remaining capacity of a store");
            System.out.println("0 - Quit");

            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    displaySuppliersAndCrops(); // Çalışıyor.
                    break;
                case 2:
                    displayStoresAndFruits(); // Çalışıyor.
                    break;
                case 3:
                    buyFruitForSupplier(scanner);
                    break;
                case 4:
                    sellFruitFromSupplier(scanner);
                    break;
                case 5:
                    removeFruitFromStore(scanner);
                    break;
                case 6:
                    removeCropFromSupplier(scanner);
                    break;
                case 7:
                    addCrop(scanner);
                    break;
                case 8:
                    showRemainingBudget(scanner); // Çalışıyor.
                    break;
                case 9:
                    showRemainingCapacity(scanner); // Çalışıyor.
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Load the supplier data from a file
    private static void loadSuppliersFromFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/camer/IdeaProjects/Crop_Managment_System/src/DataFile/Suppliers"))) { //TXT FILE EKLENECEK
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                String name = parts[0];
                String id = parts[1];
                double budget = Double.parseDouble(parts[2]);
                suppliers.add(new Supplier(name, id, budget));
            }
        }
    }

    // Load the store data from a file
    private static void loadStoresFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/camer/IdeaProjects/Crop_Managment_System/src/DataFile/Stores"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] storeData = line.split(", ");
                String name = storeData[0];
                String id = storeData[1];  // Store ID ikinci index'te
                double maxCapacityArea = Double.parseDouble(storeData[2]);
                double kgPerSquareMeter = Double.parseDouble(storeData[3]);

                // Store nesnesini oluştur
                Store store = new Store(name, id, maxCapacityArea, kgPerSquareMeter);

                // Store'u ekle
                stores.add(store);
                System.out.println("Store added: " + store.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Load the crops data from a file
    private static void loadCropsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/camer/IdeaProjects/Crop_Managment_System/src/DataFile/Crops"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cropData = line.split(", ");
                String name = cropData[0];
                String type = cropData[1];
                double weight = Double.parseDouble(cropData[2]);

                // Fruit için veriler0
                if (type.equals("fruit")) {
                    String season = cropData[3];
                    String taste = cropData[4];
                    double price = Double.parseDouble(cropData[5]);
                    long cropKeeperID = Long.parseLong(cropData[6]);

                    Fruit fruit = new Fruit(name, type, weight, season, taste, price, cropKeeperID);

                    // Supplier'a ekleme işlemi
                    Supplier supplier = findSupplierById(String.valueOf(cropKeeperID));
                    if (supplier != null) {
                        supplier.addFruit(fruit);
                        System.out.println("Fruit added to supplier: " + fruit.getName());
                    }

                    // Store'a ekleme işlemi
                    Store store = findStoreById(String.valueOf(cropKeeperID));  // Store ID'yi buluyoruz
                    if (store != null) {
                        Store.addFruitToStore(fruit, store); // Store'a meyve ekliyoruz
                    }

                }
                // Vegetable için veriler
                else if (type.equals("vegetable")) {
                    String cityName = cropData[3];
                    long cropKeeperID = Long.parseLong(cropData[4]);

                    Vegetable vegetable = new Vegetable(name, type, weight, cityName, cropKeeperID);

                    // Supplier'a ekleme işlemi
                    Supplier supplier = findSupplierById(String.valueOf(cropKeeperID));
                    if (supplier != null) {
                        supplier.addVegetable(vegetable);
                        System.out.println("Vegetable added to supplier: " + vegetable.getName());
                    }

                    // Store'a ekleme işlemi (İsteğe bağlı olarak sebzeler için de yapılabilir)
                    Store store = findStoreById(String.valueOf(cropKeeperID)); // Store ID'yi buluyoruz
                    if (store != null) {
                        // Sebzeleri depoya ekleme (Opsiyonel, çünkü sebzeler genellikle depolarda tutulmaz)
                        System.out.println("Vegetable does not need to be added to store.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private static void displaySuppliersAndCrops() {
        for (Supplier supplier : suppliers) {
            System.out.println("Supplier: " + supplier.getName() + " (ID: " + supplier.getId() + ")");
            System.out.println("Budget: " + supplier.getBudget() + " TL");


            if (supplier.getCropList().isEmpty()) {
                System.out.println("Crops: None");
            } else {
                System.out.println("Crops:");
                for (Crop crop : supplier.getCropList()) {
                    // Burada crop nesnesinin `toString` metodunun düzgün çalıştığını kontrol edebilirsiniz
                    System.out.println(" - " + crop.toString());
                }
            }
        }
    }

    /* Display all stores and their fruit list, how they are stored and consumed */
    private static void displayStoresAndFruits() {
        for (Store store : stores) {
            System.out.println("Store: " + store.getName() + " (ID: " + store.getId() + ")");
            System.out.println("Max Capacity: " + store.getMaxCapacityArea() + " m²");
            System.out.println("Used Capacity: " + store.getUsedCapacityArea() + " m²");

            // Meyve listesi yazdırma
            System.out.println("Fruits in store: ");
            if (store.getFruitList().isEmpty()) {
                System.out.println(" - No fruits available.");
            } else {
                for (Fruit fruit : store.getFruitList()) {
                    System.out.println(" - " + fruit.getName() + " (Weight: " + fruit.getWeight() + " kg)");
                }
            }
        }
    }



    // Buy a fruit crop for a Supplier and add it to a store’s fruitList
    private static void buyFruitForSupplier(Scanner scanner) throws FruitNotFoundException {
        System.out.println("\nEnter Supplier ID:");
        String supplierId = scanner.nextLine();
        Supplier supplier = findSupplierById(supplierId);

        if (supplier != null) {
            System.out.println("Enter Fruit Name:");
            String fruitName = scanner.nextLine();
            Fruit fruit = findFruitByName(fruitName);

            if (fruit != null) {
                System.out.println("Enter Store ID where the fruit is available:");
                String storeId = scanner.nextLine();
                Store store = findStoreById(storeId);

                if (store != null) {
                    // Store'daki meyveyi al ve Supplier'a ekle
                    if (store.getFruitList().contains(fruit)) {
                        // Store'dan meyveyi çıkar
                        store.exportCrop(fruit);
                        // Supplier'a meyveyi ekle
                        supplier.addFruit(fruit);
                        System.out.println(fruit.getName() + " successfully bought and added to the supplier.");
                    } else {
                        System.out.println("The requested fruit is not available in the store.");
                    }
                } else {
                    System.out.println("Store not found.");
                }
            } else {
                System.out.println("Fruit not available.");
            }
        } else {
            System.out.println("Supplier not found.");
        }
    }



    // Sell a fruit crop of a Supplier and add it to the store’s fruitList
    private static void sellFruitFromSupplier(Scanner scanner) {
        System.out.println("\nEnter Supplier ID:");
        String supplierId = scanner.nextLine();
        Supplier supplier = findSupplierById(supplierId);

        if (supplier != null) {
            System.out.println("Enter Fruit Name to sell:");
            String fruitName = scanner.nextLine();
            Fruit fruit = findFruitInSupplierList(supplier, fruitName);

            if (fruit != null) {
                System.out.println("Enter Store ID to add the fruit to:");
                String storeId = scanner.nextLine();
                Store store = findStoreById(storeId);

                if (store != null) {
                    try {
                        supplier.sellCrop(fruit, store);
                        System.out.println("Fruit successfully sold and added to the store.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Store not found.");
                }
            } else {
                System.out.println("Fruit not found in Supplier’s crop list.");
            }
        } else {
            System.out.println("Supplier not found.");
        }
    }

    // Remove a fruit from a store
    private static void removeFruitFromStore(Scanner scanner) throws FruitNotFoundException {
        System.out.println("\nEnter Store ID:");
        String storeId = scanner.nextLine();
        Store store = findStoreById(storeId);

        if (store != null) {
            System.out.println("Enter Fruit Name to remove from store:");
            String fruitName = scanner.nextLine();
            Fruit fruit = findFruitByName(fruitName);

            if (fruit != null) {
                store.exportCrop(fruit);
                System.out.println("Fruit successfully removed from the store.");
            } else {
                System.out.println("Fruit not found in the store.");
            }
        } else {
            System.out.println("Store not found.");
        }
    }

    // Remove a crop from a supplier
    private static void removeCropFromSupplier(Scanner scanner) throws FruitNotFoundException {
        System.out.println("\nEnter Supplier ID:");
        String supplierId = scanner.nextLine();
        Supplier supplier = findSupplierById(supplierId);

        if (supplier != null) {
            System.out.println("Enter Crop Name to remove from supplier:");
            String cropName = scanner.nextLine();
            Crop crop = findCropInSupplierList(supplier, cropName);

            if (crop != null) {
                supplier.exportFruit(crop);
                System.out.println("Crop successfully removed from the supplier.");
            } else {
                System.out.println("Crop not found in the supplier’s list.");
            }
        } else {
            System.out.println("Supplier not found.");
        }
    }

    // Add a new crop (fruit or vegetable) to a store or supplier
    private static void addCrop(Scanner scanner) throws CapacityNotEnoughException, FruitNotFoundException {
        System.out.println("\nEnter 1 to add crop to a Supplier or 2 to a Store:");
        int option = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.println("Enter Crop Name:");
        String cropName = scanner.nextLine();
        System.out.println("Enter Crop Type (fruit/vegetable):");
        String cropType = scanner.nextLine();
        System.out.println("Enter Crop Weight:");
        double weight = scanner.nextDouble();

        scanner.nextLine();
        System.out.println("Enter CropKeeper ID:");
        long ckID = Long.parseLong(scanner.nextLine());  // CropKeeper ID

        if (option == 1) {
            // Add to Supplier
            System.out.println("Enter Supplier ID:");
            String supplierId = scanner.nextLine();
            Supplier supplier = findSupplierById(supplierId);

            if (supplier != null) {
                if (cropType.equalsIgnoreCase("fruit")) {
                    System.out.println("Enter Price per kg:");
                    double price = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    System.out.println("Enter Cultivated Season:");
                    String season = scanner.nextLine();
                    System.out.println("Enter Crop Taste:");
                    String taste = scanner.nextLine();

                    // Create Fruit object
                    Fruit fruit = new Fruit(cropName, cropType, weight, season, taste, price, ckID);
                    // Add fruit to Supplier
                    supplier.addFruit(fruit);
                    System.out.println("Fruit successfully added to the supplier.");
                } else if (cropType.equalsIgnoreCase("vegetable")) {
                    System.out.println("Enter City Name for the vegetable:");
                    String cityName = scanner.nextLine();

                    // Create Vegetable object
                    Vegetable vegetable = new Vegetable(cropName, cropType, weight, cityName, ckID);
                    // Add vegetable to Supplier
                    supplier.addVegetable(vegetable);
                    System.out.println("Vegetable successfully added to the supplier.");
                }
            } else {
                System.out.println("Supplier not found.");
            }
        } else if (option == 2) {
            // Add to Store
            System.out.println("Enter Store ID:");
            String storeId = scanner.nextLine();
            Store store = findStoreById(storeId);

            if (store != null) {
                if (cropType.equalsIgnoreCase("fruit")) {
                    System.out.println("Enter Price per kg:");
                    double price = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    System.out.println("Enter Cultivated Season:");
                    String season = scanner.nextLine();
                    System.out.println("Enter Crop Taste:");
                    String taste = scanner.nextLine();

                    // Create Fruit object
                    Fruit fruit = new Fruit(cropName, cropType, weight, season, taste, price, ckID);
                    // Add fruit to Store
                    store.addFruit(fruit);
                    System.out.println("Fruit successfully added to the store.");
                } else {
                    System.out.println("Vegetables cannot be added to the store.");
                }
            } else {
                System.out.println("Store not found.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }




    // Show remaining budget of a given supplier
    private static void showRemainingBudget(Scanner scanner) {
        System.out.println("\nEnter Supplier ID:");
        String supplierId = scanner.nextLine();
        Supplier supplier = findSupplierById(supplierId);

        if (supplier != null) {
            System.out.println("Remaining Budget: " + supplier.getBudget() + " TL");
        } else {
            System.out.println("Supplier not found.");
        }
    }

    // Show remaining capacity of a given store
    private static void showRemainingCapacity(Scanner scanner) {
        System.out.println("\nEnter Store ID:");
        String storeId = scanner.nextLine();
        Store store = findStoreById(storeId);

        if (store != null) {
            System.out.println("Remaining Capacity: " + store.getAvailableCapacity() + " m²");
        } else {
            System.out.println("Store not found.");
        }
    }

    // Find supplier by ID
    private static Supplier findSupplierById(String id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(id)) {
                return supplier;
            }
        }
        return null;
    }

    // Find store by ID
    private static Store findStoreById(String id) {
        for (Store store : stores) {
            if (store.getId().equals(id)) {
                return store;
            }
        }
        return null;
    }

    //// Find fruit by name
    //    private static Fruit findFruitByName(String name) {
    //        // Tüm mağazalardaki meyve listelerini kontrol et
    //        for (Store store : stores) {
    //            for (Fruit fruit : store.getFruitList()) {
    //                if (fruit.getName().equals(name)) {
    //                    return fruit; // Meyve bulundu, döndür
    //                }
    //            }
    //        }
    //        return null; // Meyve bulunamadı
    //    }


    private static Fruit findFruitByName(String name) {
        // Tüm mağazalardaki meyve listelerini kontrol et
        for (Store store : stores) {
            for (Fruit fruit : store.getFruitList()) {
                if (fruit.getName().equals(name)) {
                    return fruit; // Meyve bulundu, döndür
                }
            }
        }
        // Tüm supplier'lardaki cropList'lerdeki meyveleri kontrol et
        for (Supplier supplier : suppliers) {
            for (Crop crop : supplier.getCropList()) {
                if (crop instanceof Fruit && crop.getName().equals(name)) {
                    return (Fruit) crop; // Meyve bulundu, döndür
                }
            }
        }

        return null; // Meyve bulunamadı
    }



    // Find fruit in supplier’s crop list
    private static Fruit findFruitInSupplierList(Supplier supplier, String name) {
        for (Crop crop : supplier.getCropList()) {
            if (crop instanceof Fruit && crop.getName().equals(name)) {
                return (Fruit) crop;
            }
        }
        return null;
    }

    // Find fruit in store’s list
    private static boolean findFruitInStoreList(Fruit fruit, Store store) {
        // Mağazadaki meyve listesini kontrol et
        for (Fruit f : store.getFruitList()) {
            // Meyve ismi ve ağırlığını karşılaştır
            if (f.getName().equals(fruit.getName()) && f.getWeight() == fruit.getWeight()) {
                return true; // Meyve bulundu
            }
        }
        return false; // Meyve bulunamadı
    }

    // Find crop in supplier’s list
    private static Crop findCropInSupplierList(Supplier supplier, String name) {
        for (Crop crop : supplier.getCropList()) {
            if (crop.getName().equals(name)) {
                return crop;
            }
        }
        return null;
    }
}
