package com.Bcsapat._ib153l14b.EliteFoods.service;

import com.Bcsapat._ib153l14b.EliteFoods.model.Kosar;
import com.Bcsapat._ib153l14b.EliteFoods.model.Rendeles;
import com.Bcsapat._ib153l14b.EliteFoods.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private List<Kosar> cartItems;
    private OrderRepository orderRepository;

    public CartService(OrderRepository orderRepository) {
        this.cartItems = new ArrayList<>();
        this.orderRepository = orderRepository;
    }

    public Rendeles addOrderToRepo(Rendeles rendeles) {
        return orderRepository.save(rendeles);
    }


    public void addItem(Kosar item) {
        if (item.getMennyiseg() <= 0) {
            throw new IllegalArgumentException("A mennyiség nem lehet 0 vagy negatív!");
        }
        for (Kosar cartItem : cartItems) {
            if (cartItem.getAzonosito().equals(item.getAzonosito())) {
                cartItem.setMennyiseg(Math.max(cartItem.getMennyiseg(), item.getMennyiseg()));
                return;
            }
        }
        cartItems.add(item);
    }

    public void removeItem(Long productId) {
        cartItems.removeIf(cartItem -> cartItem.getAzonosito().equals(productId));
    }


    public List<Kosar> getCartItems() {
        return cartItems;
    }


    public int getTotalPrice() {
        return cartItems.stream()
                .mapToInt(item -> (int) (item.getAr() * item.getMennyiseg()))
                .sum();
    }

    public int getCartItemCount() {
        return cartItems.stream()
                .mapToInt(Kosar::getMennyiseg)
                .sum();
    }

    public void clearCart() {
        if (cartItems != null) {
            cartItems.clear();
        }
    }
}