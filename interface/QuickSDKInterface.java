package com.noctua;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface QuickSDKInterface {

    void Init(MainActivity activity, String productCode);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    void onStart();
    void onPause();
    void onResume();
    void onNewIntent(Intent newIntent);
    void onStop();
    void onDestroy();
    void onRestart();

    // Unified Tracker
    void trackEvent(String eventName, Bundle parameters);
    void trackPurchaseEvent(String orderId, double purchaseAmount, String currency, Bundle parameters);

    //Basic Integration
    void silentLogin();
    void googlePay(String data);
    void openSpecialButton(String data);

    //Adjust Integration
    void adjustAdRevenue(String data);
}
