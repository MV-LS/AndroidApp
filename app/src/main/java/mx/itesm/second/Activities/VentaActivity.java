package mx.itesm.second.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.itesm.second.Models.Product;
import mx.itesm.second.R;

public class VentaActivity extends AppCompatActivity
{
    private String token;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        product = Product.fromBundle( extras );
        token = extras.getString("token");
        setContentView(R.layout.activity_venta);
    }
}
