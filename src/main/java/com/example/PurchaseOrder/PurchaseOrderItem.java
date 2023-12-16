package com.example.PurchaseOrder;

import com.example.material.Material;

public class PurchaseOrderItem {
    private int  posnr;
    private Material material;
    private float quantity;
    private float price;
    private float vat;

    public PurchaseOrderItem() {

    }

    public Integer getPosnr() {
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

    @Override
    public String toString() {
        return "Položka č. " + getPosnr() + ", materiál " + getMaterial().getDescription() + " v množství " + getQuantity() + " za cenu " + getPrice() + " DPH " + getVat();
    }
}
