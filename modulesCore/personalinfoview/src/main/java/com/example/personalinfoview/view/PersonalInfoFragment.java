package com.example.personalinfoview.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.module.libBase.inter.Scrollable;
import com.example.module.libBase.utils.AnimationUtils;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.R;
import com.example.personalinfoview.adapter.MyVPAdapter;
import com.example.personalinfoview.contract.IInfoContract;
import com.example.personalinfoview.databinding.FragmentPersonalInfoBinding;
import com.example.personalinfoview.presenter.PersonalInfoPresenter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/personalinfoview/PersonalInfoFragment")
public class PersonalInfoFragment extends Fragment implements IInfoContract.View {
    private static final String TAG = "PersonalInfoFragmentTAG";
    private FragmentPersonalInfoBinding binding;

    private IInfoContract.Presenter presenter;
    private List<String> categories = new ArrayList<>();
    private User user;

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

        presenter.setUserInfo();

        binding.imgMyinfoAvatar.setOnClickListener(v -> {
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
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
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

        binding.tvMyinfoName.setOnClickListener(v -> {
            if (user == null) {
//                getActivity().finish();
                ARouter.getInstance().build("/login/LoginActivity")
                        .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                        .navigation();
            }
            AnimationUtils.setLikeAnimate(v);
        });

        binding.imgMyinfoSetup.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build("/personalinfoview/MyInfoActivity")
                    .withSerializable("user", getUser())
                    .navigation();
        });
        binding.imgTopSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/personalinfoview/MyInfoActivity")
                        .withSerializable("user", getUser())
                        .navigation();
            }
        });


        categories = List.of("帖子", "赞过", "收藏");
        binding.vpMyinfo.setAdapter(new MyVPAdapter(this, categories));
        binding.vpMyinfo.setOffscreenPageLimit(1);
        new TabLayoutMediator(binding.tabMyinfo, binding.vpMyinfo, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(categories.get(position));
            }
        }).attach();

        binding.ablMyinfo.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // 当偏移量的绝对值等于总滑动距离时，表示完全折叠
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    binding.imgTopUseravatar.setVisibility(View.VISIBLE);
                    binding.tvTopUseraname.setVisibility(View.VISIBLE);
                    binding.imgTopSetup.setVisibility(View.VISIBLE);
                    binding.llMyinfoCount.setVisibility(View.INVISIBLE);
                } else {
                    binding.imgTopUseravatar.setVisibility(View.INVISIBLE);
                    binding.tvTopUseraname.setVisibility(View.INVISIBLE);
                    binding.imgTopSetup.setVisibility(View.INVISIBLE);
                    binding.llMyinfoCount.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
                    .into(binding.imgMyinfoAvatar);
        } else {
            Glide.with(this)
                    .load(R.drawable.default_user2)
                    .into(binding.imgMyinfoAvatar);
        }
    }

    public void UpdateUserInfo(User user) {
        if (user != null) {
            binding.tvMyinfoName.setText(user.getUserName());
            binding.tvTopUseraname.setText(user.getUserName());
            binding.tvMyinfoEmail.setText(user.getEmail());
            binding.tvPostsCount.setText(String.format("%s\n发帖", String.valueOf(user.getPostnum())));

            String avatarUri = user.getAvatar();
            Glide.with(this)
                    .load(avatarUri)
                    .error(R.drawable.default_user2)
                    .fallback(R.drawable.default_user2)
                    .into(binding.imgMyinfoAvatar);
            Glide.with(this)
                    .load(avatarUri)
                    .error(R.drawable.default_user2)
                    .fallback(R.drawable.default_user2)
                    .into(binding.imgTopUseravatar);
        } else {
            binding.tvMyinfoName.setText("未登录");
            binding.tvTopUseraname.setText("未登录");
            binding.tvMyinfoEmail.setText("立即登录，解锁完整体验✨");
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
