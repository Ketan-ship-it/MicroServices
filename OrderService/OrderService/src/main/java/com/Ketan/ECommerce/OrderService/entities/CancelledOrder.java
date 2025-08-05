package com.Ketan.ECommerce.OrderService.entities;

import com.Ketan.ECommerce.OrderService.entities.enums.cancelReason;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelledOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    @Enumerated(value = EnumType.STRING)
    private cancelReason reason;

    private String issue;
}
