plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.yakovleva_zd2_v8"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.yakovleva_zd2_v8"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        kapt {
            arguments {arg("room.schemaLocation", "$projectDir/schemas")}
        }
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation ("androidx.room:room-runtime:2.5.0")
    implementation(libs.activity)
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")
    implementation ("com.google.code.gson:gson:2.9.0")
}