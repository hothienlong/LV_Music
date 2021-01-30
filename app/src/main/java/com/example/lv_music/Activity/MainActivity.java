package com.example.lv_music.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lv_music.Adapter.MainViewPagerAdapter;
import com.example.lv_music.Fragment.AccountFragment;
import com.example.lv_music.Fragment.HomeFragment;
import com.example.lv_music.Fragment.ListSongPageFragment;
import com.example.lv_music.Fragment.PlaylistFragment;
import com.example.lv_music.Fragment.ListSongFragment;
import com.example.lv_music.Fragment.TopListFragment;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        init();


    }

    private void init() {
        // viewpager & tab layout
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new ListSongPageFragment(), "Song");
        adapter.addFragment(new PlaylistFragment(), "Play list");
        adapter.addFragment(new TopListFragment(), "Top list");
//        adapter.addFragment(new AccountFragment(), "Information");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.song);
        tabLayout.getTabAt(2).setIcon(R.drawable.playlist);
        tabLayout.getTabAt(3).setIcon(R.drawable.top);
//        tabLayout.getTabAt(4).setIcon(R.drawable.account);

        // side bar
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // set item as selected to persist highlight
                            menuItem.setChecked(true);
                            // close drawer when item is tapped
                            mDrawerLayout.closeDrawers();

                            // Add code here to update the UI based on the item selected
                            // For example, swap UI fragments here
                            if(menuItem.getItemId()==R.id.nav_account){
                                Intent intent = new Intent(MainActivity.this,AccountActivity.class);
                                startActivity(intent);
                            }

                            return true;
                        }
                });

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true); // icon
        actionbar.setHomeAsUpIndicator(R.drawable.ic_navigation);
    }

    // xử lí sự kiện khi bấm nút navigation => mở side bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }
}