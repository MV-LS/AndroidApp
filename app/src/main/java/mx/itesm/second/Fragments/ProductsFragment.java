package mx.itesm.second.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mx.itesm.second.Adapter.ProductAdapter;
import mx.itesm.second.Models.Product;
import mx.itesm.second.R;

/**
 * Created by rubcuadra on 3/9/17.
 */

public class ProductsFragment extends Fragment
{
    private OnProductsListFragmentInteractionListener mListener;
    private Activity CONTEXT;
    private ProductAdapter mProdAdapter;
    private Unbinder unbinder;
    @BindView(R.id.recycler_products) RecyclerView mRecyclerView;

    public ProductsFragment() {}
    public static ProductsFragment newInstance()
    {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        //fragment.setArguments(args);
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
        List<Product> productos_obtenidos = new ArrayList<>();
        Product current;
        //TODO QUITAR DUMMY DATA
        for (int i = 0; i < 10; i++)
        {
            current = new Product( i,
                                   String.format("Test%s",i),
                                   String.format("Descripcion%s",i),
                                    i*10000,
                                    i,
                                    "http://ubiquitous.csf.itesm.mx/~pddm-1019102/content/parcial1/tareas/8/expedition.jpg");
            productos_obtenidos.add(current);
        }
        mProdAdapter.addProducts( productos_obtenidos );
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
