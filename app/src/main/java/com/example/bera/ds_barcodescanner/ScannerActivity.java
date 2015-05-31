package com.example.bera.ds_barcodescanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.bera.ds_barcodescanner.model.Item;
import com.example.bera.ds_barcodescanner.model.Product;
import com.example.bera.ds_barcodescanner.util.FileLoader;
import com.google.zxing.Result;
import com.thoughtworks.xstream.XStream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private static final String TAG = ScannerActivity.class.getSimpleName();
    private ZXingScannerView mScannerView;
    private TextView bcodeTextView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scan);
        FrameLayout scanwrapper = (FrameLayout) findViewById(R.id.scan_view_wrapper);
        bcodeTextView = (TextView) findViewById(R.id.bcode_text);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        scanwrapper.addView(mScannerView);

        initItemData();
    }

    private void initItemData() {

        XStream xstream = new XStream();
        xstream.alias("product", Product.class);
        xstream.addImplicitCollection(Product.class, "item");
        String barcodeData = FileLoader.loadRawFile(this, R.raw.barcode_data).replaceAll("\t", "");
        Item item = (Item) xstream.fromXML(barcodeData);
        item.getProducts();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
//        Log.v(TAG, rawResult.getText()); // Prints scan results
//        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        boolean resultValue;
        resultValue = checkResult(rawResult);

        if(resultValue==true) {
            bcodeTextView.setText(rawResult.getText());
        } else {
            bcodeTextView.setText("no barcode");
        }
        mScannerView.startCamera();
    }

    public boolean checkResult(Result barCode) {
        String data = "123456789012";
        if (data.equals(barCode.toString())) {
            return true;
        } else {
            return false;
        }
    }
}