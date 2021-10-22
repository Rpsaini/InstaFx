package invite_earn;

import android.os.Bundle;
import android.view.View;

import com.web.instafx.BaseActivity;
import com.web.instafx.R;

public class InviteEarnScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        initiateObj();
    }
}
