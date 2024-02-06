package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CoachRepository extends JpaRepository<Coach, UUID>, JpaSpecificationExecutor<Coach> {

	boolean existsByUser(User user);

	@Query("SELECT c.monthlyPrice FROM Coach c WHERE c.id = :coachId")
	Double findMonthlyPriceById(@Param("coachId") UUID coachId);

	Coach findByUserId(UUID userId);

}
