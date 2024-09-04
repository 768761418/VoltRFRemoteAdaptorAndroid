package com.lin.voltrfremoteadaptorandroid.Activity.homeFgm.zone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonAdapter;
import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonViewHolder;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.FragmentZoneBinding;
import com.lin.voltrfremoteadaptorandroid.db.ZoneDb;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZoneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentZoneBinding fragmentZoneBinding;
    private CommonAdapter<ZoneDb> zoneDbCommonAdapter;
    private List<ZoneDb> zoneDbs;
    private String TAG = "ZoneFragment";

    public ZoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZoneFragment newInstance(String param1, String param2) {
        ZoneFragment fragment = new ZoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentZoneBinding = fragmentZoneBinding.inflate(inflater,container,false);
        initData();
        initUi();
        // Inflate the layout for this fragment
        return fragmentZoneBinding.getRoot();
    }


    private void initData(){
        zoneDbs = ZoneDb.getAllZones();
        Log.d(TAG, "initData: " + zoneDbs);
    }

    private void initUi(){
        zoneDbCommonAdapter = new CommonAdapter<ZoneDb>(getContext(),zoneDbs,R.layout.item_zone) {
            @Override
            public void bindData(CommonViewHolder holder, ZoneDb data, int position) {
                holder.setText(R.id.zone_item_name, data.getZoneName());
                holder.setViewImageRes(R.id.zone_item_logo,R.drawable.icon_zone_item);

                holder.setCommonClickListener(new CommonViewHolder.OnCommonItemEventListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), ZoneDetailActivity.class);
                        intent.putExtra("zoneName",data.getZoneName());
                        intent.putExtra("zoneId",data.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(int viewId, int position) {

                    }
                });



            }
        };
        fragmentZoneBinding.zoneList.setAdapter(zoneDbCommonAdapter);

//        添加的点击事件
        fragmentZoneBinding.zoneBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                获取长度判断默认名字
                int current = zoneDbs.size()+1;
                String newZoneName = "zone" + current;
//                保存到数据库
                ZoneDb zoneDb = new ZoneDb(newZoneName);
                zoneDb.save();
//                添加到数据列表中，刷新数据
                zoneDbs.add(zoneDb);
                zoneDbCommonAdapter.notifyDataSetChanged();
            }
        });

    }
}