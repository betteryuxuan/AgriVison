package com.example.module.shoppingview.view;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.module.shoppingview.R;
import com.example.module.shoppingview.model.ShoppingModel;
import com.example.module.shoppingview.presenter.ShoppingPresenter;


public class ShoppingActivity extends AppCompatActivity {

    private ShoppingFragment shoppingFragment;
    private ShoppingPresenter shoppingPresenter;
    private ShoppingModel shoppingModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        shoppingFragment = new ShoppingFragment();
        shoppingModel = new ShoppingModel();
        shoppingPresenter = new ShoppingPresenter(shoppingFragment, shoppingModel);
        shoppingFragment.setPresenter(shoppingPresenter);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, shoppingFragment)
                .commit();
    }
}