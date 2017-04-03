package mx.itesm.second.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mx.itesm.second.Adapter.ProductAdapter;
import mx.itesm.second.Models.Product;
import mx.itesm.second.R;
import mx.itesm.second.Requester;

/**
 * Created by rubcuadra on 3/9/17.
 */

public class ProductsFragment extends Fragment
{
    private OnProductsListFragmentInteractionListener mListener;
    private Activity CONTEXT;
    private ProductAdapter mProdAdapter;
    private Unbinder unbinder;
    private static final String TAG = "ProductsFragment";
    private String token;
    @BindView(R.id.recycler_products) RecyclerView mRecyclerView;

    public ProductsFragment() {}
    public static ProductsFragment newInstance(String token)
    {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString("token", token);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CONTEXT = getActivity();
    }

    @Override public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        token = getArguments().getString("token");
        unbinder = ButterKnife.bind(this, view);
        mProdAdapter = new ProductAdapter(CONTEXT,new ArrayList<Product>());
        mRecyclerView.setLayoutManager(new GridLayoutManager(CONTEXT,2));
        mRecyclerView.setAdapter( mProdAdapter );
        loadCards();
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnProductsListFragmentInteractionListener )
        {
            mListener = (OnProductsListFragmentInteractionListener ) context;
        } else
        {
            throw new RuntimeException(context.toString()+" must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    void loadCards()
    {
        String url = "http://ddm.coma.mx/api/products";
        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.GET,url, null ,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    Log.d(TAG,response.toString());

                    List<Product> productos_obtenidos = new ArrayList<>();
                    Product currentProduct;
                    JSONObject currentObj;

                    JSONArray products = response.getJSONArray("products");
                    for (int i = 0; i < products.length() ; i++)
                    {
                        currentObj = products.getJSONObject(i);
                        currentProduct = new Product(
                                currentObj.getString("_id"),
                                currentObj.getString("name"),
                                currentObj.getString("description"),
                                currentObj.getDouble("price"),
                                currentObj.getInt("stock"),
                                currentObj.getString("img")
                        );
                        productos_obtenidos.add(currentProduct);
                    }
                    mProdAdapter.addProducts( productos_obtenidos ); //Updatear adapter

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProductsListFragmentInteractionListener
    {
        void OnProductsListFragmentInteraction (Product c);
    }
}
