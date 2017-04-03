package mx.itesm.second.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import java.util.HashMap;
import java.util.Map;
import mx.itesm.second.Models.LatLng;
import mx.itesm.second.Models.Product;
import mx.itesm.second.R;
import mx.itesm.second.Requester;
import org.json.JSONException;
import org.json.JSONObject;

public class VentaActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  private static final String TAG = "VentaActivity";
  public static final int LOCATION_PERMISSION = 23;
  private String token;
  private Product product;
  private GoogleApiClient mGoogleApiClient;
  private LatLng position;

  @BindView(R.id.pName) TextView productName;
  @BindView(R.id.pDes) TextView productDescr;
  @BindView(R.id.pStock) TextView productStock;
  @BindView(R.id.pPrice) TextView productPrice;
  @BindView(R.id.pId) TextView productId;
  @BindView(R.id.cantidadEditText) EditText cantidadET;
  @BindView(R.id.buttonBorrar) Button borrar;
  @BindView(R.id.pbar) View progressBar;

  @BindView(R.id.productImage) SimpleDraweeView drawee_image;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();
    product = Product.fromBundle(extras);
    token = extras.getString("token");

      mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();

    position = new LatLng(19.359480, -99.260089);
    //Fresco.initialize(this);
    setContentView(R.layout.activity_venta);
    ButterKnife.bind(this);
    productName.setText(product.getName());
    productDescr.setText(product.getDescription());
    productStock.setText(String.valueOf(product.getStock()));
    productPrice.setText(String.valueOf(product.getPrice()));
    productId.setText(product.getId());
    drawee_image.setImageURI(Uri.parse(product.getImg()));
  }

  @Override protected void onResume() {
    mGoogleApiClient.connect();
    super.onResume();
  }

  @Override protected void onPause() {
    mGoogleApiClient.disconnect();
    super.onPause();
  }

  @OnClick(R.id.buttonComprar) public void venta(View view) {

    String url = "http://ddm.coma.mx/api/sales";
    try {
      if (Integer.parseInt(cantidadET.getText().toString()) > product.getStock()) return;
    } catch (NumberFormatException e) {
      return;
    }
    progressBar.setVisibility(View.VISIBLE);
    JSONObject params = new JSONObject();
    JSONObject latlng = new JSONObject();
    JSONObject sale = new JSONObject();
    try {
      Log.d(TAG, "lat: " + position.getLat() + " lng: " + position.getLng());
      latlng.put("lat", position.getLat());
      latlng.put("lng", position.getLng());
      params.put("product", product.getId());
      params.put("quantity", cantidadET.getText().toString() );
      params.put("location", latlng);
      params.put("type", PreferenceManager.getDefaultSharedPreferences(VentaActivity.this)
          .getInt("access", 0)); //Comprador
      sale.put("sale", params);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    JsonObjectRequest jsonObjReq =
        new JsonObjectRequest(Request.Method.POST, url, sale, new Response.Listener<JSONObject>() {
          @Override public void onResponse(JSONObject response) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.d("VENTA", response.toString());
            Toast.makeText(VentaActivity.this, "Venta Exitosa", Toast.LENGTH_SHORT);
            finish();
          }
        }, new Response.ErrorListener() {

          @Override public void onErrorResponse(VolleyError error) {
            progressBar.setVisibility(View.INVISIBLE);
            VolleyLog.d("VENTA", error.getMessage());
          }
        }) {
          @Override public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("token", token);
            return headers;
          }
        };
    Requester.getInstance().addToRequestQueue(jsonObjReq);
  }

  @Override public void onConnected(@Nullable Bundle bundle) {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

      ActivityCompat.requestPermissions(this, new String[] {
          Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
      }, LOCATION_PERMISSION);
      return;
    }
    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    position = new LatLng(location.getLatitude(), location.getLongitude());
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == LOCATION_PERMISSION) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        mGoogleApiClient.connect();
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override public void onConnectionSuspended(int i) {
    Log.d(TAG, "onConnectionSuspended() called with: i = [" + i + "]");
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.d(TAG, "onConnectionFailed() called with: connectionResult = [" + connectionResult + "]");
  }
}
