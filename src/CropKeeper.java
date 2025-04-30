interface CropKeeper {
    void storeCrop(Fruit fruit);

    void howToStore(Supplier supplier);

    void howToStore(Store store);

    void howToStoreNew(Fruit fruit);
    void howToStoreNew(Vegetable vegetable);
}