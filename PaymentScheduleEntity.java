package com.lashron.springboot.photography.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="PaymentSchedule")
public class PaymentScheduleEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="scheduleId")
   private UUID scheduleId;

   @Column(name="TotalAmount")
   private BigDecimal totalAmount;

   @Column(name="ScheduleAmount")
   private BigDecimal scheduleAmount;

   @Column(name="PaymentDueDate")
   private Date paymentDueDate;

   @Column(name="IsPaid")
   private Boolean isPaid;

   @Column(name="PaymentDate")
   private Date paymentDate;

   @ManyToOne
   @JoinColumn(name="productId")
   private ProductEntity product;

   public UUID getScheduleId() {
      return scheduleId;
   }

   public void setScheduleId(UUID scheduleId) {
      this.scheduleId = scheduleId;
   }

   public BigDecimal getTotalAmount() {
      return totalAmount;
   }

   public void setTotalAmount(BigDecimal totalAmount) {
      this.totalAmount = totalAmount;
   }

   public BigDecimal getScheduleAmount() {
      return scheduleAmount;
   }

   public void setScheduleAmount(BigDecimal scheduleAmount) {
      this.scheduleAmount = scheduleAmount;
   }

   public Date getPaymentDueDate() {
      return paymentDueDate;
   }

   public void setPaymentDueDate(Date paymentDueDate) {
      this.paymentDueDate = paymentDueDate;
   }

   public Boolean getIsPaid() {
      return isPaid;
   }

   public void setIsPaid(Boolean isPaid) {
      this.isPaid = isPaid;
   }

   public Date getPaymentDate() {
      return paymentDate;
   }

   public void setPaymentDate(Date paymentDate) {
      this.paymentDate = paymentDate;
   }

   public ProductEntity getProduct() {
      return product;
   }

   public void setProduct(ProductEntity product) {
      this.product = product;
   }

  
}






   // public PaymentScheduleEntity(Long scheduleId,Long ProjectId,BigDecimal TotalAmount,BigDecimal ScheduleAmount,Date PaymentDueDate,Boolean IsPaid,Date PaymentDate){
   //    this.scheduleId = scheduleId;
   //    this.TotalAmount = TotalAmount;
   //    this.ScheduleAmount = ScheduleAmount;
   //    this.PaymentDueDate = PaymentDueDate;
   //    this.IsPaid = IsPaid;
   //    this.PaymentDate = PaymentDate;

   // }

   // @ManyToOne
   // @JoinColumn(name = "project_id", nullable = false)
   // private Project project;

   // @Column(name="ProjectId")
   // private UUID ProjectId;

   // public Long getProjectId() {
   //    return ProjectId;
   // }
   // public void setProjectId(Long projectId) {
   //    ProjectId = projectId;
   // }
   // import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;