plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.kidapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kidapp"
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
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Glide
    implementation (libs.glide)
    annotationProcessor(libs.glide.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //GSON
    implementation (libs.gson)

}