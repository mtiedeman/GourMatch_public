package umut.gourmatch;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Megan on 8/3/2016.
 */

/*
Should contain all the information for a listing
May be used to pass information
 */
public class ListingObj {
    private final int OWN;
    private final int GUEST;
    public String listingID;
    public int color;
    private boolean empty = true;
    private HashMap<String, Object> list;
    private Context context;

    private String[] infoMini;

    public ListingObj(String id, boolean owner, Context c) {
        context = c;
        OWN = ContextCompat.getColor(context, R.color.listing_owned);
        GUEST = ContextCompat.getColor(context, R.color.listing_owned);
        if(owner) {
            color = OWN;
        } else {
            color = GUEST;
        }
        listingID = id;
        infoMini = context.getResources().getStringArray(R.array.short_listing);
        list = new HashMap<String, Object>();
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseReference mD = FirebaseDatabase.getInstance().getReference();
                mD.child("listings").child(listingID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("uid")) {
                            empty = false;
                            collectData(dataSnapshot);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        a.run();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //end
    }

    private void collectData(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
        Iterator<DataSnapshot> it = iterable.iterator();
        while(it.hasNext()) {
            DataSnapshot curr = it.next();
            String title = curr.getKey();
            Object val = curr.getValue();
            list.put(title, val);
        }
        notify();
    }

    public ArrayList<String[]> getInfoMini() {
        ArrayList<String[]> total = new ArrayList<>();
        for(String title : infoMini) {
            //0:Title, 1:Value, 2:bold, 3:italicized, 4:size level (small, med, large)
            String[] curr = new String[5];
            curr[0] = title;
            int id = context.getResources().getIdentifier(title, "array", context.getPackageName());
            String[] dbPull = context.getResources().getStringArray(id);
            String[] pulled = new String[dbPull.length];
            for(int i = 0; i < dbPull.length; i++) {
                pulled[i] = (String) list.get(dbPull[0]);
            }
            String sep = ", ";
            String val = "";
            if(title.contains("time") || title.contains("Time")) {
                sep = ":";
            }
            if(title.contains("date") || title.contains("Date")) {
                String month = "";
                String day = "";
                String year = "";
                for(int i = 0; i < dbPull.length; i++) {
                    if(dbPull[i].contains("month") || dbPull[i].contains("Month")) {
                        month = new DateFormatSymbols().getMonths()[Integer.parseInt(pulled[i])];
                    }
                    else if(dbPull[i].contains("year") || dbPull[i].contains("Year")) {
                        year = pulled[i];
                    } else {
                        day = pulled[i];
                    }
                }
                val = month + day + sep + year;
            } else  {
                for (int i = 0; i < pulled.length; i++) {
                    val += pulled[i];
                    if(i+1 != pulled.length) {
                        val += sep;
                    }
                }
            }
            curr[1] = val;
            int idSet = context.getResources().getIdentifier(title+"_settings", "array", context.getPackageName());
            String[] set = context.getResources().getStringArray(idSet);
            curr[2] = set[0];
            curr[3] = set[1];
            curr[4] = set[2];
            total.add(curr);
        }
        return total;
    }

    public boolean isEmpty() {
        return empty;
    }
}
