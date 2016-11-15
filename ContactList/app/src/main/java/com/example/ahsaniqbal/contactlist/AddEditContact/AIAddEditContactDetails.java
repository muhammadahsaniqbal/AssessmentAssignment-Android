package com.example.ahsaniqbal.contactlist.AddEditContact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ahsaniqbal.contactlist.DataManager.AIContactsManager;
import com.example.ahsaniqbal.contactlist.Models.AIContact;
import com.example.ahsaniqbal.contactlist.R;
import com.google.gson.Gson;

import static com.example.ahsaniqbal.contactlist.R.id.tvCountryValue;
import static com.example.ahsaniqbal.contactlist.R.id.tvEmailValue;
import static com.example.ahsaniqbal.contactlist.R.id.tvNameValue;
import static com.example.ahsaniqbal.contactlist.R.id.tvPhoneValue;

/**
 * Created by ahsaniqbal on 11/15/16.
 */

public class AIAddEditContactDetails extends Activity implements View.OnClickListener {

    private AIContact contact;

    private Boolean isEdit = false;

    private Bundle extras;

    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etCountry;

    private Button btnAddUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// Removing squared black edges, since we are using rounded edges...
        setContentView(R.layout.ai_add_edit_contact_details_activity);

        initViews();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddUpdate) {
            if (isEdit == true) {
                AIContact contact = new AIContact();
                contact.name = etName.getText().toString();
                contact.email = etEmail.getText().toString();
                contact.phone = etPhone.getText().toString();
                contact.country = etCountry.getText().toString();
                AIContactsManager.getInstance(AIAddEditContactDetails.this).updateContact(contact);
                setResult(RESULT_OK, new Intent());
                finish();
            } else {
                AIContact contact = new AIContact();
                contact.name = etName.getText().toString();
                contact.email = etEmail.getText().toString();
                contact.phone = etPhone.getText().toString();
                contact.country = etCountry.getText().toString();
                AIContactsManager.getInstance(AIAddEditContactDetails.this).saveContact(contact);
                setResult(RESULT_OK, new Intent());
                finish();
            }
        }
    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etCountry = (EditText) findViewById(R.id.etCountry);

        extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.containsKey("isEdit")) {
                String canEdit = extras.getString("isEdit");
                if (canEdit.equals("true")) {
                    isEdit = true;
                    etEmail.setFocusable(false);
                    etEmail.setEnabled(false);
                    if(extras.containsKey("contactJson")) {
                        Gson gson = new Gson();
                        contact = gson.fromJson(getIntent().getStringExtra("contactJson"), AIContact.class);
                        loadContactDetails();
                    }
                } else {
                    isEdit = false;
                    etEmail.setFocusable(true);
                    etEmail.setEnabled(true);
                }

            }
        }

        btnAddUpdate = (Button) findViewById(R.id.btnAddUpdate);
        btnAddUpdate.setOnClickListener(this);
    }

    private void loadContactDetails() {
        etName.setText(contact.getDisplayName());
        etEmail.setText(contact.getDisplayEmail());
        etPhone.setText(contact.getDisplayPhone());
        etCountry.setText(contact.getDisplayCountry());
    }
}
