package com.example.personalinfoview.view;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.module.libBase.AnimationUtils;
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
    private TextView tvEmail;
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
        tvUsername = view.findViewById(R.id.tv_myinfo_name);
        tvEmail = view.findViewById(R.id.tv_myinfo_email);
        imgAvatar = view.findViewById(R.id.img_myinfo_avatar);

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.loadMenuItems();

        presenter.setUserInfo();

        imgAvatar.setOnClickListener(v -> {
//            AnimationUtils.setAnimateView(v);
            if (user == null) {
                ARouter.getInstance().build("/login/LoginActivity")
                        .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                        .navigation();
                return;
            }

            String imageUri = presenter.getUserAvatar();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_image_viewer, null);
            ImageView photoView = dialogView.findViewById(R.id.photo_view_dialog);
            Button changeBtn = dialogView.findViewById(R.id.btn_change);
            changeBtn.setVisibility(View.GONE);
            Glide.with(getContext())
                    .load(imageUri)
                    .error(R.drawable.default_user2)
                    .into(photoView);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();


            dialog.show();

            // 调整 Dialog 的宽高和位置
            if (dialog.getWindow() != null) {
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.getWindow().setWindowAnimations(0);
                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
                dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            dialogView.setOnClickListener(v1 -> dialog.dismiss());
        });

        tvUsername.setOnClickListener(v -> {
            if (user == null) {
//                getActivity().finish();
                ARouter.getInstance().build("/login/LoginActivity")
                        .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                        .navigation();
            }
            AnimationUtils.setLikeAnimate(v);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setUserInfo();
        String avatarUri = presenter.getUserAvatar();
        if (avatarUri != null && user != null) {
            Glide.with(this)
                    .load(avatarUri)
                    .error(R.drawable.default_user2)
                    .fallback(R.drawable.default_user2)
                    .into(imgAvatar);
        } else {
            Glide.with(this)
                    .load(R.drawable.default_user2)
                    .into(imgAvatar);
        }
    }

    @Override
    public void showMenuItems(List<MenuItem> items) {
        rlv.setAdapter(new MenuAdapter(items, position -> presenter.onMenuItemClick(position)));
    }

    public void UpdateUserInfo(User user) {
        if (user != null) {
            tvUsername.setText(user.getUserName());
            tvEmail.setText(user.getEmail());

            String avatarUri = user.getAvatar();
            if (avatarUri != null) {
                Log.d(TAG, "有图片 " + avatarUri);
                Glide.with(this)
                        .load(avatarUri)
                        .error(R.drawable.default_user2)
                        .fallback(R.drawable.default_user2)
                        .into(imgAvatar);
            } else {
                Log.d(TAG, "无图片: " + avatarUri);
                Glide.with(this)
                        .load(R.drawable.default_user2)
                        .into(imgAvatar);
            }
        } else {
            tvUsername.setText("未登录");
            tvEmail.setText("");
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
