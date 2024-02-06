package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

	List<Contract> findByClientId(UUID clientId);

	List<Contract> findByCoachId(UUID coachId);

}
