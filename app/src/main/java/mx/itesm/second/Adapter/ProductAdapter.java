package mx.itesm.second.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.itesm.second.Models.Product;
import mx.itesm.second.R;

/**
 * Created by rubcuadra on 3/9/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    private List<Product> products;
    private Activity activity;

    public ProductAdapter(Activity activity, List<Product> ps)
    {
        this.products = ps;
        this.activity = activity;
    }

    public void addProducts(List<Product> cs)
    {
        this.products.addAll(cs);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final Product current = products.get(position);
        holder.title.setText( current.getName());
        holder.description.setText(current.getDescription());
        holder.img.setImageURI( Uri.parse( current.getImg() ) );
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*
                TODO :: Pasar a otra actividad al darleClick para seguir proceso de compra
                Intent intent = new Intent(activity,CardDetailActivity.class);
                intent.putExtras( Product.asBundle(current) );
                activity.startActivity(intent);
                */
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return products.size();
    }

    /**
     * View holder to display each RecylerView item
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.product_name) TextView title;
        @BindView(R.id.product_description) TextView description;
        @BindView(R.id.product_image) SimpleDraweeView img;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
