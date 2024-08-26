package com.lashron.springboot.photography.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lashron.springboot.photography.entity.PaymentScheduleEntity;
import com.lashron.springboot.photography.service.PaymentScheduleService;

@RestController
@RequestMapping("/schedule")
public class PaymentScheduleController {
   @Autowired
   private PaymentScheduleService service;
    
   @PostMapping("/create")
   public ResponseEntity<PaymentScheduleEntity> createSchedule(@RequestParam BigDecimal scheduleAmount, @RequestParam Date paymentDate) {
      PaymentScheduleEntity schedule = service.createSchedule(scheduleAmount, paymentDate);
      
      return ResponseEntity.ok(schedule);
   }
    
   @DeleteMapping("/delete")
   public ResponseEntity<Void> deleteSchedule(@RequestParam UUID scheduleId) {
      service.deleteSchedule(scheduleId);
      return ResponseEntity.noContent().build();
   }
    
   @GetMapping("/view")
   public ResponseEntity<PaymentScheduleEntity> viewSchedule(@RequestParam UUID scheduleId) {
      PaymentScheduleEntity schedule = service.viewSchedule(scheduleId);
      return ResponseEntity.ok(schedule);
   }
    
   @PutMapping("/edit")
   public ResponseEntity<PaymentScheduleEntity> editSchedule(@RequestParam UUID scheduleId, @RequestParam(required = false) BigDecimal scheduleAmount, @RequestParam(required = false) Date paymentDueDate, @RequestParam(required = false) Boolean isPaid, @RequestParam(required = false) Date paymentDate) {
      PaymentScheduleEntity schedule = service.editSchedule(scheduleId, scheduleAmount, paymentDueDate, isPaid, paymentDate);
      return ResponseEntity.ok(schedule);
   }
}


   //public ResponseEntity<PaymentScheduleEntity> createSchedule(@RequestParam UUID projectId, @RequestParam BigDecimal scheduleAmount, @RequestParam Date paymentDate)
   //PaymentScheduleEntity schedule = service.createSchedule(projectId, scheduleAmount, paymentDate);

   // @GetMapping("/viewAll")
   // public ResponseEntity<List<PaymentScheduleEntity>> viewAllSchedules(@RequestParam UUID projectId) {
   //    List<PaymentScheduleEntity> schedules = service.viewAllSchedules(projectId);
   //    return ResponseEntity.ok(schedules);
   // }


      // @Autowired
   // private PaymentScheduleService service;

   // @PostMapping("/create")
   // public ResponseEntity<PaymentScheduleEntity> createSchedule(@RequestBody PaymentScheduleEntity schedule) {
   //     PaymentScheduleEntity createdSchedule = service.createSchedule(schedule);
   //     return ResponseEntity.ok(createdSchedule);
   // }

   // @DeleteMapping("/delete")
   // public ResponseEntity<Void> deleteSchedule(@RequestParam Long scheduleId) {
   //     service.deleteSchedule(scheduleId);
   //     return ResponseEntity.noContent().build();
   // }

   // @GetMapping("/view")
   // public ResponseEntity<PaymentScheduleEntity> viewSchedule(@RequestParam Long scheduleId) {
   //     Optional<PaymentScheduleEntity> schedule = service.viewSchedule(scheduleId);
   //     return schedule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
   // }

   // @GetMapping("/viewAll")
   // public ResponseEntity<List<PaymentScheduleEntity>> viewAllSchedules(@RequestParam Long projectId) {
   //     List<PaymentScheduleEntity> schedules = service.viewAllSchedules(projectId);
   //     return ResponseEntity.ok(schedules);
   // }

   // @PutMapping("/edit")
   // public ResponseEntity<PaymentScheduleEntity> editSchedule(
   //         @RequestParam Long scheduleId,
   //         @RequestParam(required = false) BigDecimal scheduleAmount,
   //         @RequestParam(required = false) LocalDate paymentDueDate,
   //         @RequestParam(required = false) Boolean isPaid,
   //         @RequestParam(required = false) LocalDate paymentDate) {

   //     PaymentScheduleEntity updatedSchedule = service.editSchedule(scheduleId, scheduleAmount, paymentDueDate, isPaid, paymentDate);
   //     return updatedSchedule != null ? ResponseEntity.ok(updatedSchedule) : ResponseEntity.notFound().build();
   // }