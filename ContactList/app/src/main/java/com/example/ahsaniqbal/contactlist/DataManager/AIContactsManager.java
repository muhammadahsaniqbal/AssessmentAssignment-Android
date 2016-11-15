package com.example.ahsaniqbal.contactlist.DataManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ahsaniqbal.contactlist.Models.AIContact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahsaniqbal on 11/15/16.
 */

public class AIContactsManager {

    public static AIContactsManager contactsManager;

    public Context localContext;

    public AIContactsManager(Context context) {
        localContext = context;
    }

    //Singleton
    public static synchronized AIContactsManager getInstance(Context context) {
        if (contactsManager == null) {
            contactsManager = new AIContactsManager(context);
        }
        return contactsManager;
    }

    //Create Record
    public void saveContact(AIContact contact) {
        ArrayList<AIContact> contactsList = getContacts();
        if (contactsList == null) {
            contactsList = new ArrayList<AIContact>();
        }
        contactsList.add(contact);
        saveContactsArray(contactsList);
    }

    public void saveContactsArray (ArrayList<AIContact> contactsList) {
        Gson gson = new Gson();
        String json = gson.toJson(contactsList);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(localContext);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("contacts", json);
        prefsEditor.commit();
    }

    //Read Records
    public ArrayList<AIContact> getContacts () {
        Type type = new TypeToken<ArrayList<AIContact>>(){}.getType();
        Gson gson = new Gson();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(localContext);
        String json = appSharedPrefs.getString("contacts", "");
        ArrayList<AIContact> contactsList = gson.fromJson(json, type);
        return contactsList;
    }

    //Update Record
    public void updateContact(AIContact contact) {
        ArrayList<AIContact> contactsList = getContacts();
        for (int i = 0; i < contactsList.size(); i++) {
            AIContact currentContact = contactsList.get(i);
            if (currentContact.email.equals(contact.email)) {
                contactsList.set(i, contact);
                break;
            }
        }
        saveContactsArray(contactsList);
    }

    //Delete Record
    public void deleteContact(AIContact contact) {
        ArrayList<AIContact> contactsList = getContacts();
        for (int i = 0; i < contactsList.size(); i++) {
            AIContact currentContact = contactsList.get(i);
            if (currentContact.email.equals(contact.email)) {
                contactsList.remove(i);
                break;
            }
        }
        saveContactsArray(contactsList);
    }

}
