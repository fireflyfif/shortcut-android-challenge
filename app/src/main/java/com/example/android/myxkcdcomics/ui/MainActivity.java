package com.example.android.myxkcdcomics.ui;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.android.myxkcdcomics.R;
import com.example.android.myxkcdcomics.ui.comicsfragment.ComicsFragment;
import com.example.android.myxkcdcomics.ui.favfragment.FavFragment;
import com.example.android.myxkcdcomics.ui.searchfragment.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.appbar_main)
    AppBarLayout appBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.content_view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private BottomNavAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager();
        setupBottomNavStyle();
        addBottomNavItems();

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position);

                return true;
            }
        });
    }

    private void setupViewPager() {
        pagerAdapter = new BottomNavAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new ComicsFragment());
        pagerAdapter.addFragments(new SearchFragment());
        pagerAdapter.addFragments(new FavFragment());

        viewPager.setAdapter(pagerAdapter);
    }

    private void setupBottomNavStyle() {
        bottomNavigation.setDefaultBackgroundColor(
                getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setAccentColor(
                getResources().getColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(
                getResources().getColor(R.color.colorBackground));
        bottomNavigation.setColoredModeColors(
                (getResources().getColor(R.color.colorBottomNavigationInactive)),
                (getResources().getColor(R.color.colorAccent)));

        bottomNavigation.setColored(false);
    }

    private void addBottomNavItems() {
        AHBottomNavigationItem comicsItem = new AHBottomNavigationItem(
                "Comics", R.drawable.ic_number_sign);
        AHBottomNavigationItem searchItem = new AHBottomNavigationItem(
                "Search", R.drawable.ic_search_black_24dp);
        AHBottomNavigationItem favItem = new AHBottomNavigationItem(
                "Favs", R.drawable.ic_favorite_black_24dp);

        bottomNavigation.addItem(comicsItem);
        bottomNavigation.addItem(searchItem);
        bottomNavigation.addItem(favItem);
    }
}
