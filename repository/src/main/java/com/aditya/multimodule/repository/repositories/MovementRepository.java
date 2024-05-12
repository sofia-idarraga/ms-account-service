package com.aditya.multimodule.repository.repositories;

import com.aditya.multimodule.repository.enitites.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Long> {
}
