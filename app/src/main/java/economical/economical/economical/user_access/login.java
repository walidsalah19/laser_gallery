package economical.economical.economical.user_access;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import economical.economical.economical.R;
import economical.economical.economical.admin.admin;
import economical.economical.economical.user.user;

public class login extends AppCompatActivity {
    private EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email_edittext_login);
        password=findViewById(R.id.email_password_text_login);
        login();
        show_password_method();
    }
    private void login()
    {
        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("admin@gmail.com") && password.getText().toString().equals("12345678")) {
                    startActivity(new Intent(login.this, admin.class));
                }
                else {
                    Toast.makeText(login.this, "تاكد من البريد الالكتروني و كلمة المرور ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void show_password_method() {
        CheckBox show_password=findViewById(R.id.show_password_login);
        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (show_password.isChecked()) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
    }
}