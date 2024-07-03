package com.noctua;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends UnityPlayerActivity {

    private QuickSDKInterface _sdkcom;
    private String _productCode;
    private static MainActivity _instance;

    public static void doInit(QuickSDKInterface isd, String productCode) {
        _instance._sdkcom = isd;
        _instance._productCode = productCode;
        if (isd != null) {
            isd.Init(_instance, productCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _instance = this;
        super.onCreate(savedInstanceState);

        doInit(new QuickSDKMethod(), _productCode);
    }

    public static void doSilentLogin() {
        if (_instance._sdkcom != null) {
            _instance._sdkcom.silentLogin();
        }
    }

    public static void doGooglePay(String data) {
        if (_instance._sdkcom != null) {
            _instance._sdkcom.googlePay(data);
        }
    }

    public static void doOpenSpecialButton(String data) {
        if (_instance._sdkcom != null) {
            _instance._sdkcom.openSpecialButton(data);
        }
    }

    public static void doAdjustAdRevenue(String data) {
        if (_instance._sdkcom != null) {
            _instance._sdkcom.adjustAdRevenue(data);
        }
    }

    public static void doTrackEvent(String eventName, Bundle parameters) {
        if (_instance._sdkcom != null) {
            _instance._sdkcom.trackEvent(eventName, parameters);
        }
    }

    public static void doTrackPurchaseEvent(String orderId, double purchaseAmount, String currency, Bundle parameters) {
        if (_instance._sdkcom != null) {
            _instance._sdkcom.trackPurchaseEvent(orderId, purchaseAmount, currency, parameters);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (_sdkcom != null) {
            _sdkcom.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (_sdkcom != null) {
            _sdkcom.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onStart() {
        if (_sdkcom != null) {
            _sdkcom.onStart();
        }
        super.onStart();
    }

    public void onPause() {
        if (_sdkcom != null) {
            _sdkcom.onPause();
        }
        super.onPause();
    }

    public void onResume() {
        if (_sdkcom != null) {
            _sdkcom.onResume();
        }
        super.onResume();
    }

    public void onNewIntent(Intent newIntent) {
        if (_sdkcom != null) {
            _sdkcom.onNewIntent(newIntent);
        }
        super.onNewIntent(newIntent);
    }

    public void onStop() {

        if (_sdkcom != null) {
            _sdkcom.onStop();
        }
        super.onStop();
    }

    public void onDestroy() {
        if (_sdkcom != null) {
            _sdkcom.onDestroy();
        }
        super.onDestroy();
    }

    public void onRestart() {
        if (_sdkcom != null) {
            _sdkcom.onRestart();
        }
        super.onRestart();
    }
}
