package mx.itesm.second.Models;

/**
 * Created by rubcuadra on 3/29/17.
 */

public class Product
{
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String img;

    public Product(){}

    public Product(int id, String name, String description, double price, int stock, String img)
    {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
