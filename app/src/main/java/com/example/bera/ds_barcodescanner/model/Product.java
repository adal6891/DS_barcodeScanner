package com.example.bera.ds_barcodescanner.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by bera on 2015-05-31.
 */
@XStreamAlias("product")
public class Product {
    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    @XStreamAlias("barcode")
    private String barcode;
    @XStreamAlias("name")
    private String name;

    public Product(String barcode, String name) {
        this.barcode = barcode;
        this.name = name;
    }
}
