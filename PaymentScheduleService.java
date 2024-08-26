package com.lashron.springboot.photography.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lashron.springboot.photography.entity.PaymentScheduleEntity;
import com.lashron.springboot.photography.repo.PaymentScheduleRepository;

@Service
public class PaymentScheduleService {

   @Autowired
   private PaymentScheduleRepository repository;
    
   public PaymentScheduleEntity createSchedule(BigDecimal scheduleAmount, Date paymentDate) {
      PaymentScheduleEntity schedule = new PaymentScheduleEntity();
      schedule.setScheduleAmount(scheduleAmount);
      schedule.setPaymentDate(paymentDate);

      return repository.save(schedule);
   }

   public void deleteSchedule(UUID scheduleId) {
      if (!repository.existsById(scheduleId)) {
         throw new RuntimeException("Schedule not found");
      }
      repository.deleteById(scheduleId);
   }

   public PaymentScheduleEntity viewSchedule(UUID scheduleId) {
      return repository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
   }

   public PaymentScheduleEntity editSchedule(UUID scheduleId, BigDecimal scheduleAmount, Date paymentDueDate, Boolean isPaid, Date paymentDate) {
      PaymentScheduleEntity schedule = repository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
        
      if (scheduleAmount != null) {
         schedule.setScheduleAmount(scheduleAmount);
      }
      if (paymentDueDate != null) {
         schedule.setPaymentDueDate(paymentDueDate);
      }
      if (isPaid != null) {
         schedule.setIsPaid(isPaid);
      }
      if (paymentDate != null) {
         schedule.setPaymentDate(paymentDate);
      }
      return repository.save(schedule);
   }

}





   // public List<PaymentScheduleEntity> viewAllSchedules(UUID projectId) {
      
   // }  

   //  public List<PaymentScheduleEntity> viewAllSchedules(UUID projectId) {
   //      return repository.findByProject_ProjectId(projectId);
   //  


   //schedule.setProjectId(projectId);
   //public PaymentScheduleEntity createSchedule(UUID projectId, BigDecimal scheduleAmount, Date paymentDate) 


   // Validate inputs
   // Check if project exists (implement validation)
   // Validate scheduleAmount
   // Validate paymentDate








      // @Autowired
   //  private PaymentScheduleRepository repository;

   //  public PaymentScheduleEntity createSchedule(PaymentScheduleEntity schedule) {
   //      return repository.save(schedule);
   //  }

   //  public void deleteSchedule(Long scheduleId) {
   //      repository.deleteById(scheduleId);
   //  }

   //  public Optional<PaymentScheduleEntity> viewSchedule(Long scheduleId) {
   //      return repository.findById(scheduleId);
   //  }

   //  public List<PaymentScheduleEntity> viewAllSchedules(Long projectId) {
   //      return repository.findByProjectId(projectId);
   //  }

   //  public PaymentScheduleEntity editSchedule(Long scheduleId, BigDecimal scheduleAmount, LocalDate paymentDueDate, Boolean isPaid, LocalDate paymentDate) {
   //      Optional<PaymentScheduleEntity> existingSchedule = repository.findById(scheduleId);
   //      if (existingSchedule.isPresent()) {
   //          PaymentScheduleEntity schedule = existingSchedule.get();
   //          if (scheduleAmount != null) schedule.setScheduleAmount(scheduleAmount);
   //          if (paymentDueDate != null) schedule.setPaymentDueDate(paymentDueDate);
   //          if (isPaid != null) schedule.setIsPaid(isPaid);
   //          if (paymentDate != null) schedule.setPaymentDate(paymentDate);
   //          return repository.save(schedule);
   //      } else {
   //          return null;
   //      }
   //  }