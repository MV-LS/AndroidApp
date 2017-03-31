package mx.itesm.second.Activities;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.itesm.second.Adapter.SectionsPagerAdapter;

import mx.itesm.second.Fragments.MapFragment;
import mx.itesm.second.Fragments.ProductsFragment;
import mx.itesm.second.Fragments.ReportsFragment;
import mx.itesm.second.Models.Product;
import mx.itesm.second.R;

public class MainActivity extends AppCompatActivity implements ProductsFragment.OnProductsListFragmentInteractionListener, ReportsFragment.OnReportsFragmentInteractionListener,MapFragment.OnMapFragmentInteractionListener
{
    @BindView(R.id.toolbar) Toolbar toolbar;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnProductsListFragmentInteraction(Product c)
    {
        //Fragmento de lista quiere decir algo
    }

    @Override
    public void onReportsFragmentInteraction()
    {
        //Fragmento de Reportes quiere decir algo
    }

    @Override
    public void OnMapFragmentInteraction() {

    }
}
