package mx.itesm.second.Adapter;

/**
 * Created by rubcuadra on 3/29/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mx.itesm.second.Fragments.MapFragment;
import mx.itesm.second.Fragments.PlaceHolderFragment;
import mx.itesm.second.Fragments.ProductsFragment;
import mx.itesm.second.Fragments.ReportsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return ProductsFragment.newInstance();
            case 1:
                return ReportsFragment.newInstance();
            default:
                return MapFragment.newInstance();
        }
    }

    @Override
    public int getCount()
    {
        return 3; // Show 3 total pages.
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "Lista";
            case 1:
                return "Reportes";
            case 2:
                return "Algo Mas";
        }
        return null;
    }
}