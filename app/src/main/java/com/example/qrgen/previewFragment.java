package com.example.qrgen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class previewFragment extends Fragment implements MainAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<String> articles;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        articles = new ArrayList<String>();
        recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        adapter = new MainAdapter(getContext(),getList());
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this::onItemClick);

        super.onViewCreated(view, savedInstanceState);
    }

    private List<String> getList() {
        List<String> test_data = new ArrayList<>();
        test_data.add("Test artikal 01");
        test_data.add("Test artikal 02");
        test_data.add("Test artikal 03");
        test_data.add("Test artikal 04");
        test_data.add("Test artikal 05");
        test_data.add("Test artikal 06");
        test_data.add("Test artikal 07");
        test_data.add("Test artikal 08");
        test_data.add("Test artikal 09");
        test_data.add("Test artikal 10");
        test_data.add("Test artikal 11");
        test_data.add("Test artikal 12");
        test_data.add("Test artikal 13");
        test_data.add("Test artikal 14");
        test_data.add("Test artikal 15");
        test_data.add("Test artikal 16");
        test_data.add("Test artikal 17");
        test_data.add("Test artikal 18");
        test_data.add("Test artikal 19");
        test_data.add("Test artikal 20");
        test_data.add("Test artikal 21");
        test_data.add("Test artikal 22");
        test_data.add("Test artikal 23");
        test_data.add("Test artikal 24");
        return test_data;
    };

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        bundle = new Bundle();
        bundle.putString("id", String.valueOf(position));
        bundle.putString("article", getList().get(position));
        editFragment eFrag = new editFragment();
        eFrag.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(eFrag);
    }

}