package mx.itesm.second.Activities;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.itesm.second.Models.Product;
import mx.itesm.second.R;
import mx.itesm.second.Requester;

public class VentaActivity extends AppCompatActivity
{
    private String token;
    private Product product;

    @BindView(R.id.pName) TextView productName;
    @BindView(R.id.pDes) TextView productDescr;
    @BindView(R.id.pStock) TextView productStock;
    @BindView(R.id.pPrice) TextView productPrice;
    @BindView(R.id.pId) TextView productId;
    @BindView(R.id.cantidadEditText) EditText cantidadET;

    @BindView(R.id.productImage) SimpleDraweeView drawee_image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        product = Product.fromBundle( extras );
        token = extras.getString("token");

        //Fresco.initialize(this);
        setContentView(R.layout.activity_venta);
        ButterKnife.bind(this);
        productName.setText( product.getName() );
        productDescr.setText( product.getDescription() );
        productStock.setText( String.valueOf(product.getStock()) );
        productPrice.setText( String.valueOf(product.getPrice())  );
        productId.setText( product.getId() );
        drawee_image.setImageURI( Uri.parse( product.getImg() )  );
    }

    @OnClick(R.id.buttonComprar)
    public void venta(View view)
    {

            String url = "http://ddm.coma.mx/api/sales";

            JSONObject params = new JSONObject();
            JSONObject latlng = new JSONObject();
            JSONObject sale = new JSONObject();
            try
            {
                latlng.put("lat",19.359480);
                latlng.put("lng",-99.260089);
                params.put("product", product.getId() );
                params.put("quantity", cantidadET.getText() );
                params.put("location",latlng);
                params.put("type", PreferenceManager.getDefaultSharedPreferences(VentaActivity.this).getInt("access", 0)); //Comprador
                sale.put("sale",params);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url ,sale,new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {

                    Log.d("VENTA",response.toString());
                    Toast.makeText( VentaActivity.this ,"Venta Exitosa", Toast.LENGTH_SHORT);
                    finish();
                }
            }, new Response.ErrorListener()
            {

                @Override
                public void onErrorResponse(VolleyError error)
                {
                    VolleyLog.d("VENTA",error.getMessage());
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("token", token );
                    return headers;
                }
            };
            Requester.getInstance().addToRequestQueue(jsonObjReq);

    }
}
