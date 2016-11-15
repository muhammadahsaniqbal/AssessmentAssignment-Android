package com.example.ahsaniqbal.contactlist.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ahsaniqbal.contactlist.Listeners.DeleteClickListener;
import com.example.ahsaniqbal.contactlist.Listeners.EditClickListener;
import com.example.ahsaniqbal.contactlist.Listeners.OnRecyclerItemClickListener;
import com.example.ahsaniqbal.contactlist.Models.AIContact;
import com.example.ahsaniqbal.contactlist.R;

import java.util.List;

import static com.example.ahsaniqbal.contactlist.R.id.btnEdit;

/**
 * Created by ahsaniqbal on 11/15/16.
 */

public class AIContactListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    protected OnRecyclerItemClickListener onRecyclerItemClickListener;
    protected EditClickListener editClickListener;
    protected DeleteClickListener deleteClickListener;


    protected RecyclerView recyclerView;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void setEditClickListener(EditClickListener editClickListener) {
        this.editClickListener = editClickListener;
    }

    public void setDeleteClickListener(DeleteClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public void onClick(final View view) {
        int itemPosition = this.recyclerView.getChildLayoutPosition(view);

        if (onRecyclerItemClickListener != null) {
            onRecyclerItemClickListener.onItemClick(view, itemPosition);
        }

    }

    private List<AIContact> aiContactList;

    public AIContactListAdapter(List<AIContact> aiContactList, RecyclerView recyclerView) {
        this.aiContactList = aiContactList;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ai_contact_list_adapter, parent, false);
        itemView.setOnClickListener(this);
        return new AIContactListAdapter.AIContactListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AIContactListViewHolder viewHolder = (AIContactListViewHolder)holder;
        AIContact contact = aiContactList.get(position);

        viewHolder.tvNameValue.setText(contact.name);
        viewHolder.tvEmailValue.setText(contact.email);
        viewHolder.tvPhoneValue.setText(contact.phone);
        viewHolder.tvCountryValue.setText(contact.country);

        final Button editButton = (Button) viewHolder.itemView.findViewById(btnEdit);
        final Button deleteButton = (Button) viewHolder.itemView.findViewById(R.id.btnDelete);

        View.OnClickListener editListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClickListener.onEditClick(viewHolder.itemView, position);
            }
        };

        View.OnClickListener deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClickListener.onDeleteClick(viewHolder.itemView, position);
            }
        };

        editButton.setOnClickListener(editListener);
        deleteButton.setOnClickListener(deleteListener);
    }

    @Override
    public int getItemCount() {
        return aiContactList.size();
    }


    public class AIContactListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameValue;
        private TextView tvEmailValue;
        private TextView tvPhoneValue;
        private TextView tvCountryValue;

        public AIContactListViewHolder(View itemView) {
            super(itemView);
            tvNameValue = (TextView) itemView.findViewById(R.id.tvNameValue);
            tvEmailValue = (TextView) itemView.findViewById(R.id.tvEmailValue);
            tvPhoneValue = (TextView) itemView.findViewById(R.id.tvPhoneValue);
            tvCountryValue = (TextView) itemView.findViewById(R.id.tvCountryValue);
        }
    }


}
