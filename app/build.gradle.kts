plugins {
    id("com.android.application")
}

android {
    namespace = "com.lin.voltrfremoteadaptorandroid"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.lin.voltrfremoteadaptorandroid"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.satyan:sugar:1.5")
    implementation ("com.github.mik3y:usb-serial-for-android:3.7.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.github.JessYanCoding:AndroidAutoSize:v1.2.1")
    implementation ("com.github.aicareles:Android-BLE:3.3.0")
    implementation ("cn.com.superLei:aop-arms:1.0.4")
}