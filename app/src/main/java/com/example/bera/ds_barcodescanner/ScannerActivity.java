package com.example.bera.ds_barcodescanner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
    private TextView pnameTextView;
    private Item item;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scan);
        FrameLayout scanwrapper = (FrameLayout) findViewById(R.id.scan_view_wrapper);
        bcodeTextView = (TextView) findViewById(R.id.bcode_text);
        pnameTextView = (TextView) findViewById(R.id.pname_text);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        scanwrapper.addView(mScannerView);
        bcodeTextView.setText("");
        pnameTextView.setText("");
        initItemData();
    }

    private void initItemData() {
        XStream xstream = new XStream();
        xstream.alias("root", Item.class);
        xstream.alias("product", Product.class);
        xstream.addImplicitCollection(Item.class, "products");
        String barcodeData = FileLoader.loadRawFile(this, R.raw.barcode_data).replaceAll("\t", "");
        item = (Item) xstream.fromXML(barcodeData);
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
        boolean resultValue;
        resultValue = checkResult(rawResult);

        if (resultValue == true) {
            bcodeTextView.setText(rawResult.getText());
        } else {
            bcodeTextView.setText(rawResult.getText());
            pnameTextView.setText(R.string.error_message);
        }
        mScannerView.startCamera();
    }

    public boolean checkResult(Result barCode) {
        Log.d("barcode", barCode.getText());
        if (item != null && item.getProducts() != null) {
            for (Product product : item.getProducts()) {
                Log.d("product", product.getBarcode());
                if (product.getBarcode().equals(barCode.getText())) {
                    pnameTextView.setText(product.getName());
                    return true;
                }
            }
        }
        return false;
    }
}