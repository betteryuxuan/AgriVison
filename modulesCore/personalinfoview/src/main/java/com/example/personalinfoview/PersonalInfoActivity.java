package com.example.personalinfoview;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.personalinfoview.view.PersonalInfoFragment;

public class PersonalInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personalInfoPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        PersonalInfoFragment infoFragment = (PersonalInfoFragment) ARouter.getInstance().build("/personalinfoview/PersonalInfoFragment").navigation();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, infoFragment)
                .commit();

    }
}