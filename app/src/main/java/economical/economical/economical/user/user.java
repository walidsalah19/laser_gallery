package economical.economical.economical.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.UUID;

import economical.economical.economical.R;
import economical.economical.economical.SendNotificationPack.APIService;
import economical.economical.economical.SendNotificationPack.Client;
import economical.economical.economical.SharedPref;

public class user extends AppCompatActivity {
     private Toolbar toolbar;
    String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        toolbar_initialize();
        replace_framelayout(new u_categories());
        updateToken();
    }
    private void toolbar_initialize() {
        toolbar = findViewById(R.id.manager_appbar_main);
        setSupportActionBar(toolbar);
    }
    private void replace_framelayout(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.user_framelayout,fragment).addToBackStack(null).commitAllowingStateLoss();
    }
    private void updateToken()
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d("tag", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    userToken = task.getResult();
                    Log.d("tag",userToken);
                    check_token();
                });
    }
    private void check_token()
    {
        DatabaseReference data=FirebaseDatabase.getInstance().getReference("Users");
        SharedPref share=new SharedPref(this);
        if(share.gettoken().equals(""))
        {
           // FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("token").setValue(userToken);
            share.settoken(userToken);
        }
        else {
            FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean found=false;
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(dataSnapshot.child("token").getValue().toString().equals(userToken))
                            {
                                found=true;
                                break;
                            }
                        }
                         if (!found)
                         {
                            data.child(UUID.randomUUID().toString()).child("token").setValue(userToken);
                         }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}