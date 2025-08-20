plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ushst.patchertiktok"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ushst.patchertiktok"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    compileOnly("de.robv.android.xposed:api:82")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}