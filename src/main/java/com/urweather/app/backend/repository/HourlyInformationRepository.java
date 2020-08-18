package com.urweather.app.backend.repository;


import com.urweather.app.backend.entity.HourlyInformationEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyInformationRepository extends JpaRepository<HourlyInformationEntity, Long> {

}