package umut.gourmatch;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateListingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText title_view;
    private EditText location_title_view;
    private EditText address_view;
    private EditText city_view;
    private EditText state_view;
    private EditText zip_code_view;
    private EditText description_view;


    private String title;
    private String location_title;
    private String address;
    private String city;
    private String state;
    private int    zip_code;
    private String description;

    private OnFragmentInteractionListener mListener;

    public CreateListingFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateListingFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static CreateListingFragment newInstance(String param1, String param2)
    {
        CreateListingFragment fragment = new CreateListingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    //OnFragmentInteractionListener mCallback;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_listing, container, false);
       // ImageView imageView = (ImageView) getView().findViewById(R.id.foo);
        title_view           = (EditText)rootView.findViewById(R.id.title);
        location_title_view  = (EditText)rootView.findViewById(R.id.location_title);
        address_view         = (EditText)rootView.findViewById(R.id.address);
        city_view            = (EditText)rootView.findViewById(R.id.city);
        state_view           = (EditText)rootView.findViewById(R.id.state);
        zip_code_view        = (EditText)rootView.findViewById(R.id.zip_code);
        description_view     = (EditText)rootView.findViewById(R.id.description);
        FloatingActionButton sendBtn = (FloatingActionButton)rootView.findViewById(R.id.fab);


        title = title_view.getText().toString();
        location_title = location_title_view.getText().toString();
        address = address_view.getText().toString();
        city = city_view.getText().toString();
        state = state_view.getText().toString();
        //zip_code = Integer.parseInt(zip_code_view.getText().toString());
        description = description_view.getText().toString();

        if(title.equals("") || location_title.equals("") || address.equals("") || city.equals("") || state.equals("") || zip_code==0 || description.equals(""))
        {
            sendBtn.setClickable(false);
        }
        else
        {
            sendBtn.setClickable(true);
        }


        sendBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(title.equals("") || location_title.equals("") || address.equals("") || city.equals("") || state.equals("") || zip_code==0 || description.equals(""))
                {
                    //sendBtn.setClickable(false);
                }
                else
                {
                    //sendBtn.setClickable(true);
                    buttonPressed(title);
                    buttonPressed(location_title);
                    buttonPressed(address);
                    buttonPressed(city);
                    buttonPressed(state);
                   //uttonPressed(zip_code
                    buttonPressed(description);
                }



              //buttonPressed("Fragment Button Pressed");

            }
        });




        return rootView; //inflater.inflate(R.layout.fragment_create_listing, container, false);


    }

    public void buttonPressed(String uri){
        if(mListener != null){
            mListener.onFragmentInteraction(uri);
        }
    }

    public void buttonPressed(String one, String two, String three, String four, String five, String six)
    {
        if(mListener != null){
     //       mListener.onFragmentInteraction(one, two, three, four, five, six);
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try{
            mListener = (OnFragmentInteractionListener) activity;
        } catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener{
        public void onFragmentInteraction(String uri);
    }
}
