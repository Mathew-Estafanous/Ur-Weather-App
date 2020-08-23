package com.urweather.app.backend.repository;

import java.util.ArrayList;
import java.util.List;

import com.urweather.app.backend.entity.HourlyInformationEntity;

import org.springframework.stereotype.Repository;

@Repository
public class HourlyInformationRepository {

    private List<HourlyInformationEntity> repositoryOfHours = new ArrayList<>();

    public void saveAll(List<HourlyInformationEntity> entitiesToSave) {
        entitiesToSave.forEach(entity -> repositoryOfHours.add(entity));
    }

    public void deleteAll() {
        repositoryOfHours.clear();
    }

    public List<HourlyInformationEntity> findAll() {
        return repositoryOfHours;
    }

    public HourlyInformationEntity findAll(int index) {
        return repositoryOfHours.get(index);
    }

    public int count() {
        return repositoryOfHours.size();
    }
}