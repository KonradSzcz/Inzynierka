plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.crypto.inzynierka"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.crypto.inzynierka"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Kotlin


    implementation ("com.android.volley:volley:1.2.1")

    val activity_version = "1.9.0"
    implementation("androidx.activity:activity-ktx:$activity_version")

    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    implementation ("androidx.sqlite:sqlite:2.1.0")
    implementation("androidx.core:core-ktx:1.13.1")

    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.fragment:fragment-ktx:1.3.4")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}