package com.Ketan.ECommerce.ShippingService.entities;

import com.Ketan.ECommerce.ShippingService.entities.enums.ShippingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShippingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ShippingStatus shippingStatus;

    @Column(nullable = false,updatable = false,unique = true)
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "shipped_to_id")
    @JsonIgnore
    private ShippingPartners shippedTo;

}
