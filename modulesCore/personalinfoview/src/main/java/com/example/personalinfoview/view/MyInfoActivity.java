package com.example.personalinfoview.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.FileUtils;
import com.example.personalinfoview.R;
import com.example.personalinfoview.contract.IMyInfoContract;
import com.example.personalinfoview.databinding.ActivityInfoBinding;
import com.example.personalinfoview.presenter.MyInfoPresenter;

@Route(path = "/personalinfoview/MyInfoActivity")
public class MyInfoActivity extends AppCompatActivity implements IMyInfoContract.View {
    private static final String TAG = "MyInfoActivityTAG";

    private ActivityInfoBinding binding;
    private MyInfoPresenter mPresenter;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int READ_STORAGE_REQUEST_CODE = 2;

    @Autowired
    public User user;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInfoBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mPresenter = new MyInfoPresenter(this);

        ARouter.getInstance().inject(this);
        if (user == null) {
            Log.d("MyInfoActivity", "user: null");
        }

        if (user != null) {
            binding.tvInfoMail.setText(user.getEmail());
            binding.tvInfoUsername.setText(user.getUserName());
            String avatarUri = user.getAvatar();
            if (avatarUri != null) {
                Log.d(TAG, "有图片 " + avatarUri);
                Glide.with(this)
                        .load(avatarUri)
                        .into(binding.imgInfoPhoto);
            }else {
                Log.d(TAG, "无图片: " + avatarUri);
                Glide.with(this)
                        .load(R.drawable.default_user2)
                        .into(binding.imgInfoPhoto);
            }
        } else {
            binding.tvInfoMail.setText("未登录");
            binding.tvInfoUsername.setText("未登录");
        }

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                binding.imgInfoPhoto.setImageURI(uri);
                mPresenter.saveUserAvatar(uri);
                // 获取 ContentResolver 实例
                ContentResolver contentResolver = getApplicationContext().getContentResolver();

                // 持久化URI访问权限
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mPresenter.modifyUserAvatar(FileUtils.getRealPathFromUri(MyInfoActivity.this, uri));
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("InfoActivity1", "onClick: ");
                finish();
            }
        });

        binding.rlUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    showUpdateUsernameDialog();
                } else {
                    Toast.makeText(MyInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.rlChangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    ARouter.getInstance().build("/login/ForgetActivity")
                            .withString("email", user.getEmail())
                            .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                            .navigation();
                } else {
                    Toast.makeText(MyInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.rlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    requestPermissions();
                } else {
                    Toast.makeText(MyInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean granted = true;
                for (Boolean value : result.values()) {
                    if (!value) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    launchImagePicker();
                } else {
                    Toast.makeText(MyInfoActivity.this, "请允许权限以选择头像", Toast.LENGTH_SHORT).show();
                }
            });

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            });
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
            });
        } else {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            });
        }
    }

    private void checkPermissionResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED)) {
            launchImagePicker();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) == PackageManager.PERMISSION_GRANTED) {
            launchImagePicker();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            launchImagePicker();
        }
    }

    private void launchImagePicker() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    private void showUpdateUsernameDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_username_layout, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();
        dialog.show();

        EditText usernameEt = dialogView.findViewById(R.id.et_dialog_email);
        Button confiemBtn = dialogView.findViewById(R.id.btn_dialog_confirm);
        Button canceltBtn = dialogView.findViewById(R.id.btn_dialog_cancel);

        canceltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confiemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEt.getText().toString();
                if (username.isEmpty()) {
                    usernameEt.setError("用户名不能为空");
                } else if (username.length() > 10) {
                    usernameEt.setError("用户名长度不能大于10");
                } else {
                    mPresenter.modifyInfo(username, user.getEmail());
                    dialog.dismiss();
                }
            }
        });
    }

    public void onModifyInfoFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onModifyInfoSuccess(String username) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvInfoUsername.setText(username);
                Toast.makeText(MyInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finishAffinity();
                ARouter.getInstance().build("/main/MainActivity")
                        .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                        .navigation();
            }
        });
    }
}