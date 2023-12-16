package com.example.interfaces;

import com.example.material.Material;

public class DocumentItem {
    private int posnr;
    private Material material;
    private float quantity;
    private float price;
    private float vat;

    public DocumentItem(int posnr, Material material, float quantity, float price, float vat) {
        this.posnr = posnr;
        this.material = material;
        this.quantity = quantity;
        this.price = price;
        this.vat = vat;
    }

    public DocumentItem() {

    }

    public int getPosnr() {
        return posnr;
    }

    public void setPosnr(int posnr) {
        this.posnr = posnr;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getVat() {
        return vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }
}
