package mx.itesm.second.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by rubcuadra on 3/30/17.
 */

public class Venta
{
    private int id;
    private Product product;
    private int quantity;
    private LatLng lat;
    private Date date;

    public Venta(){}
    public Venta(int id, Product product, int quantity, LatLng lat, Date date)
    {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.lat = lat;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LatLng getLat() {
        return lat;
    }

    public void setLat(LatLng lat) {
        this.lat = lat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
