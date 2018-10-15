package com.example.android.myxkcdcomics.ui.searchfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.myxkcdcomics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();

    @BindView(R.id.search_webview)
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        webView = new WebView(getContext());

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);

        webView.setWebViewClient(new InnerWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("https://relevantxkcd.appspot.com/");

        return rootView;
    }

    private class InnerWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("https://relevantxkcd.appspot.com/")) {
                return false;
            }

            Log.d(TAG, "Taken url from web: " + url);
            // Show the loaded url into the web view
            webView.loadUrl(url);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            /*webView.loadUrl("javascript:(function() { " +
                    "document.getElementsByTagName('comic')[0].style.display='none';" +
                    "})()");*/
        }
    }
}


