package com.example.ahsaniqbal.contactlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ahsaniqbal.contactlist.Adapter.AIContactListAdapter;
import com.example.ahsaniqbal.contactlist.AddEditContact.AIAddEditContactDetails;
import com.example.ahsaniqbal.contactlist.ContactDetails.AIContactDetailsActivity;
import com.example.ahsaniqbal.contactlist.DataManager.AIContactsManager;
import com.example.ahsaniqbal.contactlist.Listeners.DeleteClickListener;
import com.example.ahsaniqbal.contactlist.Listeners.EditClickListener;
import com.example.ahsaniqbal.contactlist.Listeners.OnRecyclerItemClickListener;
import com.example.ahsaniqbal.contactlist.Models.AIContact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final private int requestCode = 1;

    private Button btnAdd;

    private Context context;
    private RecyclerView recyclerViewContactList;
    private ArrayList<AIContact> aiContactList;
    private AIContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// Removing squared black edges, since we are using rounded edges...
        setContentView(R.layout.activity_main);

        initViews();

        loadContactList();
    }

    private void initViews() {
        recyclerViewContactList = (RecyclerView) findViewById(R.id.recyclerViewContactList);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
    }

    private void loadContactList() {


        aiContactList = null;
        contactListAdapter = null;

//        AIContact contact1 = new AIContact();
//        contact1.name = "Faizan";
//
//        AIContact contact2 = new AIContact();
//        contact2.name = "Absar";

        aiContactList = AIContactsManager.getInstance(MainActivity.this).getContacts();
        if (aiContactList == null) {
            return;
        }
//        aiContactList.add(contact1);
//        aiContactList.add(contact2);

        recyclerViewContactList.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);

        recyclerViewContactList.setLayoutManager(mLayoutManager);

        recyclerViewContactList.setItemAnimator(new DefaultItemAnimator());

        contactListAdapter = new AIContactListAdapter(aiContactList, recyclerViewContactList);

        recyclerViewContactList.setAdapter(contactListAdapter);

        contactListAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Intent contactDetailsIntent = new Intent(MainActivity.this, AIContactDetailsActivity.class);
                Gson gson = new Gson();
                String contactJson = gson.toJson(aiContactList.get(position));
                contactDetailsIntent.putExtra("contactJson", contactJson);
                MainActivity.this.startActivity(contactDetailsIntent);
            }

        });

        contactListAdapter.setEditClickListener(new EditClickListener() {
            @Override
            public void onEditClick(View v, final int position) {
                Intent addEditContactDetailsIntent = new Intent(MainActivity.this, AIAddEditContactDetails.class);
                Gson gson = new Gson();
                String contactJson = gson.toJson(aiContactList.get(position));
                addEditContactDetailsIntent.putExtra("contactJson", contactJson);
                addEditContactDetailsIntent.putExtra("isEdit", "true");
                MainActivity.this.startActivityForResult (addEditContactDetailsIntent, requestCode);
            }
        });

        contactListAdapter.setDeleteClickListener(new DeleteClickListener() {
            @Override
            public void onDeleteClick(View v, final int position) {
                AIContactsManager.getInstance(MainActivity.this).deleteContact(aiContactList.get(position));
                loadContactList();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            Intent addEditContactDetailsIntent = new Intent(MainActivity.this, AIAddEditContactDetails.class);
            addEditContactDetailsIntent.putExtra("isEdit", "false");
            MainActivity.this.startActivityForResult (addEditContactDetailsIntent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) {
                loadContactList();
            }
        }
    }
}
