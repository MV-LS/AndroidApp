package mx.itesm.second.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubcuadra on 3/31/17.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback
{
    private static final double CDMX_LAT=19.427;
    private static final double CDMX_LNG=-99.16771;
    public static final int MAP_ZOOM = 25;

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
        token = getArguments().getString("token");
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CDMX_LAT,CDMX_LNG),MAP_ZOOM-10));

        mMarkers = new ArrayList<Marker>();

        Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng( CDMX_LAT,CDMX_LNG)).title("Prueba"));
        m.setTag("Prueba");
        mMarkers.add(m);

    }

    public interface OnMapFragmentInteractionListener
    {
        void OnMapFragmentInteraction ();
    }
}
