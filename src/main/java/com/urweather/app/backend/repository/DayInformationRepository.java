package com.urweather.app.backend.repository;

import com.urweather.app.backend.entity.DayInformationEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DayInformationRepository extends JpaRepository<DayInformationEntity, Long> {

}