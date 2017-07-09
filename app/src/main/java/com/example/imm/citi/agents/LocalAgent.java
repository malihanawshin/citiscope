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

public class LocalAgent extends Agent {
    Boolean bookmarked=false;
    final String BKFILE = "bookmarkLocal.php", UNBKFILE = "unbookmarkLocal.php";
    private Button bookmarkBtn;

    public void setBookmarked(){
        bookmarked=true;
        System.out.println("bookmarked " + name);
    }

    public Boolean isBookmarked(){
        return bookmarked;
    }




    public void addLocalBookmark(Activity act, Button bookmark) {
        parent = act;
        bookmarkBtn = bookmark;

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        keys.add("email");
        keys.add("id");

        vals.add(User.Email);
        vals.add(id);

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, BKFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("false"))
                    Toast.makeText(parent,"Bookmark cannot be added",Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(parent,"Bookmark successfully added",Toast.LENGTH_LONG).show();
                    bookmarkBtn.setText("Unbookmark");
                }
            }
        });
    }


    public void removeLocalBookmark(Activity act, Button bookmark) {
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










    public LocalAgent(Parcel in) {
        super(in);
        bookmarked = (Boolean)in.readValue(null);
    }

    public static final Creator<LocalAgent> CREATOR = new Creator<LocalAgent>() {
        @Override
        public LocalAgent createFromParcel(Parcel in) {
            return new LocalAgent(in);
        }

        @Override
        public LocalAgent[] newArray(int size) {
            return new LocalAgent[size];
        }
    };

    public LocalAgent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel,i);
        parcel.writeValue(bookmarked);
    }
}
