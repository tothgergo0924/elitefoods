package com.Bcsapat._ib153l14b.EliteFoods.model;

import lombok.Getter;

import java.util.List;

@Getter
public class PurchaseRequest {
    private List<Kosar> products;
    private String username;
    private int price;

}
