package com.example.personalinfoview.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.libBase.bean.AnimationUtil;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.R;
import com.example.personalinfoview.adapter.MenuAdapter;
import com.example.personalinfoview.bean.MenuItem;
import com.example.personalinfoview.contract.IInfoContract;
import com.example.personalinfoview.presenter.PersonalInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/personalinfoview/PersonalInfoFragment")
public class PersonalInfoFragment extends Fragment implements IInfoContract.View {
    private static final String TAG = "PersonalInfoFragmentTAG";

    private IInfoContract.Presenter presenter;
    private RecyclerView rlv;
    private List<MenuItem> items = new ArrayList<>();
    private TextView tvUsername;
    private User user;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private CircleImageView imgAvatar;

    public PersonalInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PersonalInfoPresenter(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlv = view.findViewById(R.id.rlv_menuList);
        tvUsername = view.findViewById(R.id.img_info_userName);
        imgAvatar = view.findViewById(R.id.img_info_avatar);

        pickMedia = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        imgAvatar.setImageURI(uri);
                        presenter.saveUserAvatar(uri);
                        // 获取 ContentResolver 实例
                        ContentResolver contentResolver = getContext().getContentResolver();

                        // 持久化URI访问权限
                        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);


                    }
                }
        );

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.loadMenuItems();

        presenter.setUserInfo();

        imgAvatar.setOnClickListener(v -> {
            AnimationUtil.setAnimateView(v);
            if(user == null){
                Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }else {
                requestPermissions();
                checkPermissionResult();
            }
        });
        tvUsername.setOnClickListener(v -> {
            AnimationUtil.setShakeAnimateView(v);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    private ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
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
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED)) {
            launchImagePicker();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) == PackageManager.PERMISSION_GRANTED) {
            launchImagePicker();
        } else if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            launchImagePicker();
        }
    }

    private void launchImagePicker() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setUserInfo();
        String avatarUri = presenter.getUserAvatar();
        if (avatarUri != null && user != null) {
            imgAvatar.setImageURI(Uri.parse(avatarUri));
        }
    }

    @Override
    public void showMenuItems(List<MenuItem> items) {
        rlv.setAdapter(new MenuAdapter(items, position -> presenter.onMenuItemClick(position)));
    }

    public void UpdateUserName(String userName) {
        if (userName != null) {
            tvUsername.setText(userName);
        } else {
            tvUsername.setText("未登录");
        }
    }

    public User getUser() {
        return user;
    }

    @Override
    public void showUserInfo(User user) {
        this.user = user;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter = null;
    }

}
