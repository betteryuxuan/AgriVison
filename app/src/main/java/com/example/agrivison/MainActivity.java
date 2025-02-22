package com.example.agrivison;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/main/MainActivity")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG";
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragments;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.vp_main);
        bottomNavigationView = findViewById(R.id.bnv_main);

        Fragment fragment = (Fragment) ARouter.getInstance().build("/HomePageView/HomePageFragment").navigation(this);
        Fragment chatpageFragment = (Fragment) ARouter.getInstance().build("/chatpageview/ChatpageFragment").navigation(this);
        Fragment personalInfoFragment = (Fragment) ARouter.getInstance().build("/personalinfoview/PersonalInfoFragment").navigation(this);
        Fragment shoppingFragment = (Fragment) ARouter.getInstance().build("/shoppingview/ShoppingFragment").navigation(this);
        Fragment videoFragment = (Fragment) ARouter.getInstance().build("/videoview/VideoFragment").navigation(this);

        fragments = new ArrayList<>();
        fragments.add(fragment);
        fragments.add(videoFragment);
        fragments.add(shoppingFragment);

//        fragments.add(chatpageFragment);
        fragments.add(personalInfoFragment);

        PagesAdapter pagesAdapter = new PagesAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager2.setAdapter(pagesAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_item1) {
                    viewPager2.setCurrentItem(0);
                } else if (item.getItemId() == R.id.navigation_item2) {
                    viewPager2.setCurrentItem(1);
                } else if (item.getItemId() == R.id.navigation_item3) {
                    viewPager2.setCurrentItem(2);
                } else if (item.getItemId() == R.id.navigation_item4) {
                    viewPager2.setCurrentItem(3);
                } else if (item.getItemId() == R.id.navigation_item5) {
                    viewPager2.setCurrentItem(4);
                }
                return true;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }

}