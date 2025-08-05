package com.example.communityfragment.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.communityfragment.R;
import com.example.communityfragment.adapter.ImageAdapter;
import com.example.communityfragment.bean.PostPublishedEvent;
import com.example.communityfragment.contract.IPublishContract;
import com.example.communityfragment.databinding.ActivityPublishBinding;
import com.example.communityfragment.presenter.PublishPresenter;
import com.example.module.libBase.utils.SPUtils;
import com.example.module.libBase.utils.TokenManager;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Route(path = "/communityPageView/PublishActivity")
public class PublishActivity extends AppCompatActivity implements IPublishContract.View {
    public static final String TAG = "PublishFunctionTAG";

    private ActivityPublishBinding binding;
    private PublishPresenter mPresenter;

    private List<String> imageUrls = new ArrayList<>();
    private Uri cameraImageUri;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private AlertDialog.Builder builder;
    private AlertDialog dialogPick;
    private AlertDialog dialogLoading;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.publish_in, 0);

        binding = ActivityPublishBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.publish_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mPresenter = new PublishPresenter(this);

        Glide.with(this)
                .load(getAvatar())
                .error(R.drawable.default_user2)
                .into(binding.imgPublishAvatar);
        if (getLoginStatus(PublishActivity.this)) {
            binding.tvPublishUsername.setText(getUserName());
        } else {
            binding.tvPublishUsername.setText("未登录");
            Toast.makeText(PublishActivity.this, "登录后即可发帖", Toast.LENGTH_SHORT).show();
        }

        binding.imgPublishSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getLoginStatus(PublishActivity.this)) {
                    Toast.makeText(PublishActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                String content = binding.etPublishContent.getText().toString();
                if (content.trim().isEmpty()) {
                    Toast.makeText(PublishActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    binding.imgPublishSend.setEnabled(false);
                    binding.imgPublishSend.setImageResource(R.drawable.ic_publish_gray);
                    List<String> imagePaths = new ArrayList<>(imageUrls);

                    Toast.makeText(PublishActivity.this, "上传中", Toast.LENGTH_SHORT).show();
                    if (!imagePaths.isEmpty()) {
                        showPublishDialog();
                    }

                    mPresenter.publish(content, imagePaths, getCommunityId());
                }
            }
        });

        binding.imgPublishExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rlvImage.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        imageAdapter = new ImageAdapter(this, imageUrls, new ImageAdapter.OnImageActionListener() {
            @Override
            public void onAddImageClick() {
                if (imageUrls.size() < 9) {
                    showSelectImageSourceDialog();
                }
            }

            @Override
            public void onDeleteImage(int position) {
                imageUrls.remove(position);
            }
        });
        binding.rlvImage.setLayoutManager(layoutManager);
        binding.rlvImage.setAdapter(imageAdapter);


        // 注册拍照权限请求
        cameraPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                launchCamera();
            } else {
                Toast.makeText(PublishActivity.this, "请允许摄像头权限以拍照", Toast.LENGTH_SHORT).show();
            }
        });
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Log.d(TAG, "选中的图片 URI: " + uri);
                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // 配置uCrop
