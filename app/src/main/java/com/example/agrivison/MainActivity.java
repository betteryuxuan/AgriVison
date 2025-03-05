package com.example.agrivison;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Route(path = "/main/MainActivity")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG";
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragments;
    private ViewPager2 viewPager2;
    private boolean isExit = false;
    private static final long TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager2 = findViewById(R.id.vp_main);
        bottomNavigationView = findViewById(R.id.bnv_main);

        bottomNavigationView.setItemIconTintList(null);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Fragment fragment = (Fragment) ARouter.getInstance().build("/HomePageView/HomePageFragment").navigation(this);
        Fragment chatpageFragment = (Fragment) ARouter.getInstance().build("/chatpageview/chatPage").navigation(this);
        Fragment personalInfoFragment = (Fragment) ARouter.getInstance().build("/personalinfoview/PersonalInfoFragment").navigation(this);
        Fragment shoppingFragment = (Fragment) ARouter.getInstance().build("/shoppingview/ShoppingFragment").navigation(this);
        Fragment videoFragment = (Fragment) ARouter.getInstance().build("/videoview/VideoFragment").navigation(this);

        fragments = new ArrayList<>();
        fragments.add(fragment);
        fragments.add(chatpageFragment);
        fragments.add(videoFragment);
        fragments.add(shoppingFragment);
        fragments.add(personalInfoFragment);

        PagesAdapter pagesAdapter = new PagesAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager2.setAdapter(pagesAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_item1) {
                    viewPager2.setCurrentItem(0, false);
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
                viewPager2.setUserInputEnabled(position != 0);
            }
        });

        viewPager2.setOffscreenPageLimit(5);

        @SuppressLint("RestrictedApi")
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            View item = menuView.getChildAt(i);
            item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        }

        // 适配底部小白条颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white_gray));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByTwoClick();      //调用双击退出函数
        }
        return false;
    }

    private void exitByTwoClick() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, TIME);
        } else {
            System.exit(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


}