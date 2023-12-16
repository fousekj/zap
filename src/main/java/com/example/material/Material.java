package com.example.material;

public class Material {
    private int id;
    private String description;
    private float qtyAvailable;
    private float price;
    private String baseUOM;
    private float vatRate;
    private float bruttoWeight;
    private float nettoWeight;

    public Material(){

    }

    public Material( String description, float price, String baseUOM, float vatRate, float bruttoWeight, float nettoWeight) {
        this.description = description;
        this.price = price;
        this.baseUOM = baseUOM;
        this.vatRate = vatRate;
        this.bruttoWeight = bruttoWeight;
        this.nettoWeight = nettoWeight;
    }

    @Override
    public String toString() {
        return "Materiál číslo " + id + " s popisem " + description + " a cenou " + price + " Kč. Momentálně je skladem " + qtyAvailable + " kusů.";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(float qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBaseUOM() {
        return baseUOM;
    }

    public void setBaseUOM(String baseUOM) {
        this.baseUOM = baseUOM;
    }

    public float getVatRate() {
        return vatRate;
    }

    public void setVatRate(float vatRate) {
        this.vatRate = vatRate;
    }

    public float getBruttoWeight() {
        return bruttoWeight;
    }

    public void setBruttoWeight(float bruttoWeight) {
        this.bruttoWeight = bruttoWeight;
    }

    public float getNettoWeight() {
        return nettoWeight;
    }

    public void setNettoWeight(float nettoWeight) {
        this.nettoWeight = nettoWeight;
    }
}