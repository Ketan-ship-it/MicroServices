package com.Ketan.ECommerce.ShippingService.services;

import com.Ketan.ECommerce.ShippingService.client.client.OrderFeignClient;
import com.Ketan.ECommerce.ShippingService.dto.BillDto;
import com.Ketan.ECommerce.ShippingService.dto.OrderDto;
import com.Ketan.ECommerce.ShippingService.dto.ShippingDto;
import com.Ketan.ECommerce.ShippingService.entities.ShippingOrder;
import com.Ketan.ECommerce.ShippingService.entities.ShippingPartners;
import com.Ketan.ECommerce.ShippingService.entities.enums.ShippingStatus;
import com.Ketan.ECommerce.ShippingService.repository.ShippingOrderRepository;
import com.Ketan.ECommerce.ShippingService.repository.ShippingPartnersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShippingService {

    static String address = "Jhansi,UP";
    private final ShippingOrderRepository shippingOrderRepository;
    private final ShippingPartnersRepository shippingPartnersRepository;
    private final OrderFeignClient orderFeignClient;
    private final ModelMapper modelMapper;


    public String incomingDeliveryOrders(OrderDto orderDto) {
        List<ShippingPartners> deliverers = shippingPartnersRepository.findByLocation(address);
        if(deliverers.isEmpty()){
            return "not accepted";
        }
        ShippingOrder shippingOrder = ShippingOrder
                .builder()
                .order_id(orderDto.getId())
                .shippingStatus(ShippingStatus.AT_DELIVERERS_WAREHOUSE)
                .shippedTo(deliverers.getFirst())
                .build();
        shippingOrderRepository.save(shippingOrder);

        return "accepted";
    }

    public BillDto shippingBill(Long id) {
        ShippingOrder shippingOrder = shippingOrderRepository.findById(id).orElseThrow(()->new RuntimeException("No such shipment exists"));
        OrderDto orderDto = orderFeignClient.getOrderDto(shippingOrder.getOrder_id()).orElseThrow(()-> new RuntimeException("Kuch toh gadbad hai"));
        return BillDto
                .builder()
                .shippingDto(modelMapper.map(shippingOrder,ShippingDto.class))
                .orderDto(orderDto)
                .build();
    }
}
