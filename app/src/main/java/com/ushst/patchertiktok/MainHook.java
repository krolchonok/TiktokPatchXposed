package com.ushst.patchertiktok;

import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class MainHook implements IXposedHookLoadPackage {
    private static final String TAG = "Xposed-TikTokPatcher";
    private static final String PKG_TIKTOK_1 = "com.ss.android.ugc.trill";
    private static final String PKG_TIKTOK_2 = "com.zhiliaoapp.musically";
    private static final String PREFS_NAME = "tiktok_patcher_prefs";
    private static final String KEY_TOAST_SHOWN = "toast_shown";

    // SIM spoof
    private static final String COUNTRY_ISO = "CO";
    private static final String OPERATOR = "73202";
    private static final String OPERATOR_NAME = "Edatel";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!PKG_TIKTOK_1.equals(lpparam.packageName) && !PKG_TIKTOK_2.equals(lpparam.packageName)) return;
        XposedBridge.log(TAG + ": loaded for " + lpparam.packageName);

        installVideoPatches(lpparam);
        installSimSpoof();
        installGoogleLoginFix(lpparam);
        showToastOnAppStart(lpparam);
    }

    private void installVideoPatches(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            hookReturnConst("com.ss.android.ugc.aweme.feed.model.ACLCommonShare", lpparam.classLoader, "getCode", 0);
            hookReturnConst("com.ss.android.ugc.aweme.feed.model.ACLCommonShare", lpparam.classLoader, "getShowType", 2);
            hookReturnConst("com.ss.android.ugc.aweme.feed.model.ACLCommonShare", lpparam.classLoader, "getTranscode", 1);
            XposedBridge.log(TAG + ": video/ui patches installed");
        } catch (Throwable t) {
            XposedBridge.log(TAG + " [video patches] " + t);
        }
    }

    private void hookReturnConst(String cls, ClassLoader cl, String method, Object value) {
        try {
            XposedHelpers.findAndHookMethod(cls, cl, method, XC_MethodReplacement.returnConstant(value));
        } catch (Throwable t) {
            XposedBridge.log(TAG + " hookReturnConst failed for " + cls + "#" + method + ": " + t);
        }
    }

    // ---------------- Подмена SIM ----------------
    private void installSimSpoof() {
        hookTelephony("getSimCountryIso", COUNTRY_ISO);
        hookTelephony("getNetworkCountryIso", COUNTRY_ISO);
        hookTelephony("getSimOperator", OPERATOR);
        hookTelephony("getNetworkOperator", OPERATOR);
        hookTelephony("getSimOperatorName", OPERATOR_NAME);
        hookTelephony("getNetworkOperatorName", OPERATOR_NAME);
        XposedBridge.log(TAG + ": SIM spoof installed");
    }

    private void hookTelephony(String methodName, final String result) {
        try {
            XposedHelpers.findAndHookMethod(
                    TelephonyManager.class,
                    methodName,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            param.setResult(result);
                        }
                    }
            );
        } catch (Throwable t) {
            XposedBridge.log(TAG + " [telephony:" + methodName + "] " + t);
        }
    }

    private void showToastOnAppStart(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                    "android.app.Application",
                    lpparam.classLoader,
                    "attach",
                    android.content.Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            android.content.Context ctx = (android.content.Context) param.args[0];

                            // читаем локальные prefs целевого приложения
                            android.content.SharedPreferences prefs =
                                    ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);

                            boolean alreadyShown = prefs.getBoolean(KEY_TOAST_SHOWN, false);
                            if (!alreadyShown) {
                                Toast.makeText(ctx, "with love by @ushastoe", Toast.LENGTH_LONG).show();
                                prefs.edit().putBoolean(KEY_TOAST_SHOWN, true).apply();
                                XposedBridge.log(TAG + ": toast shown on first app start");
                            } else {
                                XposedBridge.log(TAG + ": toast suppressed (already shown before)");
                            }
                        }
                    }
            );
        } catch (Throwable t) {
            XposedBridge.log(TAG + " [showToastOnAppStart] " + t);
        }
    }

    private void installGoogleLoginFix(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            forceBooleanNoArgMethods("com.bytedance.lobby.google.GoogleAuth", lpparam.classLoader, false);
            forceBooleanNoArgMethods("com.bytedance.lobby.google.GoogleOneTapAuth", lpparam.classLoader, false);
            XposedBridge.log(TAG + ": Google login fix installed");
        } catch (Throwable t) {
            XposedBridge.log(TAG + " [google login fix] " + t);
        }
    }

    private void forceBooleanNoArgMethods(String className, ClassLoader cl, boolean returnValue) {
        try {
            Class<?> cls = XposedHelpers.findClassIfExists(className, cl);
            if (cls == null) { XposedBridge.log(TAG + ": class not found " + className); return; }
            for (Method m : cls.getDeclaredMethods()) {
                if (m.getParameterTypes().length == 0
                        && m.getReturnType() == boolean.class
                        && Modifier.isFinal(m.getModifiers())
                        && Modifier.isPublic(m.getModifiers())) {

                    XposedBridge.hookMethod(m, XC_MethodReplacement.returnConstant(returnValue));
                    XposedBridge.log(TAG + ": hooked " + className + "#" + m.getName() + "() -> " + returnValue);
                }
            }
        } catch (Throwable t) {
            XposedBridge.log(TAG + " [forceBooleanNoArgMethods " + className + "] " + t);
        }
    }
}
