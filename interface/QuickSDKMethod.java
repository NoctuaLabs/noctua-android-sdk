package com.noctua;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAdRevenue;
import com.adjust.sdk.AdjustConfig;
import com.quickgame.android.sdk.QuickGameManager;
import com.quickgame.android.sdk.bean.QGOrderInfo;
import com.quickgame.android.sdk.bean.QGRoleInfo;
import com.quickgame.android.sdk.bean.QGUserData;
import com.quickgame.android.sdk.model.QGUserHolder;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

public class QuickSDKMethod implements QuickSDKInterface {

    private MainActivity _activityContent;
    private QuickGameManager sdkInstance;
    private String TAG = "MainActivity";

    @Override
    public void Init(MainActivity activity) {
        _activityContent = activity;
        SampleSDKCallback sdkCallback = new SampleSDKCallback();
        sdkInstance = QuickGameManager.getInstance();

        String productCode = "your_product_code";
        sdkInstance.init(_activityContent, productCode, sdkCallback);
        sdkInstance.onCreate(_activityContent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        sdkInstance.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        sdkInstance.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onStart() {
        sdkInstance.onStart(_activityContent);
    }

    @Override
    public void onPause() {
        sdkInstance.onPause(_activityContent);
    }

    @Override
    public void onResume() {
        sdkInstance.onResume(_activityContent);
    }

    @Override
    public void onNewIntent(Intent newIntent) {}

    @Override
    public void onStop() {
        sdkInstance.onStop(_activityContent);
    }

    @Override
    public void onDestroy() {
        sdkInstance.onDestroy(_activityContent);
    }

    @Override
    public void onRestart() {}

    @Override
    public void silentLogin() {
        sdkInstance.freeLogin(_activityContent);
    }

    @Override
    public void googlePay(String data) {
        try {
            JSONObject jo = new JSONObject(data);
            SamplePaymentCallback paymentCallback = new SamplePaymentCallback();

            QGOrderInfo orderInfo = new QGOrderInfo();
            orderInfo.setOrderSubject(jo.getString("productName"));
            orderInfo.setProductOrderId("diamond.10");
            orderInfo.setAmount(0.99);
            orderInfo.setSuggestCurrency("USD");
            orderInfo.setGoodsId(jo.getString("goodsID"));
            orderInfo.setExtrasParams("com.noctua.diamond.100#serverid#uid#Android");

            QGRoleInfo roleInfo = new QGRoleInfo();
            roleInfo.setRoleId("hyperCasualApp");
            roleInfo.setRoleLevel("1");
            roleInfo.setRoleName("Noctua");
            roleInfo.setServerName("NoctuaGameServer");
            roleInfo.setVipLevel("1");

            sdkInstance.pay(_activityContent, orderInfo, roleInfo, paymentCallback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openSpecialButton(String data) {
        try {
            JSONObject jsonData = new JSONObject(data);
            String accountId = jsonData.getString("accountID");
            String serverID = jsonData.getString("serverID");
            sdkInstance.openSpecialButton(_activityContent, accountId, serverID);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void adjustAdRevenue(String data) {
        try {
            JSONObject jsonData = new JSONObject(data);

            Double adRevenue = jsonData.getDouble("adRevenue");
            String networkName = jsonData.getString("networkName");
            String adUnitId = jsonData.getString("adUnitId");
            String adPlacement = jsonData.getString("adPlacement");

            AdjustAdRevenue adjustAdRevenue = new AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB);
            adjustAdRevenue.setRevenue(adRevenue, "USD");
            adjustAdRevenue.setAdRevenueNetwork(networkName);
            adjustAdRevenue.setAdRevenueUnit(adUnitId);
            adjustAdRevenue.setAdRevenuePlacement(adPlacement);
            Adjust.trackAdRevenue( adjustAdRevenue);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void trackEvent(String eventName, Bundle parameters) {
        if (sdkInstance != null) {
            sdkInstance.trackEvent(eventName, parameters);
        } else {
            Log.d("NOCTUADEBUG", "sdkInstance is not available");
        }
    }

    @Override
    public void trackPurchaseEvent(String orderId, double purchaseAmount, String currency, Bundle parameters) {
        sdkInstance.trackPurchaseEvent(orderId, purchaseAmount, currency, parameters);
    }

    private class SampleSDKCallback implements QuickGameManager.SDKCallback {

        @Override
        public void onLoginFinished(QGUserData userData, QGUserHolder loginState) {
            if (loginState.getStateCode() == QGUserHolder.LOGIN_SUCCESS) {

                // Unified tracker
                sdkInstance.trackEvent("login_success");

                String uid = userData.getUid();
                String token = userData.getToken();

                JSONObject jo = new JSONObject();
                try {
                    jo.putOpt("uid",uid);
                    jo.putOpt("token",token);
                    UnityPlayer.UnitySendMessage("Hypercasual","onLoginSuccess", jo.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                UnityPlayer.UnitySendMessage("Hypercasual","OnLoginFailed","login fail");
            }
        }

        @Override
        public void onInitFinished(boolean isSuccess,String msg) {
            if (isSuccess) {
                UnityPlayer.UnitySendMessage("Hypercasual","onInitSDK","Success");
            } else {
                UnityPlayer.UnitySendMessage("Hypercasual","onInitSDK","Failed");
            }
        }

        @Override
        public void onLogout() {
//            _activityContent.runOnUiThread(() -> {
//                UnityPlayer.UnitySendMessage("JMessage","DoLogout","logout");
//            });
        }

        @Override
        public void onGooglePlaySub(String goodsId, String sdkOrder, boolean isAutoRenewing, boolean isAcknowledged) {
            Log.d(TAG, "goodsId=" + goodsId + "&&sdkOrder=" + sdkOrder);
            UnityPlayer.UnitySendMessage("Hypercasual","onInitSDK","Failed");
        }
    }

    private class SamplePaymentCallback implements QuickGameManager.QGPaymentCallback {

        @Override
        public void onPaySuccess(String orderId, String orderNo, String goodsId, String extraParams) {
            Toast.makeText(_activityContent, "pay success", Toast.LENGTH_LONG).show();
            // Unified tracker
            sdkInstance.trackPurchaseEvent(orderId, 0.99, "USD");

            UnityPlayer.UnitySendMessage("Hypercasual","onPaySuccess", "Success Pay");

        }

        @Override
        public void onPayFailed(String orderId, String orderNo, String errorMessage) {
            Toast.makeText(_activityContent, "pay fail:" + errorMessage, Toast.LENGTH_LONG).show();
            UnityPlayer.UnitySendMessage("Hypercasual","onPayFailed", errorMessage);
        }

        @Override
        public void onPayCancel(String orderId, String orderNo, String errorMessage) {
            Toast.makeText(_activityContent, "pay cancel", Toast.LENGTH_LONG).show();
            UnityPlayer.UnitySendMessage("Hypercasual","onPayCancel","pay cancel");
        }
    }
}
