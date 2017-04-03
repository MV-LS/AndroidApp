package mx.itesm.second.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.itesm.second.R;
import mx.itesm.second.Requester;

public class LoginActivity extends AppCompatActivity
{
    @BindView(R.id.editText2) EditText et_password;
    @BindView(R.id.editText10) EditText et_email;

    private static final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String TAG = "LOGINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button3)
    public void login(View view)
    {
        if (et_email.getText().toString().matches(emailRegex) && et_password.getText().toString().length()!=0)
        {
            String url = "http://ddm.coma.mx/api/authenticate";

            JSONObject params = new JSONObject();
            try
            {
                params.put("email", et_email.getText().toString() );
                params.put("password", et_password.getText().toString() );
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url ,params,new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    Log.d(TAG,response.toString());
                    try
                    {
                        Intent intent = new Intent( LoginActivity.this, MainActivity.class);
                        intent.putExtra("token", response.getString("token"));
                        startActivity(intent);
                        finish();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {

                @Override
                public void onErrorResponse(VolleyError error)
                {
                    VolleyLog.d(TAG,error.getMessage());
                }
            });

            Requester.getInstance().addToRequestQueue(jsonObjReq);
        }
        else
            Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show();
    }
}
