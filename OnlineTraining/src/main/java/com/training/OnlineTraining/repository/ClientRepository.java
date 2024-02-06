package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.Client;
import com.training.OnlineTraining.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

	boolean existsByUser(User user);

	Optional<Client> findByUserId(UUID userId);

	@Query("SELECT COUNT(c) FROM Client c")
	int countClients();

}
