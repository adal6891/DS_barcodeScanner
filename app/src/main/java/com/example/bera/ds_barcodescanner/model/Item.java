package com.example.bera.ds_barcodescanner.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bera on 2015-05-31.
 */
@XStreamAlias("item")
public class Item {
    @XStreamImplicit(itemFieldName = "products")
    private List products = new ArrayList();

    public void add(Product product){
        products.add(product);
    }

    public List getProducts() {
        return products;
    }
}
