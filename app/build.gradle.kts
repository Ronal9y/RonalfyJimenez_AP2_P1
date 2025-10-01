plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "edu.ucne.ronalfyjimenez_ap2_p1"
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.ucne.ronalfyjimenez_ap2_p1"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //room
    implementation("androidx.room:room-runtime:2.8.1")
    annotationProcessor("androidx.room:room-compiler:2.8.1")
    ksp("androidx.room:room-compiler:2.8.1")
    //  optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.8.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57.2")
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.9.2")
    implementation("androidx.compose.runtime:runtime:1.9.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")

    //navegacion
    implementation("androidx.navigation:navigation-compose:2.9.5")

// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

// ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

    // Iconos de Material
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    //Material3
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.4")

}