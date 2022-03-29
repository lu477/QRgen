package com.example.qrgen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_newFragment extends Fragment {

    private DatabaseReference databaseRef;
    private FirebaseDatabase database;
    private Button add_btn;
    private EditText et_artName;
    private EditText et_artColor;
    private EditText et_artAmount;
    private EditText et_artPrice;
    private String artName;
    private String artColor;
    private String artAmount;
    private String artId;
    private String artPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance("https://qrgen-5ad40-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseRef = database.getReference("incentarva@gmailcom");

        et_artPrice = view.findViewById(R.id.addnew_et_price);
        et_artName = view.findViewById(R.id.addnew_et_name);
        et_artAmount = view.findViewById(R.id.addnew_et_amount);
        et_artColor = view.findViewById(R.id.addnew_et_color);

        add_btn = view.findViewById(R.id.btn_add_art);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                artName = et_artName.getText().toString();
                artAmount = et_artAmount.getText().toString();
                artColor = et_artColor.getText().toString();
                artPrice = et_artPrice.getText().toString();

                if (artName.matches("") || artAmount.matches("") || artColor.matches("")) {
                    Toast.makeText(getContext(),"You must fill out all the fields",Toast.LENGTH_SHORT).show();
                } else {
                    databaseRef.child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                Log.d("firebase", task.getResult().getValue().toString());
                                writeNewArt(artName,artAmount,artColor,artPrice);
                            }
                        }
                    });
                }
            }
        });
    }

    public void writeNewArt(String name, String amount, String color, String price) {
        databaseRef.child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getChildrenCount()));
                    artId = String.valueOf(task.getResult().getChildrenCount());
                    Article a1 = new Article(name, amount, color, price);
                    databaseRef.child("article").child(artId).setValue(a1);
                    startActivity(new Intent(getContext(),MainActivity.class));
                }
            }
        });
    }
}

