package com.app.comic.ui.Activity.SignUp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.comic.MainFragmentActivity;
import com.app.comic.R;
import com.app.comic.ui.Activity.FragmentContainerActivity;
import com.app.comic.ui.Activity.Login.SignUpFragment;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class SignAsDriverActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //@InjectView(R.id.btnLogin) Button btnLogin;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, SignAsDriverFragment.newInstance()).commit();

        setTitle("Sign Up");
        setBackButton();
        // [END shared_tracker]

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
