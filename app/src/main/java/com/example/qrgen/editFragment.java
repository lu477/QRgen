package com.example.qrgen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.print.PrintHelper;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.WriterException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static android.content.ContentValues.TAG;
import static android.os.Environment.DIRECTORY_PICTURES;


public class editFragment extends Fragment {

    private DatabaseReference databaseRef;
    private FirebaseDatabase database;
    private EditText et_art;
    private EditText et_price;
    private EditText et_amount;
    private EditText et_color;
    private Button generate_qr;
    private Button btn_save;
    private Button btn_print;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private ImageView qrImage;
    private File extStorage = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
    private String savePath = extStorage.getPath();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance("https://qrgen-5ad40-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseRef = database.getReference("incentarva@gmailcom");

        qrImage = view.findViewById(R.id.imageViewQr);
        generate_qr = view.findViewById(R.id.buttonGen);
        btn_save = view.findViewById(R.id.buttonSave);
        btn_print = view.findViewById(R.id.btn_print);
        et_art = view.findViewById(R.id.et_edit_art);
        et_price = view.findViewById(R.id.et_edit_price);
        et_amount = view.findViewById(R.id.et_edit_amount);
        et_color = view.findViewById(R.id.et_edit_color);

        Bundle bundle = this.getArguments();

        verifyStoragePermissions(editFragment.this);

        if(bundle != null){
            Log.d(TAG, "id: "+ bundle.getString("id"));
            Log.d(TAG, "amount: "+ bundle.getString("amount"));
            Log.d(TAG, "price: "+ bundle.getString("price"));
            Log.d(TAG, "name: "+ bundle.getString("article"));
            Log.d(TAG, "color: "+ bundle.getString("color"));
            et_art.setText(bundle.getString("article"));
            et_price.setText(bundle.getString("price"));
            et_amount.setText(bundle.getString("amount"));
            et_color.setText(bundle.getString("color"));
        }


        generate_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
                qrgEncoder = new QRGEncoder("Artikal: " + bundle.getString("article") + " " + bundle.getString("color") + ", Cena: " + bundle.getString("price"), null, QRGContents.Type.TEXT, 512);
                // Getting QR-Code as Bitmap
                bitmap = qrgEncoder.getBitmap();
                // Setting Bitmap to ImageView
                qrImage.setImageBitmap(bitmap);
                // Save with location, value, bitmap returned and type of Image(JPG/PNG).
                QRGSaver qrgSaver = new QRGSaver();
                qrgSaver.save(savePath, et_color.getText().toString().trim().toLowerCase() + et_art.getText().toString().trim().toLowerCase(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                qrImage.setImageBitmap(bitmap);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_art.getText().toString().matches("") || et_price.getText().toString().matches("") || et_amount.getText().toString().matches("") || et_color.getText().toString().matches("") ){
                    Toast t = Toast.makeText(getContext(), "You must fill out all the fields", Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("name",et_art.getText().toString());
                    childUpdates.put("color", et_color.getText().toString());
                    childUpdates.put("price", et_price.getText().toString());
                    childUpdates.put("amount", et_amount.getText().toString());
                    databaseRef.child("article").child(bundle.getString("id")).updateChildren(childUpdates);
                    startActivity(new Intent(getContext(),MainActivity.class));
                }


            }
        });

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null){
                    doPhotoPrint();
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public static void verifyStoragePermissions(Fragment fragment) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    fragment.getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(getActivity());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }

}