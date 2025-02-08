package com.Bcsapat._ib153l14b.EliteFoods.service;

import com.Bcsapat._ib153l14b.EliteFoods.model.Rendeles;
import com.Bcsapat._ib153l14b.EliteFoods.model.Termek;
import com.Bcsapat._ib153l14b.EliteFoods.repository.OrderRepository;
import com.Bcsapat._ib153l14b.EliteFoods.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Rendeles getRendelesById(Long azonsoito){
        return orderRepository.getRendelesByAzonosito(azonsoito);
    }

    public List<Termek> getTermekekByRendeles(Rendeles rendeles){
        return productRepository.getTermekByRendeles(rendeles);
    }
}
