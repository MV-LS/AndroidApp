package mx.itesm.second.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.itesm.second.Models.Product;
import mx.itesm.second.Requester;

/**
 * Created by rubcuadra on 3/31/17.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback
{
    private static final double CDMX_LAT=19.427;
    private static final double CDMX_LNG=-99.16771;
    public static final int MAP_ZOOM = 22;

    private OnMapFragmentInteractionListener mListener;
    private Activity CONTEXT;
    private List<Marker> mMarkers;
    private String token;
    private GoogleMap mMap;

    public MapFragment() {}
    public static MapFragment newInstance(String token)
    {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("token",token);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CONTEXT = getActivity();
        token = PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString("token","");
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnMapFragmentInteractionListener )
        {
            mListener = (OnMapFragmentInteractionListener ) context;
        } else
        {
            throw new RuntimeException(context.toString()+" must implement OnMapFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CDMX_LAT,CDMX_LNG),MAP_ZOOM-10));

        mMarkers = new ArrayList<Marker>();
        getMarkers();
    }

    public void getMarkers()
    {
        String url = "http://ddm.coma.mx/api/sales";
        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.GET,url, null ,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.d("MAPS",response.toString());
                try
                {
                    JSONArray sales = response.getJSONArray("sales");
                    JSONObject current_location;
                    for (int i = 0; i < sales.length(); i++)
                    {
                        current_location = sales.getJSONObject(i).getJSONObject("location");
                        Marker m = mMap.addMarker(new MarkerOptions().position(
                                new LatLng( current_location.getDouble("lat") ,
                                            current_location.getDouble("lng")))
                                .title( sales.getJSONObject(i).getString("date") ));
                        //m.setTag("m");
                        mMarkers.add(m);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
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
        Requester.getInstance().addToRequestQueue(rq);
    }

    public interface OnMapFragmentInteractionListener
    {
        void OnMapFragmentInteraction ();
    }
}
