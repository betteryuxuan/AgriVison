package com.example.agrivison;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.bean.SwitchPageEvent;
import com.example.module.libBase.inter.Scrollable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private ImageView add;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager2 = findViewById(R.id.vp_main);
        bottomNavigationView = findViewById(R.id.bnv_main);
        add = findViewById(R.id.iv_main_add);

        bottomNavigationView.setItemIconTintList(null);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Fragment fragment = (Fragment) ARouter.getInstance().build("/HomePageView/HomePageFragment").navigation(this);
        Fragment personalInfoFragment = (Fragment) ARouter.getInstance().build("/personalinfoview/PersonalInfoFragment").navigation(this);
        Fragment videoFragment = (Fragment) ARouter.getInstance().build("/videoview/VideoFragment").navigation(this);
        Fragment communityFragment = (Fragment) ARouter.getInstance().build("/communityPageView/CommunityFragment").navigation(this);

        fragments = new ArrayList<>();
        fragments.add(fragment);
        fragments.add(videoFragment);
        fragments.add(new Fragment());
        fragments.add(communityFragment);
        fragments.add(personalInfoFragment);

        PagesAdapter pagesAdapter = new PagesAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager2.setAdapter(pagesAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_item1) {
                    viewPager2.setCurrentItem(0, false);
                    if (viewPager2.getCurrentItem() == 0) {
                        scrollToTop(0);
                    }
                } else if (item.getItemId() == R.id.navigation_item2) {
                    viewPager2.setCurrentItem(1, false);
                } else if (item.getItemId() == R.id.navigation_item3) {
                    viewPager2.setCurrentItem(2, false);
                } else if (item.getItemId() == R.id.navigation_item4) {
                    viewPager2.setCurrentItem(3, false);
                    if (viewPager2.getCurrentItem() == 3) {
                        scrollToTop(3);
                    }
                } else if (item.getItemId() == R.id.navigation_item5) {
                    viewPager2.setCurrentItem(4, false);
                }
                return true;
            }
        });


        bottomNavigationView.setOnLongClickListener(v -> true);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                viewPager2.setUserInputEnabled(false);
            }
        });

        viewPager2.setOffscreenPageLimit(5);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2); // 索引 2 表示第三个菜单项
        menuItem.setEnabled(false);
        menuItem.setIcon(android.R.color.transparent);
        menuItem.setTitle("");
        // 适配底部小白条颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white_gray));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/communityPageView/PublishActivity")
                        .navigation();
            }

        });


    }

    private void scrollToTop(int position) {
        Fragment fragment = fragments.get(position);
        if (fragment instanceof Scrollable) {
            ((Scrollable) fragment).scrollToTop();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && viewPager2.getCurrentItem() == 0) {
            exitByTwoClick();      //调用双击退出函数
        } else if (keyCode == KeyEvent.KEYCODE_BACK && viewPager2.getCurrentItem() != 0) {
            viewPager2.setCurrentItem(0);
        }
        return false;
    }

    private void exitByTwoClick() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchPageEvent(SwitchPageEvent event) {
        viewPager2.setCurrentItem(event.getPageIndex(), true);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            // 判断当前焦点是否为EditText
            if (v instanceof EditText) {
                // 将outRect设置为视图根视图坐标空间中该视图的非剪裁区域的坐标,即 EditText 在屏幕上的可见区域
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                // 触摸点不在 EditText 的可见区域
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}