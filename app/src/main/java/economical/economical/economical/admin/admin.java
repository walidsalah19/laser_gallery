package economical.economical.economical.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import economical.economical.economical.R;
import economical.economical.economical.admin.adapters.categories_adapter;
import economical.economical.economical.admin.viewmodels.categories_viewmodel;
import economical.economical.economical.data.category_data;

public class admin extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private categories_viewmodel viewmodel;
    private categories_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar_initialize();
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_framelayout,new categories()).addToBackStack(null).commitAllowingStateLoss();
    }
    private void toolbar_initialize() {
        toolbar = findViewById(R.id.manager_appbar_main);
        setSupportActionBar(toolbar);
    }

}