package com.cleiton.sdmlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String STATE_CHECKBOX_NOTIFICATION = "state_checkbox_notification";
    private final String IS_SELECTED_RB_NOTIFICATION = "is_selected_rb_notification";

    private CheckBox mCheckboxNotification;
    private RadioGroup mRadioGroupNotificacoes;
    private EditText nome;
    private EditText telefone;
    private EditText email;
    private LinearLayout mLinearLayoutTelephone;
    private ArrayList<View> fones;
    private LayoutInflater layoutInflater;
    private LinearLayout linearLayoutEmail;
    private ArrayList<View> mEmails;
    private HashMap<String, Integer> mHashTelephones;
    private HashMap<String, Integer> mHashEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_main);

        layoutInflater = getLayoutInflater();

        mCheckboxNotification = findViewById(R.id.check_box_notifications);
        mRadioGroupNotificacoes = findViewById(R.id.rg_notifications);
        nome = findViewById(R.id.edit_text_name);
        telefone = findViewById(R.id.edit_text_telephone);
        email = findViewById(R.id.edit_text_email);
        mLinearLayoutTelephone = findViewById(R.id.linear_layout_telephone);
        fones = new ArrayList<>();
        linearLayoutEmail = findViewById(R.id.linear_layout_email);
        mEmails = new ArrayList<>();
        if (mHashTelephones == null) {
            mHashTelephones = new HashMap<>();
        }
        if (mHashEmails == null) {
            mHashEmails = new HashMap<>();
        }

        mCheckboxNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRadioGroupNotificacoes.setVisibility(View.VISIBLE);
                } else {
                    mRadioGroupNotificacoes.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_CHECKBOX_NOTIFICATION, mCheckboxNotification.isChecked());
        outState.putInt(IS_SELECTED_RB_NOTIFICATION, mRadioGroupNotificacoes.getCheckedRadioButtonId());

        mHashEmails.clear();
        for (View v : mEmails) {
            EditText innerEmailEditText = v.findViewById(R.id.edit_text_email);
            Spinner innerTipoEmailSpinner = v.findViewById(R.id.spinner_email_type);
            mHashEmails.put(innerEmailEditText.getText().toString(), innerTipoEmailSpinner.getSelectedItemPosition());
        }
        outState.putSerializable("emails_list", mHashEmails);

        mHashTelephones.clear();
        for (View v : fones) {
            EditText innerTelefoneEditText = v.findViewById(R.id.edit_text_telephone);
            Spinner innerTipoTelefoneSpinner = v.findViewById(R.id.spinner_telephone_type);
            mHashTelephones.put(innerTelefoneEditText.getText().toString(), innerTipoTelefoneSpinner.getSelectedItemPosition());
        }
        outState.putSerializable("telephones_list", mHashTelephones);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCheckboxNotification.setChecked(savedInstanceState.getBoolean(STATE_CHECKBOX_NOTIFICATION, false));

        int idRadioButtonSelected = savedInstanceState.getInt(IS_SELECTED_RB_NOTIFICATION, -1);
        if (idRadioButtonSelected != -1) {
            mRadioGroupNotificacoes.check(idRadioButtonSelected);
        }

        mHashEmails = (HashMap<String, Integer>) savedInstanceState.getSerializable("emails_list");
        for (Map.Entry<String, Integer> v : mHashEmails.entrySet()) {
            View newEmailLayout = layoutInflater.inflate(R.layout.email_layout, null);
            ((EditText) newEmailLayout.findViewById(R.id.edit_text_email)).setText(v.getKey());
            ((Spinner) newEmailLayout.findViewById(R.id.spinner_email_type)).setSelection(v.getValue());
            mEmails.add(newEmailLayout);
            linearLayoutEmail.addView(newEmailLayout);

        }

        mHashTelephones = (HashMap<String, Integer>) savedInstanceState.getSerializable("telephones_list");
        for (Map.Entry<String, Integer> v : mHashTelephones.entrySet()) {
            View newTelephoneLayout = layoutInflater.inflate(R.layout.telephone_layout, null);
            ((EditText) newTelephoneLayout.findViewById(R.id.edit_text_telephone)).setText(v.getKey());
            ((Spinner) newTelephoneLayout.findViewById(R.id.spinner_telephone_type)).setSelection(v.getValue());
            fones.add(newTelephoneLayout);
            mLinearLayoutTelephone.addView(newTelephoneLayout);

        }
    }

    public void clearForm(View button) {
        mCheckboxNotification.setChecked(false);
        mRadioGroupNotificacoes.clearCheck();
        nome.setText("");
        telefone.setText("");
        email.setText("");
        fones.clear();
        mLinearLayoutTelephone.removeAllViews();
        mEmails.clear();
        linearLayoutEmail.removeAllViews();
        nome.requestFocus();

    }

    public void addTelephone(View button) {
        if (button.getId() == R.id.button_add_telephone) {
            View newTelephoneLayout = layoutInflater.inflate(R.layout.telephone_layout, null);
            fones.add(newTelephoneLayout);
            mLinearLayoutTelephone.addView(newTelephoneLayout);
        }
    }

    public void addEmail(View button) {
        if (button.getId() == R.id.button_add_email) {
            View newEmailLayout = layoutInflater.inflate(R.layout.email_layout, null);
            mEmails.add(newEmailLayout);
            linearLayoutEmail.addView(newEmailLayout);
        }
    }
}
