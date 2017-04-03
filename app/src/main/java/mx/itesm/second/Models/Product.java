package mx.itesm.second.Models;

import android.os.Bundle;

/**
 * Created by rubcuadra on 3/29/17.
 */

public class Product
{
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String img;

    public Product(){}

    public Product(String id, String name, String description, double price, int stock, String img)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Bundle toBundle(Product p)
    {
        Bundle b = new Bundle();
            b.putString( "id", p.getId() );
            b.putString("name", p.getName());
            b.putString("description",p.getDescription() );
            b.putDouble("price",p.getPrice() );
            b.putInt("stock",p.getStock());
            b.putString("img",p.getImg());
        return b;
    }
    public static Product fromBundle(Bundle extras)
    {
        Product p = new Product();
            p.setId( extras.getString("id") );
            p.setName( extras.getString("name") );
            p.setDescription( extras.getString("description") );
            p.setPrice(extras.getDouble("price"));
            p.setStock( extras.getInt("stock") );
            p.setImg( extras.getString("img"));
        return p;
    }
}
