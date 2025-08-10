package com.Ketan.ECommerce.OrderService.services;

import com.Ketan.ECommerce.OrderService.clients.InventoryFeignClient;
import com.Ketan.ECommerce.OrderService.clients.ShippingFeignClient;
import com.Ketan.ECommerce.OrderService.dto.OrderCancelDto;
import com.Ketan.ECommerce.OrderService.dto.OrderDto;
import com.Ketan.ECommerce.OrderService.entities.Order;
import com.Ketan.ECommerce.OrderService.entities.OrderItem;
import com.Ketan.ECommerce.OrderService.entities.CancelledOrder;
import com.Ketan.ECommerce.OrderService.entities.enums.OrderStatus;
import com.Ketan.ECommerce.OrderService.repository.CancelOrderRepository;
import com.Ketan.ECommerce.OrderService.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;
    private final ShippingFeignClient shippingFeignClient;
    private final CancelOrderRepository cancelOrderRepository;

    public List<OrderDto> getAllOrders(){
        log.info("Getting all the orders in the Inventory");
        List<Order> inventory = orderRepository.findAll();
        return inventory.stream()
                .map((element) -> modelMapper.map(element, OrderDto.class))
                .collect(Collectors.toList());
    }

    public Optional<OrderDto> getOrderById(Long id){
        log.info("Getting  products in the Inventory by id :{}", id);
        Optional<Order> inventory = orderRepository.findById(id);
        return inventory.map((element) -> modelMapper.map(element, OrderDto.class));
    }

//    @Retry(name = "inventoryRetry" , fallbackMethod = "createOrderFallback") //either this
    @CircuitBreaker(name = "inventoryCircuitBreaker" , fallbackMethod = "createOrderFallback") //or this
    @RateLimiter(name = "inventoryRateLimiter" , fallbackMethod = "createOrderFallback")
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Creating order:");

        log.info("Calling inventory - service");
        Double price = inventoryFeignClient.reduceStocks(orderDto);
        log.info("inventory service is responding ");

        Order order = modelMapper.map(orderDto, Order.class);
        for (OrderItem orderItem : order.getOrderItems() ){
            orderItem.setOrder(order);
        }
        order.setTotalPrice(price);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        Order savedOrder=orderRepository.save(order);
        log.info("order created successfully");
        return modelMapper.map(savedOrder,OrderDto.class);

    }

    public OrderDto createOrderFallback(OrderDto orderDto,Throwable throwable){
        log.error("Fallback occurred due to :{}" , throwable.getMessage());
        return new OrderDto();
    }

    public CancelledOrder cancelOrder(OrderCancelDto orderCancelDto) {
        log.info("Cancelling Order");

        Order order = orderRepository.findById(orderCancelDto.getOrder_id())
                .orElseThrow(() ->
                    new RuntimeException("No such order with id :"+orderCancelDto.getOrder_id()+" was placed"));

        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        log.info("Calling Inventory Service");
        Double newPrice=inventoryFeignClient.addStocks(orderDto);
//        log.info("new Price:{}", newPrice);
//        if (newPrice!=0.0){
//            throw new RuntimeException("Some of the items not returned");
//        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        CancelledOrder cancelledOrder = CancelledOrder
                .builder()
                .issue(orderCancelDto.getIssue())
                .reason(orderCancelDto.getReasonForCancellation())
                .order(order)
                .build();
        return cancelOrderRepository.save(cancelledOrder);
    }

    @Transactional
    public OrderDto sendForDelivery(Order order,String address){
        log.info("Sending  Order  for delivery");

        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        log.info("Calling shipping Service");
        String status = shippingFeignClient.incomingDeliveryOrders(orderDto);
        if(status.equals("accepted")){
            order.setOrderStatus(OrderStatus.SHIPPED);
            orderDto.setOrderStatus(OrderStatus.SHIPPED);
            orderRepository.save(order);
        }
        return orderDto;
    }

    public OrderDto checkBeforeSending(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order does not exist by id:"+orderId));
        String address = "";
        return sendForDelivery(order,address);
    }
}
