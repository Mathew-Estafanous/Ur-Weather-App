package com.urweather.app.backend.repository;

import java.util.ArrayList;
import java.util.List;

public class ListRepository<T> {

    private List<T> repositoryOfItems = new ArrayList<>();

    public void saveAll(List<T> entitiesToSave) {
        entitiesToSave.forEach(entity -> repositoryOfItems.add(entity));
    }

    public void deleteAll() {
        repositoryOfItems.clear();
    }

    public List<T> findAll() {
        return repositoryOfItems;
    }

    public T find(int index) {
        return repositoryOfItems.get(index);
    }

    public int count() {
        return repositoryOfItems.size();
    }
}