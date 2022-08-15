package economical.economical.economical;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Locale;

import economical.economical.economical.admin.admin;
import economical.economical.economical.user.user;
import economical.economical.economical.user_access.login;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                change_local_language("ar");
                startActivity(new Intent(Splash.this, login.class));
            }
        },3000);
    }
    private void change_local_language(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
    }

