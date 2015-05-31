package com.example.bera.ds_barcodescanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bera on 2015-05-31.
 */
public class Item {
    private List products = new ArrayList();

    public void add(Product product){
        products.add(product);
    }

    public List getProducts() {
        return products;
    }
}
