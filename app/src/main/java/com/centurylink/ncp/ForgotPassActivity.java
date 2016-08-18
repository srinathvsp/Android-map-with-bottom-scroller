package com.centurylink.ncp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.centurylink.ncp.utils.Constants;
import com.centurylink.ncp.utils.ValidateUserInfo;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edit_email;
    TextView txt_remembered;
    Button btn_recover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String email;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            email = (extras == null) ? "" : extras.getString(Constants.TAG_EMAIL);
        } else {
            email = savedInstanceState.getString(Constants.TAG_EMAIL);
        }
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_email.setText(email);

        txt_remembered = (TextView) findViewById(R.id.txt_remembered);
        txt_remembered.setOnClickListener(this);

        btn_recover = (Button) findViewById(R.id.btn_recover);
        btn_recover.setOnClickListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public void attemptRecover() {
        // Store values at the time of the login attempt.
        String email = edit_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        ValidateUserInfo validate = new ValidateUserInfo();

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            edit_email.setError(getString(R.string.error_field_required));
            focusView = edit_email;
            cancel = true;
        } else if (!validate.isEmailValid(email)) {
            edit_email.setError(getString(R.string.error_invalid_email));
            focusView = edit_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //TODO Recover account logic
            // Show a progress spinner, and kick off a background task to
            // perform the user recover info attempt.

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_recover:
                attemptRecover();
                break;
            case R.id.txt_remembered:
                startActivity(new Intent(ForgotPassActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotPassActivity.this, MainActivity.class));
        finish();
    }
}
