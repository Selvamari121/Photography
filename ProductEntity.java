package com.lashron.springboot.photography.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="ProductPhotography")
public class ProductEntity {
   
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID productId;

   @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<PaymentScheduleEntity> paymentSchedules = new HashSet<>();

   public UUID getProductId() {
       return productId;
   }

   public void setProductId(UUID productId) {
       this.productId = productId;
   }

   public Set<PaymentScheduleEntity> getPaymentSchedules() {
       return paymentSchedules;
   }

   public void setPaymentSchedules(Set<PaymentScheduleEntity> paymentSchedules) {
       this.paymentSchedules = paymentSchedules;
   }
}
