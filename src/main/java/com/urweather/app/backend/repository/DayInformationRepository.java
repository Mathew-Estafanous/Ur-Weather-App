package com.urweather.app.backend.repository;

import java.util.ArrayList;
import java.util.List;

import com.urweather.app.backend.entity.DayInformationEntity;

import org.springframework.stereotype.Repository;

@Repository
public class DayInformationRepository {

    private List<DayInformationEntity> repositoryOfDays = new ArrayList<>();

    public void saveAll(List<DayInformationEntity> entitiesToSave) {
        entitiesToSave.forEach(entity -> repositoryOfDays.add(entity));
    }

    public void deleteAll() {
        repositoryOfDays.clear();
    }

    public List<DayInformationEntity> findAll() {
        return repositoryOfDays;
    }

    public DayInformationEntity findAll(int index) {
        return repositoryOfDays.get(index);
    }

    public int count() {
        return repositoryOfDays.size();
    }
}