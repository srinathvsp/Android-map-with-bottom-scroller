package com.centurylink.ncp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.centurylink.ncp.utils.Constants;
import com.centurylink.ncp.utils.ValidateUserInfo;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edit_nome, edit_email, edit_password;
    TextView txt_alreadyHave;
    Button btn_registrar;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            email = extras == null ? "" : extras.getString(Constants.TAG_EMAIL);
        } else {
            email = savedInstanceState.getString(Constants.TAG_EMAIL);
        }
        initInstances();

    }
    public void initInstances() {


        edit_nome = (EditText) findViewById(R.id.edit_nome);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_email.setText(email);
        edit_password = (EditText) findViewById(R.id.edit_password);
        txt_alreadyHave = (TextView) findViewById(R.id.txt_already_have);
        txt_alreadyHave.setOnClickListener(this);

        btn_registrar = (Button) findViewById(R.id.btn_register);
        btn_registrar.setOnClickListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public void attemptCreate() {
        // Store values at the time of the login attempt.
        String name = edit_nome.getText().toString();
        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        ValidateUserInfo validate = new ValidateUserInfo();

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            edit_nome.setError(getString(R.string.error_field_required));
            focusView = edit_nome;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            edit_email.setError(getString(R.string.error_field_required));
            focusView = edit_email;
            cancel = true;
        } else if (!validate.isEmailValid(email)) {
            edit_email.setError(getString(R.string.error_invalid_email));
            focusView = edit_email;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            edit_password.setError(getString(R.string.error_field_required));
            focusView = edit_password;
            cancel = true;
        } else if (!validate.isPasswordValid(password)) {
            edit_password.setError(getString(R.string.error_invalid_password));
            focusView = edit_password;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //TODO Create account logic

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                attemptCreate();
                break;
            case R.id.txt_already_have:
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}
