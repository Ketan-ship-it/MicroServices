package com.Ketan.ECommerce.ShippingService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipping_partners")
public class ShippingPartners {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "businessname")
    private String businessName;

    private String location;

    @OneToMany(mappedBy = "shippedTo",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<ShippingOrder> shippingOrders;
}
