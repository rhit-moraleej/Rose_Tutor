package rosefire.rosefire;

import android.content.Context;
import android.content.Intent;


public final class Rosefire {

    static final boolean DEBUG = false;

    public static Intent getSignInIntent(Context context, String registryToken) {
        Intent intent = new Intent(context, WebLoginActivity.class);
        intent.putExtra(WebLoginActivity.REGISTRY_TOKEN, registryToken);
        return intent;
    }

    public static RosefireResult getSignInResultFromIntent(Intent data) {
        String token = data != null ? data.getStringExtra(WebLoginActivity.JWT_TOKEN) : null;
        return new RosefireResult(token);
    }

}
