
package com.bj4.yhh.projectx;

import java.util.HashMap;

import com.bj4.yhh.projectx.lot.LotteryData;
import com.bj4.yhh.projectx.lot.hk6.HK6AddOrMinusFragment;
import com.bj4.yhh.projectx.lot.hk6.HK6CombinationFragment;
import com.bj4.yhh.projectx.lot.hk6.HK6LastFragment;
import com.bj4.yhh.projectx.lot.hk6.HK6OrderedFragment;
import com.bj4.yhh.projectx.lot.hk6.HK6ParseService;
import com.bj4.yhh.projectx.lot.lt539.LT539AddOrMinusFragment;
import com.bj4.yhh.projectx.lot.lt539.LT539CombinationFragment;
import com.bj4.yhh.projectx.lot.lt539.LT539LastFragment;
import com.bj4.yhh.projectx.lot.lt539.LT539OrderedFragment;
import com.bj4.yhh.projectx.lot.lt539.LT539ParseService;
import com.bj4.yhh.projectx.lot.weli.WeLiAddOrMinusFragment;
import com.bj4.yhh.projectx.lot.weli.WeLiCombinationFragment;
import com.bj4.yhh.projectx.lot.weli.WeLiLastFragment;
import com.bj4.yhh.projectx.lot.weli.WeLiOrderedFragment;
import com.bj4.yhh.projectx.lot.weli.WeLiParseService;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
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
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout)findViewById(R.id.drawer_layout));
        mCurrentGameType = LotteryData.TYPE_HK6;
        mTitle = getString(R.string.hk6);
        startUpdateService();
    }

    private void startUpdateService() {
        // startService(new Intent(this, HK6ParseService.class));
        // startService(new Intent(this, LT539ParseService.class));
        // startService(new Intent(this, WeLiParseService.class));
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
                            rtn = HK6AddOrMinusFragment.getNewInstance();
                            break;
                    }
                    fragments.put(fragmentType, rtn);
                }
                break;
            case LotteryData.TYPE_539:
                fragments = mFragments.get(gameType);
                if (fragments == null) {
                    fragments = new HashMap<Integer, Fragment>();
                }
                rtn = fragments.get(fragmentType);
                if (rtn == null) {
                    switch (fragmentType) {
                        case FRAGMENT_TYPE_ORDERED:
                            rtn = LT539OrderedFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_COMBINATION:
                            rtn = LT539CombinationFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_LAST:
                            rtn = LT539LastFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_ADD_AND_MINUS:
                            rtn = LT539AddOrMinusFragment.getNewInstance();
                            break;
                    }
                    fragments.put(fragmentType, rtn);
                }
                break;
            case LotteryData.TYPE_WELI:
                fragments = mFragments.get(gameType);
                if (fragments == null) {
                    fragments = new HashMap<Integer, Fragment>();
                }
                rtn = fragments.get(fragmentType);
                if (rtn == null) {
                    switch (fragmentType) {
                        case FRAGMENT_TYPE_ORDERED:
                            rtn = WeLiOrderedFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_COMBINATION:
                            rtn = WeLiCombinationFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_LAST:
                            rtn = WeLiLastFragment.getNewInstance();
                            break;
                        case FRAGMENT_TYPE_ADD_AND_MINUS:
                            rtn = WeLiAddOrMinusFragment.getNewInstance();
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
        if (id == R.id.hk6) {
            mCurrentGameType = LotteryData.TYPE_HK6;
            onNavigationDrawerItemSelected(0);
            mTitle = getString(R.string.hk6);
            restoreActionBar();
            return true;
        } else if (id == R.id.lt539) {
            mCurrentGameType = LotteryData.TYPE_539;
            onNavigationDrawerItemSelected(0);
            mTitle = getString(R.string.lt539);
            restoreActionBar();
            return true;
            // } else if (id == R.id.weli) {
            // mCurrentGameType = LotteryData.TYPE_WELI;
            // onNavigationDrawerItemSelected(0);
            // mTitle = getString(R.string.weli);
            // restoreActionBar();
            // return true;
        }
        return super.onOptionsItemSelected(item);
    }
}