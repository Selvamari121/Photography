package com.lashron.springboot.photography.repo;

//import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lashron.springboot.photography.entity.PaymentScheduleEntity;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentScheduleEntity, UUID> {

   Optional<PaymentScheduleEntity> findById(UUID scheduleId);

}



















//List<PaymentScheduleEntity> findByProject_ProjectId(UUID projectId);