package mx.itesm.second.Activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.drawee.backends.pipeline.Fresco;
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
    private String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras() ;
        token = extras.getString("token");
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        int screens = 1;
        if (PreferenceManager.getDefaultSharedPreferences(this).getInt("access", 0)  == 1) {
            screens = 3;
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), token, screens);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
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
