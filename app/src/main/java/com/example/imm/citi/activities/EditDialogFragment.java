package com.example.imm.citi.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.User;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by imm on 6/29/2017.
 */

public class EditDialogFragment extends DialogFragment implements View.OnClickListener{

    private EditText mEditText;
    private Unbinder unbinder;
    String edittype;
    private TextView title;
    private Button ok;
    private String text;

    @SuppressLint("ValidFragment")
    public EditDialogFragment(String edittype, String s){
        this.edittype=edittype;
        text = s;
    }

    public String getType(){
        return edittype;
    }

    public static EditDialogFragment newInstance(String type, String s){
        EditDialogFragment frag = new EditDialogFragment(type, s);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_name, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mEditText = (EditText) view.findViewById(R.id.text_new_name);
        mEditText.setText(text);
        if(edittype.equals("phone")){
            mEditText.setKeyListener(new DigitsKeyListener());
        }
        ok = (Button) view.findViewById(R.id.btn_dialog_ok);
        ok.setOnClickListener(this);

        mEditText.requestFocus();

        title = (TextView) view.findViewById(R.id.dialog_title);
        if(getType()=="name") title.setText("Enter new username");
        else if (getType()=="phone") title.setText("Enter phone no ");
        else if (getType()=="bio") title.setText("Write about you!");
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btn_dialog_ok){
            if(!mEditText.getText().toString().equals(text)){
                String name = User.Name;
                String phone = User.Phone;
                String bio = User.Bio;

                String newTxt = mEditText.getText().toString();

                if(edittype.equals("name"))
                    name = newTxt;
                else if(edittype.equals("phone"))
                    phone = newTxt;
                if(edittype.equals("bio"))
                    bio = newTxt;

                User user = new User();
                user.editProfile(name, phone, bio, this.getActivity());
            }
            dismiss();
        }
    }

    private boolean eligiblePhoneNumber(String newTxt) {
        return false;
    }
}
