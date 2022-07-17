package economical.economical.economical.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import economical.economical.economical.R;

public class user extends AppCompatActivity {
     private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        toolbar_initialize();
        replace_framelayout(new u_categories());
    }
    private void toolbar_initialize() {
        toolbar = findViewById(R.id.manager_appbar_main);
        setSupportActionBar(toolbar);
    }
    private void replace_framelayout(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.user_framelayout,fragment).addToBackStack(null).commitAllowingStateLoss();
    }
}