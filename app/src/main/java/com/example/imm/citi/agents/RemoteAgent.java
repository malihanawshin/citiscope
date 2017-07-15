package com.example.imm.citi.agents;

import android.app.Activity;
import android.os.Parcel;
import android.widget.Button;
import android.widget.Toast;

import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.User;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class RemoteAgent extends Agent{
    public String url, serviceName;
    final String BKFILE = "bookmarkRemote.php", UNBKFILE = "unbookmarkRemote.php";
    private Button bookmarkBtn;

    public RemoteAgent(String srvName){
        serviceName = srvName;
    }

    public void setAttr(String name, String phone, String url, String address, String email){
        this.name = name;
        this.phone = phone;
        this.url = url;
        this.address = address;
        this.email = email;

        ArrayList<String> phones = multiplePhones(phone);
        this.phone = phones.get(0);

        if(!this.phone.startsWith("+88") && !this.phone.equals("")) this.phone = "+88" + this.phone;

        System.out.println("srvname: " + serviceName);
    }



    public void addRemoteBookmark(Activity act) {
        parent = act;
        initiateArrays();

        if(parent!=null)System.out.println(keys + " <-> " + vals);

        Database db = new Database();

        db.update(new RetrievalData(keys, vals, BKFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("false"))
                    Toast.makeText(parent,"Bookmark cannot be added",Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(parent,"Bookmark successfully added",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public void removeRemoteBookmark(Activity act, Button bookmark) {
        parent = act;
        bookmarkBtn = bookmark;

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        keys.add("email");
        keys.add("id");

        vals.add(User.Email);
        vals.add(id);

        Database db = new Database();

        db.update(new RetrievalData(keys, vals, UNBKFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("false"))
                    Toast.makeText(parent,"Bookmark cannot be removed",Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(parent,"Bookmark successfully removed",Toast.LENGTH_LONG).show();
                    bookmarkBtn.setText("Bookmark");
                }
            }
        });
    }

    protected void initiateArrays() {
        keys = new ArrayList<>();
        vals = new ArrayList<>();

        keys.add("name");
        keys.add("phone");
        keys.add("email");
        keys.add("url");
        keys.add("location");
        keys.add("bkEmail");
        keys.add("service");

        vals.add(name);
        vals.add(phone);
        vals.add(email);
        vals.add(url);
        vals.add(address);
        vals.add(User.Email);
        vals.add(serviceName);
        //System.out.println("srvname: " + serviceName);
    }










    public RemoteAgent(Parcel in) {
        super(in);
        url = in.readString();
        serviceName = in.readString();
    }

    public static final Creator<RemoteAgent> CREATOR = new Creator<RemoteAgent>() {
        @Override
        public RemoteAgent createFromParcel(Parcel in) {
            return new RemoteAgent(in);
        }

        @Override
        public RemoteAgent[] newArray(int size) {
            return new RemoteAgent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(url);
        parcel.writeString(serviceName);
    }

//    public RemoteAgent(){
//
//    }
}
