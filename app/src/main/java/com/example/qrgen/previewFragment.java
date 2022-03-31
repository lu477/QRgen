package com.example.qrgen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.qrgen.auth.AuthMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class previewFragment extends Fragment implements MainAdapter.ItemClickListener {

    private Button refreshBtn;
    private Button btnSignOut;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<Article> articles;
    private Bundle bundle;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private FirebaseDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        refreshBtn = (Button) view.findViewById(R.id.btn_refresh);
        btnSignOut = (Button) view.findViewById(R.id.btn_signOut);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance("https://qrgen-5ad40-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseRef = database.getReference("incentarva@gmailcom");

        articles = new ArrayList<Article>();
        databaseRef.child("article").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Article a1 = dataSnapshot.getValue(Article.class);
                    articles.add(a1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: "+ error.getMessage());
            }
        });
        recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainAdapter(getContext(), articles);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this::onItemClick);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articles.clear();
                databaseRef.child("article").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            Article a1 = dataSnapshot.getValue(Article.class);
                            articles.add(a1);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: "+ error.getMessage());
                    }
                });
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), AuthMain.class));
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(View view, int position) {
        bundle = new Bundle();
        bundle.putString("id", String.valueOf(position));
        bundle.putString("article", articles.get(position).name);
        bundle.putString("price", articles.get(position).price);
        bundle.putString("color", articles.get(position).color);
        bundle.putString("amount", articles.get(position).amount);
        editFragment eFrag = new editFragment();
        eFrag.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(eFrag);
    }

}