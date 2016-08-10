package umut.gourmatch;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import umut.gourmatch.R;
import umut.gourmatch.OneFragment;

public class FeatureActivity extends AppCompatActivity {

    //private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabTitles;
    private String[] tabJavas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        Bundle extras = getIntent().getExtras();
        String feature = "";
        if (extras != null) {
            feature = extras.getString("FEATURE");
        } else {
            //ERROR STUFF CAUSE THIS SHOULDN'T HAPPEN
        }
        //Need to set title image and color scheme
        String titlePull = feature + "_Tab_Titles";
        String javaPull =  feature + "_Tab_Java";
        Log.d("FeatureActivity.java", feature + " feature");

        ImageView titleImage = (ImageView) findViewById(R.id.titleImage);
        String imageName = getResources().getString(getResources().getIdentifier(feature+"_Image", "string", getPackageName()));
        titleImage.setImageResource(getResources().getIdentifier(imageName, "drawable", getPackageName()));
        titleImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), getResources().getIdentifier(feature+"_main", "color", getPackageName())));

        tabJavas = getResources().getStringArray(getResources().getIdentifier(javaPull, "array", getPackageName()));
        tabTitles = getResources().getStringArray(getResources().getIdentifier(titlePull, "array", getPackageName()));

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), getResources().getIdentifier(feature+"_tabBack", "color", getPackageName())));
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(ContextCompat.getColor(getApplicationContext(), getResources().getIdentifier(feature+"_tabFont", "color", getPackageName())), ContextCompat.getColor(getApplicationContext(), getResources().getIdentifier(feature+"_tabFont", "color", getPackageName())));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(int i = 0; i < tabJavas.length; i++) {
            try {
                adapter.addFragment((Fragment) (Class.forName("umut.gourmatch."+tabJavas[i])).newInstance(), tabTitles[0]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

//        adapter.addFragment(new Listings(), "TWO");
//        adapter.addFragment(new OneFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}