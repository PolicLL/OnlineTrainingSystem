package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

	Page<Measurement> findByContractIdOrderByMeasurementDateDesc(UUID contractId, Pageable pageable);

	List<Measurement> findByContractIdOrderByMeasurementDateAsc(UUID contractId);

	@Query("SELECT COUNT(m) FROM Measurement m")
	int countMeasurements();

}
