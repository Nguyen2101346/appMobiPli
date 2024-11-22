package com.example.utilitycalendar;

import static java.security.AccessController.getContext;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.home.DayHomeFragment;
import com.example.utilitycalendar.note.CategoryNoteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static Database appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {;


        super.onCreate(savedInstanceState);




        setContentView(R.layout.menu);

        appDatabase = Database.getInstance(getApplicationContext());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if(item.getItemId() == R.id.nav_home) {
                selectedFragment = new DayHomeFragment();
                getSupportActionBar().setTitle("Home");
            }
            if(item.getItemId() == R.id.nav_note) {
                selectedFragment = new CategoryNoteFragment();
                getSupportActionBar().setTitle("Note");
            }
            if(item.getItemId() == R.id.nav_flag) {
                selectedFragment = new FlagFragment();
                getSupportActionBar().setTitle("Flag");
            }
            if(item.getItemId() == R.id.nav_setting) {
                selectedFragment = new SettingFragment();
                getSupportActionBar().setTitle("Setting");
            }


            // Thay thế fragment tương ứng trong FrameLayout
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Load fragment mặc định
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }


}
