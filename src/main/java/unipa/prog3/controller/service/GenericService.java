package unipa.prog3.controller.service;

import unipa.prog3.model.DataManager;

public abstract class GenericService {
    protected DataManager dataManager;

    protected GenericService() {
        dataManager = new DataManager();
    }
}
