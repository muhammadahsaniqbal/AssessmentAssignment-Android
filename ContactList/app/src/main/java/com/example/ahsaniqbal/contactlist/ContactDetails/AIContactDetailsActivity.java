package com.example.ahsaniqbal.contactlist.ContactDetails;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ahsaniqbal.contactlist.Models.AIContact;
import com.example.ahsaniqbal.contactlist.R;
import com.google.gson.Gson;


/**
 * Created by ahsaniqbal on 11/15/16.
 */

public class AIContactDetailsActivity extends Activity {

    private AIContact contact;

    private Bundle extras;

    private TextView tvNameValue;
    private TextView tvEmailValue;
    private TextView tvPhoneValue;
    private TextView tvCountryValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// Removing squared black edges, since we are using rounded edges...
        setContentView(R.layout.ai_contact_details_activity);

        initViews();
    }

    private void initViews() {
        tvNameValue = (TextView) findViewById(R.id.tvNameValue);
        tvEmailValue = (TextView) findViewById(R.id.tvEmailValue);
        tvPhoneValue = (TextView) findViewById(R.id.tvPhoneValue);
        tvCountryValue = (TextView) findViewById(R.id.tvCountryValue);

        extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.containsKey("contactJson")) {
                Gson gson = new Gson();
                contact = gson.fromJson(getIntent().getStringExtra("contactJson"), AIContact.class);
                loadContactDetails();
            }
        }
    }

    private void loadContactDetails() {
        tvNameValue.setText(contact.getDisplayName());
        tvEmailValue.setText(contact.getDisplayEmail());
        tvPhoneValue.setText(contact.getDisplayPhone());
        tvCountryValue.setText(contact.getDisplayCountry());
    }
}
