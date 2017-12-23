package ru.itis.android.books.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.itis.android.books.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String URL = "url";

    private WebView webView;

    public static Intent makeInflatedIntent(Context context, String url) {
        Intent intent = makeIntent(context);
        intent.putExtra(URL, url);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, WebViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.web_view);

        //Enable JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        //Set Render Priority To High
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String url = getIntent().getStringExtra(URL);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
