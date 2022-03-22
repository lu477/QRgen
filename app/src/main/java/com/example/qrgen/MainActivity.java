package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.qrgen.auth.AuthMain;
import com.example.qrgen.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new previewFragment());
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.m_preview:
                    replaceFragment(new previewFragment());
                    Log.d(TAG, "On switch to preview");
                    return true;
                case R.id.m_add_new:
                    replaceFragment(new add_newFragment());
                    Log.d(TAG, "On switch to add_new");
                    return true;
                case R.id.m_edit:
                    Toast t = Toast.makeText(getApplicationContext(),"Click on an item in the list tab", Toast.LENGTH_LONG);
                    t.show();
                    return false;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLay_main,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            startActivity(new Intent(this, AuthMain.class));
        } else {
            Toast t = Toast.makeText(getApplicationContext(),"Welcome " + currentUser.getEmail() ,Toast.LENGTH_LONG);
            t.show();
        }
    }
}