package umut.gourmatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Listings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Listings extends Fragment {

    //    private OnFragmentInteractionListener mListener;
    private ArrayList<ListingObj> list;
    private DatabaseReference mDatabase;
    private static final String TAG = "Listings.java";

    public Listings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Listings.
     */
    // TODO: Rename and change types and number of parameters
    public static Listings newInstance(String param1, String param2) {
        Listings fragment = new Listings();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listings, container, false);
        ImageView plus = (ImageView) view.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "****CLICKED****");
                Intent intent = new Intent(getActivity(),CreateListingActivity.class);
                startActivity(intent);
            }
        });
        final ScrollView sv = (ScrollView) view.findViewById(R.id.scrollView);
//        int ownColor = ContextCompat.getColor(getContext(), R.color.listing_owned);
//        int guestColor = ContextCompat.getColor(getContext(), R.color.listing_guest);
        list = new ArrayList<>();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild("listings")) {
                            DataSnapshot listings = dataSnapshot.child("listings");
                            Iterable<DataSnapshot> children = listings.getChildren();
                            for(DataSnapshot x : children) {
                                //May or may not work
                                Log.d("Listings.java", "Key: " + x.getKey());
                                ListingObj curr = new ListingObj(x.getKey(), (boolean) x.getValue(), getContext());

                                list.add(curr);
                            }
                        }
//                        if(dataSnapshot.hasChild("guest")) {
//                            DataSnapshot ownedListings = dataSnapshot.child("guest");
//                            Iterable<DataSnapshot> children = ownedListings.getChildren();
//                            for(DataSnapshot x : children) {
//                                //May or may not work
//                                ListingObj curr = new ListingObj((String) x.getValue(), false, getContext());
//                                list.add(curr);
//                            }
//                        }
                        for(final ListingObj obj : list) {
                            //0:Title, 1:Value, 2:bold, 3:italicized, 4:size level (small, med, large)
                            ArrayList<String[]> info = obj.getInfoMini();
                            LinearLayout lv = new LinearLayout(getContext());
                            lv.setBackgroundColor(obj.color);
                            lv.setOrientation(LinearLayout.VERTICAL);
                            for(String[] text : info) {
                                LinearLayout lh = new LinearLayout(getContext());
                                lh.setOrientation(LinearLayout.HORIZONTAL);
                                TextView title = new TextView(getContext());
                                title.setText(text[0] + ": ");
                                title.setTypeface(null, Typeface.BOLD);
                                TextView val = new TextView(getContext());
                                val.setText(text[1]);
                                boolean bold = text[2].equalsIgnoreCase("true");
                                boolean it = text[3].equalsIgnoreCase("true");
                                if(bold && it) {
                                    val.setTypeface(null, Typeface.BOLD_ITALIC);
                                } else if(bold) {
                                    val.setTypeface(null, Typeface.BOLD);
                                } else if(it) {
                                    val.setTypeface(null, Typeface.ITALIC);
                                }
                                if(text[4].equalsIgnoreCase("small")) {
                                    val.setTextSize(getResources().getDimension(R.dimen.text_size_small));
                                    title.setTextSize(getResources().getDimension(R.dimen.text_size_small));
                                } else if(text[4].equalsIgnoreCase("large")){
                                    val.setTextSize(getResources().getDimension(R.dimen.text_size_large));
                                    title.setTextSize(getResources().getDimension(R.dimen.text_size_large));
                                } else {
                                    val.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                                    title.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                                }
                                lh.addView(title);
                                lh.addView(val);
                                lv.addView(lh);
                            }
                            lv.setClickable(true);
                            lv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ///Uncomment when a listing viewer is created and modify accordingly
//                                    Intent intent = new Intent(view.getContext().getApplicationContext(), ListingActivity.class);
//                                    intent.putExtra("LID", obj.listingID);
//                                    startActivity(intent);
                                }
                            });
                            //Would add them in reverse, but keep plus at bottom
                            //sv.addView(lv, 0);
                        }

//                        else {
//                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Throw up a toast
                    }
                }
        );
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
