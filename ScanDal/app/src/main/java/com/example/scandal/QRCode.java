package com.example.scandal;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
public class QRCode {
    /**
     * QRCode Class is used to stored a QR code in various forms
     * manages QR scanning and generation, uses Bitmap to store QR
     * images and a String for the Token stored in the QR token.
     */
    private Bitmap QRPic;
    private String QRToken;
    /**
     * Takes the current context usually will be "this" if used in MainActivity.java
     * and scans a QR code storing the value from the QR code in the attribute QR
     * which can be retrieved using the getQR method. Returns a boolean value
     * indicating whether a QR was read successfully or not
     * @param context The context that this method is being called
     * @return boolean whether a QR code was successfully read
     */
    public boolean scanCode(Context context){
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE)
                .enableAutoZoom()
                .build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(context, options);
        AtomicBoolean ret = new AtomicBoolean(false);
        scanner
                .startScan()
                .addOnSuccessListener(
                        barcode -> {
                            // Task completed successfully
                            this.QRToken = barcode.getRawValue();
                            ret.set(true);

                        })
                .addOnCanceledListener(
                        () -> {
                            // Task canceled

                        })
                .addOnFailureListener(
                        e -> {
                            // Task failed with an exception
                        });
        return ret.get();
    }

    /**
     * Takes the ImageView that the QR should be generated in and
     * makes BitMap of a QR code that encodes the token to fit in
     * the designated area and stores it in the QRPic attribute which
     * can be accessed via it's getter, as well as sets the QRToken
     * attribute to the token inputted
     * @param img a ImageView to get a reference for the size of the QR
     * @param token a String Token which will be encoded into the generated QR
     * @return boolean of whether a QR was successfully generated
     */
    public boolean generateQR(ImageView img, String token){
        Integer dim =Math.min(img.getHeight(), img.getWidth());
        MultiFormatWriter writer = new MultiFormatWriter();
        boolean ret = false;
        try {
            BitMatrix bitMatrix = writer.encode(token, BarcodeFormat.QR_CODE, dim, dim);
            BarcodeEncoder encoder = new BarcodeEncoder();
            QRPic = encoder.createBitmap(bitMatrix);
            QRToken = token;
            ret = true;
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    /**
     * Gets a QR code from an image inputted as a bitmap. Needs the
     * rotaion degrees of the image inorder to work.
     * @param bitmap a Bitmap which represents the image that contains a Bitmap
     * @param rotationDegree rotation degree of the image stored in the Bitmap
     * @return boolean whether a QR was successfully read from the image.
     */
    public boolean getQRFromImage(Bitmap bitmap, int rotationDegree) {
        InputImage image;
        BarcodeScanner scanner = BarcodeScanning.getClient();
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC)
                        .build();
        image = InputImage.fromBitmap(bitmap, rotationDegree);
        final boolean[] ret = {false};
        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        // Task completed successfully
                        Barcode qr = barcodes.get(0);
                        QRToken = qr.getRawValue();
                        ret[0] = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                    }
                });

        return ret[0];
    }


    /**
     * gets the QR's token
     * @return String token attribute
     */
    public String getQRToken() {
        return QRToken;
    }

    /**
     * gets the QR's bitmap
     * @return image stored as a Bitmap
     */
    public Bitmap getQRPic() {
        return QRPic;
    }
}
