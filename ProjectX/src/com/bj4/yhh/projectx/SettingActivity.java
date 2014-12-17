
package com.bj4.yhh.projectx;

import android.app.Activity;
import android.os.Bundle;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingPreference()).commit();
    }

    public void onStart() {
        super.onStart();
        GATracker.startActivity(this);
    }

    public void onStop() {
        GATracker.stopActivity(this);
        super.onStop();
    }
}
