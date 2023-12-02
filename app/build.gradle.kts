plugins {
    id("com.android.application")
    //Google Services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.codensecurity.trojanhorse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codensecurity.trojanhorse"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //Importing the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    //Add dependencies for Firebase products to use
    //Do not specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    //Volley library
    implementation ("com.android.volley:volley:1.2.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //Background WorkerManager
    implementation ("androidx.work:work-runtime:2.8.1")
    //The ListenableFuture class from the Google Guava library
    implementation ("com.google.guava:guava:30.1-android")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("androidx.navigation:navigation-ui:2.7.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}