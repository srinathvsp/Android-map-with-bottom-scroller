package com.centurylink.ncp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.centurylink.ncp.Fragment.ImageFragment;
import com.viewpagerindicator.CirclePageIndicator;

public class CircularfrgmentActivity extends AppCompatActivity {
    private FragmentPagerAdapter fragmentAdapter;
    private ViewPager viewPager;
    private CirclePageIndicator circlePageIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circularfrgment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.circlePageIndicator);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(fragmentAdapter);
        circlePageIndicator.setViewPager(viewPager);

    }


    /*Adapter*/
    class FragmentAdapter extends FragmentPagerAdapter {

        int[] id_mensajes = {R.string.image_text_uno,R.string.image_text_dos,R.string.image_text_tres};
        int[] id_images = {R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp};

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(id_images[position], id_mensajes[position]);
        }

        @Override
        public int getCount() {
            return id_images.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return id_images + "";
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }
}
