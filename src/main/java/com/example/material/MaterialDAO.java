package com.example.material;

import com.example.DB.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Locale;

public class MaterialDAO {

    public static ObservableList<Material> getAllMaterials(){

        String query = """
                select material_id, description, qty_available, unit_of_measure, price, vat_rate, netto_weight, brutto_weight
                from Material""";

        try {
            ResultSet rsMaterials = DB.dbExecuteSelect(query);
            return getMaterialList(rsMaterials);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static ObservableList<Material> getMaterialList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Material> materials = FXCollections.observableArrayList();

        while (rs.next()) {
            Material material = new Material();
            material.setId(rs.getInt(1));
            material.setDescription(rs.getString(2));
            material.setQtyAvailable(rs.getFloat(3));
            material.setBaseUOM(rs.getString(4));
            material.setPrice(rs.getFloat(5));
            material.setVatRate(rs.getFloat(6));
            material.setNettoWeight(rs.getFloat(7));
            material.setBruttoWeight(rs.getFloat(8));

            materials.add(material);
        }
        return materials;
    }

    public static Material getMaterialFromId(int id){

        String query = String.format(Locale.US,"""
                select material_id, description, qty_available, unit_of_measure, price, vat_rate, netto_weight, brutto_weight
                from Material
                where material_id = %d""", id);

        try {
            ResultSet rsMaterial = DB.dbExecuteSelect(query);
            return getMaterial(rsMaterial);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static Material getMaterial (ResultSet rs) throws SQLException, ClassNotFoundException {
        Material material = new Material();

        if (rs.next()) {
            material.setId(rs.getInt(1));
            material.setDescription(rs.getString(2));
            material.setQtyAvailable(rs.getFloat(3));
            material.setBaseUOM(rs.getString(4));
            material.setPrice(rs.getFloat(5));
            material.setVatRate(rs.getFloat(6));
            material.setNettoWeight(rs.getFloat(7));
            material.setBruttoWeight(rs.getFloat(8));
        }
        return material;
    }


    public static int insertMaterial(Material material){

        String query = String.format(Locale.US,
                "insert into Material (description, qty_available, unit_of_measure, price, vat_rate, netto_weight, brutto_weight, created_at, updated_at)\n" +
                        "values ('%s', 0, '%s', %.2f, %.2f, %.2f, %.2f, GETDATE(), GETDATE())", material.getDescription(),
                material.getBaseUOM(), material.getPrice(), material.getVatRate(), material.getNettoWeight(),
                material.getBruttoWeight());

        try {
            ResultSet resultSet = DB.dbExecuteInsert(query);
            if (resultSet.next()){
                return resultSet.getInt(1);
            } else {
                throw new SQLException();
            }
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
            return 0;
        }

    }

    public void updateMaterialStock(Material material) {

    }
}