//                UCrop.Options options = new UCrop.Options();
//                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//                options.setCompressionQuality(50);
////                options.setToolbarColor(getResources().getColor(R.color.grenn1));
////                options.setRootViewBackgroundColor(getResources().getColor(R.color.grenn1));
//
//                // 构造裁剪后图片的保存地址
//                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
//
//                UCrop.of(uri, destinationUri)
//                        .withOptions(options)
//                        .start(PublishActivity.this);
                if (uri != null) {
                    cameraImageUri = uri;
                    imageUrls.add(uri.toString());
                    imageAdapter.notifyItemChanged(imageUrls.size() - 1);

                    if (dialogPick != null && dialogPick.isShowing()) {
                        dialogPick.dismiss();
                    }
                }
            }
        });
        // 注册拍照Launcher
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                // 裁剪
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                options.setCompressionQuality(50);
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
                UCrop.of(cameraImageUri, destinationUri)
                        .withOptions(options)
                        .start(PublishActivity.this);
            }
        });

        binding.etPublishContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1000) {
                    Toast.makeText(PublishActivity.this, "字数已达上限", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.llPublishSelsect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int communityId = 0;
                if (binding.tvPublishSelsect.getText().equals("农友杂谈")) {
                    communityId = 0;
                } else if (binding.tvPublishSelsect.getText().equals("种植交流")) {
                    communityId = 1;
                } else if (binding.tvPublishSelsect.getText().equals("农业资讯")) {
                    communityId = 2;
                }
                Log.d("BottomSelectFragmentTAG", String.valueOf(communityId));
                BottomSelectFragment bottomSelectFragment = new BottomSelectFragment(communityId);
                bottomSelectFragment.setOnOptionSelectedListener(new BottomSelectFragment.OnOptionSelectedListener() {
                    @Override
                    public void onOptionSelected(int selectedOption) {
                        switch (selectedOption) {
                            case 1:
                                binding.tvPublishSelsect.setText("种植交流");
                                break;
                            case 2:
                                binding.tvPublishSelsect.setText("农业资讯");
                                break;
                            case 0:
                            default:
                                binding.tvPublishSelsect.setText("农友杂谈");
                        }
                    }
                });
                bottomSelectFragment.show(getSupportFragmentManager(), "BottomSelectFragment");
            }
        });
    }

    private int getCommunityId() {
        String communityTag = binding.tvPublishSelsect.getText().toString();
        switch (communityTag) {
            case "农友杂谈":
                return 1;
            case "种植交流":
                return 2;
            case "农业资讯":
                return 3;
        }
        return 1;
    }

    private boolean getLoginStatus(Context context) {
        return TokenManager.getLoginStatus(PublishActivity.this);
    }

    private String getAvatar() {
        return SPUtils.getString(PublishActivity.this, SPUtils.AVATAR_KEY, "");
    }

    private String getUserName() {
        return SPUtils.getString(PublishActivity.this, SPUtils.USERNAME_KEY, "");
    }

    // 拍照裁剪的选择回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                cameraImageUri = resultUri;
                imageUrls.add(resultUri.toString());
                imageAdapter.notifyItemChanged(imageUrls.size() - 1);

                if (dialogPick != null && dialogPick.isShowing()) {
                    dialogPick.dismiss();
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            if (dialogPick != null && dialogPick.isShowing()) {
                dialogPick.dismiss();
            }
            Throwable cropError = UCrop.getError(data);
            Toast.makeText(this, "裁剪出错：图片格式不支持", Toast.LENGTH_SHORT).show();
            Log.e("MyInfoActivityTAG", "onActivityResult: " + cropError);
        }
    }

    @Override
    public void publishSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().postSticky(new PostPublishedEvent());
                Toast.makeText(PublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                binding.imgPublishSend.setEnabled(true);
                if (dialogLoading != null && dialogLoading.isShowing()) {
                    dialogLoading.dismiss();
                }
                finish();
            }
        });
    }

    @Override
    public void publishFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialogLoading != null && dialogLoading.isShowing()) {
                    dialogLoading.dismiss();
                }
                Toast.makeText(PublishActivity.this, "发布失败，请重试", Toast.LENGTH_SHORT).show();
                binding.imgPublishSend.setEnabled(true);
                binding.imgPublishSend.setImageResource(R.drawable.ic_publish);
                if (dialogLoading != null && dialogLoading.isShowing()) {
                    dialogLoading.dismiss();
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
                    Toast.makeText(PublishActivity.this, "请允许权限以选择图片", Toast.LENGTH_SHORT).show();
                }
            });

    // 选择更换图片方式
    private void showSelectImageSourceDialog() {
        builder = new AlertDialog.Builder(PublishActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pick_layout, null);
        Button btnCamera = dialogView.findViewById(R.id.btn_camera);
        Button btnGallery = dialogView.findViewById(R.id.btn_gallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PublishActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
                } else {
                    launchCamera();
                }
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });

        builder.setView(dialogView);
        dialogPick = builder.create();
        dialogPick.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
        dialogPick.show();
    }

    private void showPublishDialog() {
        builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null, false);
        builder.setView(dialogView);
        dialogLoading = builder.create();
        dialogLoading.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
        dialogLoading.setCanceledOnTouchOutside(false);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    // 打开相机
    private void launchCamera() {
        File imageFile = new File(getCacheDir(), "camera_" + System.currentTimeMillis() + ".jpg");
        cameraImageUri = androidx.core.content.FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);
        cameraLauncher.launch(cameraImageUri);
    }

    // 根据版本判断请求权限来打开相册
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

    // 从相册选择图片
    private void launchImagePicker() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
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
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.publish_out);
    }
}