package com.example.imm.mypractice.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.imm.mypractice.R;

import butterknife.ButterKnife;

public class ProfileActivity extends BottomBarActivity {

    private ImageView editname;
    private ImageView setphoneno;
    private ImageView setbio;

    private RelativeLayout resetpassword;
    private RelativeLayout agentprofiles;
    private RelativeLayout bookmarks;

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

                    break;

                case R.id.icon_edit_phone:

                    break;

                case R.id.icon_edit_bio:

                    break;

                case R.id.reset_password_from_profile:
                    Intent intent = new Intent(ProfileActivity.this,ResetPasswordActivity.class);
                    startActivity(intent);
                    break;

                case R.id.agent_from_profile:

                    break;
                case R.id.bookmark_from_profile:

                    break;

            }
        }
    };

}
