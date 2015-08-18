package me.anhvannguyen.android.addressbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by anhvannguyen on 8/18/15.
 */
public class AddressPagerActivity extends ActionBarActivity {
    private ViewPager mAddressViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_pager);

        mAddressViewPager = (ViewPager) findViewById(R.id.activity_address_detail_pager);
        mAddressViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(DetailActivityFragment.ADDRESS_DETAIL_URI, getIntent().getData());

                DetailActivityFragment fragment = new DetailActivityFragment();
                fragment.setArguments(arguments);
                
                return fragment;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });


    }
}
