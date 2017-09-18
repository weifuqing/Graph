package com.wfq.graph.ui.main;

import android.widget.ListView;

import com.example.statistics.StatService;
import com.wfq.graph.R;
import com.wfq.graph.base.BaseLazyFragment;
import com.wfq.graph.base.BasicAdapter;
import com.wfq.graph.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by weifuqing on 2017/6/8 0008.
 */

public class ContactFragment extends BaseLazyFragment{


    @BindView(R.id.lv_contact)
    ListView lv_contact;

    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initView() {
        List<String> list  = new ArrayList<>();
        int i =0;
        while (i<200){
            list.add("桃子");
            i++;
        }

        lv_contact.setAdapter(new BasicAdapter<String>(getActivity(),list,R.layout.item_contact) {
            @Override
            public void onBindHolder(ViewHolder holder, String item, int position) {
                holder.setText(R.id.tv_name,item);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.startPage("联系人");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.endPage("联系人");
    }
}
