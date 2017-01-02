package com.github.bubbleview.sample.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.bubbleview.sample.R;
import com.github.bubbleview.sample.databinding.ActivityBubbleListBinding;
import com.github.bubbleview.sample.databinding.ItemBubbleListBinding;

/**
 * Created by lguipeng on 2016/9/24.
 */

public class BubbleListActivity extends AppCompatActivity{

    private ActivityBubbleListBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bubble_list);
        mBinding.list.setLayoutManager(new LinearLayoutManager(this));
        mBinding.list.setAdapter(new Adapter());
    }

    private static class Adapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemBubbleListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_bubble_list, parent, false);
            return new ViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int adapterPosition = holder.getAdapterPosition();
            final ItemBubbleListBinding binding = DataBindingUtil.getBinding(holder.itemView);
            StringBuilder sb = new StringBuilder();
            int timer = adapterPosition;
            if (timer < 14) {
                timer = 14;
            }
            for (int i=0; i<timer; i++) {
                sb.append("" + adapterPosition);
            }
            binding.text.setText(sb.toString());
            binding.bubbleLayout.setUpBubbleDrawable();
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        private static class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
