package com.careerguide;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;import android.os.StrictMode;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.FirebaseApp;

import java.io.File;
import java.io.IOException;

import android.webkit.WebChromeClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = WebViewActivity.class.getSimpleName ( );
    private static final String AUTHORITY =
            "com.careerguide";
    private static final Uri PROVIDER =
            Uri.parse ( "content://" + AUTHORITY );
    private Activity activity = this;
    private Context mContext;
    private String pdfurl;
    PDFView pdfView;
    RelativeLayout tryAgain;

    File f1;

    WebView webView;
    Activity view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder ( );

        String pdfName = getIntent ( ).getStringExtra ( "pdfName" );
        String toolBarTitle = getIntent ( ).getStringExtra ( "toolBarTitle" );
        try {


            String filePath = "/Download/"+pdfName;
            f1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filePath);


            setContentView ( R.layout.activity_web_view );

            setTitle ( toolBarTitle );

            StrictMode.setVmPolicy ( builder.build ( ) );
            getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( true );
            //new DownloadFile ( ).execute ( pdfurl, pdfname );
            getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( true );
            FirebaseApp.initializeApp ( activity );

            //tryAgain = (RelativeLayout) findViewById ( R.id.tryAgain );
            pdfView = (PDFView) findViewById ( R.id.pdfView );
            pdfView.setSwipeVertical(true);

            pdfView.fromUri(Uri.fromFile(f1))
                    // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .load();

            if (ContextCompat.checkSelfPermission ( this, "android.Manifest.permission.READ_EXTERNAL_STORAGE" )
                    != PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions ( new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            15 );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            //tryAgain.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Try again in some time!!!",Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ( ).inflate ( R.menu.activity_webview, menu );
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId ( ) == android.R.id.home) {
            finish ( );
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }



    @Override
    protected void onPause() {
        super.onPause ( );
        Utility.handleOnlineStatus ( null, "" );
    }

    public void shareAsset(View v) {
        String pdfname = getIntent ( ).getStringExtra ( "filename" );
        Uri path = Uri.fromFile ( f1 );
        Intent shareIntent = new Intent ( );
        shareIntent.setAction ( Intent.ACTION_SEND ); // temp permission for receiving app to read this file
        shareIntent.setType ( "application/pdf" );
        shareIntent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        shareIntent.putExtra ( Intent.EXTRA_STREAM, path );
        shareIntent.addFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
        startActivity ( Intent.createChooser ( shareIntent, "Choose an app" ) );
    }


    @Override
    public void onBackPressed() {
        //  webView.loadUrl("https://docs.google.com/viewerng/viewer?url=" + /*"https://s3-ap-southeast-1.amazonaws.com/fal-careerguide/id-la/67528.pdf"*/ getIntent().getStringExtra("url"));
        super.onBackPressed ( );
//        Intent back1 = new Intent(WebViewActivity.this, ebookpdf.class);
//        back1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(back1);
//        finish();
    }


}