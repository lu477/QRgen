package com.example.qrgen;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.io.File;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static android.content.ContentValues.TAG;


public class editFragment extends Fragment {

    private TextView textView;
    private Button generate_qr;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private ImageView qrImage;
    private String savePath = Environment.getExternalStorageDirectory().getPath();
    private File hi = Environment.getExternalStoragePublicDirectory(
    Environment.DIRECTORY_PICTURES
    );

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

        qrImage = view.findViewById(R.id.imageViewQr);
        generate_qr = view.findViewById(R.id.buttonGen);
        textView = view.findViewById(R.id.tv_edit_art);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            textView.setText("Selected item "+ bundle.getString("article"));
        }


        generate_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
                qrgEncoder = new QRGEncoder(textView.getText().toString().trim(), null, QRGContents.Type.TEXT, 512);
//                qrgEncoder.setColorBlack(Color.RED);
//                qrgEncoder.setColorWhite(Color.BLUE);
                // Getting QR-Code as Bitmap
                bitmap = qrgEncoder.getBitmap();
                // Setting Bitmap to ImageView
                qrImage.setImageBitmap(bitmap);
                // Save with location, value, bitmap returned and type of Image(JPG/PNG).
                QRGSaver qrgSaver = new QRGSaver();
                qrgSaver.save(savePath, textView.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                qrImage.setImageBitmap(bitmap);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }


}