
package com.bj4.yhh.projectx;

import java.util.HashMap;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.hk6.HK6CombinationFragment;
import com.bj4.yhh.projectx.lot.hk6.HK6LastFragment;
import com.bj4.yhh.projectx.lot.hk6.HK6OrderedFragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends Activity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final int FRAGMENT_TYPE_ORDERED = 0;

    public static final int FRAGMENT_TYPE_COMBINATION = 1;

    public static final int FRAGMENT_TYPE_LAST = 2;

    public static final int FRAGMENT_TYPE_ADD_AND_MINUS = 3;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    // <gameType, <fragmentType, fragment>>
    private final HashMap<Integer, HashMap<Integer, Fragment>> mFragments = new HashMap<Integer, HashMap<Integer, Fragment>>();

    private int mCurrentGameType = 0;

    private int mCurrentFragmentType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout)findViewById(R.id.drawer_layout));
        mCurrentGameType = LotteryData.TYPE_HK6;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mCurrentFragmentType = position;
        FragmentManager fragmentManager = getFragmentManager();
        final Fragment fragment = getFragment(mCurrentGameType, mCurrentFragmentType);
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    private Fragment getFragment(final int gameType, final int fragmentType) {
        HashMap<Integer, Fragment> fragments = null;
        Fragment rtn = null;
        switch (gameType) {
            case LotteryData.TYPE_HK6:
                fragments = mFragments.get(gameType);
                if (fragments == null) {
                    fragments = new HashMap<Integer, Fragment>();
                }
                rtn = fragments.get(fragmentType);
                if (rtn == null) {
                    switch (fragmentType) {
                        case FRAGMENT_TYPE_ORDERED:
                            rtn = HK6OrderedFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_COMBINATION:
                            rtn = HK6CombinationFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_LAST:
                            rtn = HK6LastFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_ADD_AND_MINUS:
                            break;
                    }
                    fragments.put(fragmentType, rtn);
                }
                break;
            default:
                break;
        }
        if (rtn != null) {
            mFragments.put(gameType, fragments);
        }
        return rtn;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
