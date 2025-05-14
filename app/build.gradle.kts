import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.aj.ajnews"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aj.ajnews"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        android.buildFeatures.buildConfig = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\" ")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val hiltVersion = "2.56.2"
    val hiltComposeVersion = "1.2.0"
    val roomVersion = "2.7.1"
    val lifecycleVersion = "2.9.0"

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2025.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android:1.3.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Material Essentials
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.0")

    // Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltComposeVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltComposeVersion")

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.6")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Room
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Swipe to refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.25.1")

}

kapt {
    correctErrorTypes = true
}
