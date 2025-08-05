package com.Ketan.ECommerce.ShippingService.repository;

import com.Ketan.ECommerce.ShippingService.entities.ShippingPartners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingPartnersRepository extends JpaRepository<ShippingPartners,Long> {

    List<ShippingPartners> findByLocation(String address);

}
