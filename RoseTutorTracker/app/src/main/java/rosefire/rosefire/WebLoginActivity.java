package rosefire.rosefire;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@SuppressWarnings("unused")
public final class WebLoginActivity extends Activity {

    public static final String REGISTRY_TOKEN = "registry";
    public static final String JWT_TOKEN = "token";
    public static final String ERROR = "error";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView mLoginScreen = new WebView(this);
        this.setContentView(mLoginScreen);
        String token = getIntent().getStringExtra(REGISTRY_TOKEN);
        try {
            mLoginScreen.loadUrl("https://rosefire.csse.rose-hulman.edu/webview/login?registryToken=" + URLEncoder.encode(token, "UTF-8") + "&platform=android");
        } catch (UnsupportedEncodingException e) {
            // TODO: Also catch loading errors
            onLoginFail();
        }
        WebSettings webSettings = mLoginScreen.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mLoginScreen.addJavascriptInterface(this, "Android");
    }


    private void onLoginSuccess(String token) {
        if (Rosefire.DEBUG) Log.d("RFA", token);
        Intent data = new Intent();
        data.putExtra(JWT_TOKEN, token);
        setResult(RESULT_OK, data);
        finish();
    }

    private void onLoginFail() {
        if (Rosefire.DEBUG) Log.d("RFA", "Invalid registryToken");
        Intent data = new Intent();
        data.putExtra(ERROR, "Invalid registryToken");
        setResult(RESULT_CANCELED, data);
        finish();
    }

    @JavascriptInterface
    public void finish(String token) {
        onLoginSuccess(token);
    }

}
