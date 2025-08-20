package com.ushst.patchertiktok;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // Работать только для TikTok
        if (!lpparam.packageName.equals("com.ss.android.ugc.trill") &&
                !lpparam.packageName.equals("com.zhiliaoapp.musically")) {
            return;
        }

        // ---------- TikTok Hooks ----------
        try {
            // Всегда разрешать скачивание
            XposedHelpers.findAndHookMethod(
                    "com.ss.android.ugc.aweme.feed.model.ACLCommonShare",
                    lpparam.classLoader,
                    "getCode",
                    XC_MethodReplacement.returnConstant(0)
            );

            XposedHelpers.findAndHookMethod(
                    "com.ss.android.ugc.aweme.feed.model.ACLCommonShare",
                    lpparam.classLoader,
                    "getShowType",
                    XC_MethodReplacement.returnConstant(2)
            );

            // Убирать watermark
            XposedHelpers.findAndHookMethod(
                    "com.ss.android.ugc.aweme.feed.model.ACLCommonShare",
                    lpparam.classLoader,
                    "getTranscode",
                    XC_MethodReplacement.returnConstant(1)
            );

            // Всегда показывать seekbar
            XposedHelpers.findAndHookMethod(
                    "com.ss.android.ugc.aweme.player.sdk.api.SeekBarManager",
                    lpparam.classLoader,
                    "shouldShowSeekBar",
                    XC_MethodReplacement.returnConstant(true)
            );
        } catch (Throwable t) {
            t.printStackTrace();
        }

        // ---------- Подмена SIM ----------
        final String COUNTRY_ISO = "CO";
        final String OPERATOR = "73202";
        final String OPERATOR_NAME = "Edatel";

        hookTelephony("getSimCountryIso", COUNTRY_ISO);
        hookTelephony("getNetworkCountryIso", COUNTRY_ISO);
        hookTelephony("getSimOperator", OPERATOR);
        hookTelephony("getNetworkOperator", OPERATOR);
        hookTelephony("getSimOperatorName", OPERATOR_NAME);
        hookTelephony("getNetworkOperatorName", OPERATOR_NAME);

        // ---------- Удаление рекламы из фида ----------
        try {
            XposedHelpers.findAndHookMethod(
                    "com.ss.android.ugc.aweme.feed.api.FeedApiService",
                    lpparam.classLoader,
                    "fetchFeedList",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            Object feedList = param.getResult();
                            if (feedList == null) return;

                            try {
                                Object items = XposedHelpers.getObjectField(feedList, "items");
                                if (items instanceof List) {
                                    List<?> list = (List<?>) items;
                                    Iterator<?> it = list.iterator();
                                    while (it.hasNext()) {
                                        Object item = it.next();

                                        boolean isAd = false;
                                        try {
                                            isAd = XposedHelpers.getBooleanField(item, "isAd");
                                        } catch (Throwable ignored) {}

                                        if (isAd) {
                                            it.remove(); // убираем рекламу
                                        }
                                    }
                                }
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void hookTelephony(String methodName, final String result) {
        try {
            XposedHelpers.findAndHookMethod(
                    TelephonyManager.class,
                    methodName,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            param.setResult(result);
                        }
                    }
            );
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
