package com.example.settings.salesOrg;

import com.example.address.Address;

public class SalesOrg {
    private final String name;
    private String key;

    private Address address;



    public SalesOrg(String name, String key, Address address) {
        this.name = name;
        this.key = key;
    }

    @Override
    public String toString(){
        return key + " - " + name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
