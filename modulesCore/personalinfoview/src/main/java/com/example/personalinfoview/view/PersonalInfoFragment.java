package com.example.personalinfoview.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.personalinfoview.contract.IInfoContract;
import com.example.personalinfoview.adapter.MenuAdapter;
import com.example.personalinfoview.R;
import com.example.personalinfoview.bean.MenuItem;
import com.example.personalinfoview.presenter.PersonalInfoPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/personalinfoview/PersonalInfoFragment")
public class PersonalInfoFragment extends Fragment implements IInfoContract.View {

    private IInfoContract.Presenter presenter;
    private RecyclerView rlv;
    private List<MenuItem> items = new ArrayList<>();

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

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.loadMenuItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    @Override
    public void showMenuItems(List<MenuItem> items) {
        rlv.setAdapter(new MenuAdapter(items, new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.onMenuItemClick(position);
            }
        }));
    }

}