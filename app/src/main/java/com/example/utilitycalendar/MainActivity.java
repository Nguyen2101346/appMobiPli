package com.example.utilitycalendar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.utilitycalendar.Helper.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomSheetManager bottomSheetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomSheetManager = new BottomSheetManager(this);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if(item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                getSupportActionBar().setTitle("Home");
            }
            if(item.getItemId() == R.id.nav_note) {
                selectedFragment = new NoteFragment();
                getSupportActionBar().setTitle("Note");
            }
            if(item.getItemId() == R.id.nav_add) {
                bottomSheetManager.showBottomSheet();
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

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}
