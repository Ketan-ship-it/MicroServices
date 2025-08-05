package com.Ketan.ECommerce.ShippingService.dto;

import com.Ketan.ECommerce.ShippingService.entities.ShippingPartners;
import com.Ketan.ECommerce.ShippingService.entities.enums.ShippingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ShippingDto {

    private ShippingStatus shippingStatus;
    private Long order_id;
    private ShippingPartnersDto shippedTo;
}
