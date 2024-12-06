package com.moreira.dscommerce.services;

import com.moreira.dscommerce.dto.OrderDTO;
import com.moreira.dscommerce.entities.Order;
import com.moreira.dscommerce.repositories.OrderRepository;
import com.moreira.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")
        );
        return new OrderDTO(order);
    }
}
