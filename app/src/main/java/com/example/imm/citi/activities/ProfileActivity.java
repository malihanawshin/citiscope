package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.User;

import butterknife.ButterKnife;

public class ProfileActivity extends BottomBarActivity {

    private ImageView editname;
    private ImageView setphoneno;
    private ImageView setbio;

    private RelativeLayout resetpassword;
    private RelativeLayout agentprofiles;
    private RelativeLayout bookmarks;

    private TextView title;

    private TextView edtName, edtPhone, edtBio, edtEmail;

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Account");
        ButterKnife.bind(this);

        edtName = (TextView) findViewById(R.id.edt_name);
        edtName.setText(User.Name);
        edtEmail = (TextView) findViewById(R.id.edt_email);
        edtEmail.setText(User.Email);
        edtPhone = (TextView) findViewById(R.id.edt_phone);
        edtPhone.setText(User.Phone);
        edtBio = (TextView) findViewById(R.id.edt_bio);
        edtBio.setText(User.Bio);


        editname = (ImageView) findViewById(R.id.icon_edit_name);
        editname.setOnClickListener(clickListener);
        setphoneno = (ImageView) findViewById(R.id.icon_edit_phone);
        setphoneno.setOnClickListener(clickListener);
        setbio = (ImageView) findViewById(R.id.icon_edit_bio);
        setbio.setOnClickListener(clickListener);

        resetpassword = (RelativeLayout) findViewById(R.id.reset_password_from_profile);
        resetpassword.setOnClickListener(clickListener);
        agentprofiles = (RelativeLayout) findViewById(R.id.agent_from_profile);
        agentprofiles.setOnClickListener(clickListener);
        bookmarks = (RelativeLayout) findViewById(R.id.bookmark_from_profile);
        bookmarks.setOnClickListener(clickListener);
    }

    private void showNameEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment e = EditDialogFragment.newInstance("name", edtName.getText().toString());
        e.show(fm,"fragment_edit_name");
    }

    private void showPhoneEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment e = EditDialogFragment.newInstance("phone", edtPhone.getText().toString());
        e.show(fm,"fragment_edit_name");
    }

    private void showBioEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment e = EditDialogFragment.newInstance("bio", edtBio.getText().toString());
        e.show(fm,"fragment_edit_name");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.icon_edit_name:
                    showNameEditDialog();
                    break;

                case R.id.icon_edit_phone:
                    showPhoneEditDialog();
                    break;

                case R.id.icon_edit_bio:
                    showBioEditDialog();
                    break;

                case R.id.reset_password_from_profile:
                    Intent intent = new Intent(ProfileActivity.this,ResetPasswordActivity.class);
                    startActivity(intent);
                    break;

                case R.id.agent_from_profile:

                    break;
                case R.id.bookmark_from_profile:
                    Intent intentB = new Intent(ProfileActivity.this,BookmarkActivity.class);
                    startActivity(intentB);
                    break;

            }
        }
    };

}
