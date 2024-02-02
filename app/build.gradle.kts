plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.newsapp"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.newsapp"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "com.example.newsapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Deps.coreKtx)
    implementation(Deps.lifecycleRuntime)
    implementation(Deps.activityCompose)
    implementation(platform(Deps.composeBom))
    implementation(Deps.composeUI)
    implementation(Deps.composeGraphic)
    implementation(Deps.composePreview)
    implementation(Deps.material3)
    implementation(Deps.composeMaterial)

    //Room
    implementation(Deps.room)
    annotationProcessor(Deps.roomCompiler)
    kapt(Deps.roomCompiler)
    implementation(Deps.roomKtx)

    //Hilt
    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)
    implementation(Deps.hiltWork)
    kapt(Deps.hiltCompiler)
    implementation(Deps.hiltNavigation)
    implementation(Deps.workKtx)

    //Retrofit
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGson)

    //Glide
    implementation(Deps.glide)
    implementation(Deps.landscapistGlide)

    //Navigation
    implementation(Deps.navigation)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.espressoCore)
    androidTestImplementation(platform(Deps.composeBom))
    androidTestImplementation(Deps.junitExt)

    testImplementation(Deps.testCore)
    testImplementation(Deps.archCore)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.truth)
    testImplementation(Deps.mockwebserver)
    testImplementation(Deps.mockk)
    debugImplementation(Deps.uiTestManifest)
    testImplementation(Deps.hiltTesting)


    androidTestImplementation(Deps.hiltTesting)
    kaptAndroidTest(Deps.hiltCompiler)
    androidTestImplementation(Deps.junit)
    androidTestImplementation(Deps.coroutinesTest)
    androidTestImplementation(Deps.truth)
    androidTestImplementation(Deps.archCore)
    androidTestImplementation(Deps.testCoreKtx)
    androidTestImplementation(Deps.mockwebserver)
    androidTestImplementation(Deps.mockwebserver)
    androidTestImplementation(Deps.testRunner)


    //androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    //debugImplementation("androidx.compose.ui:ui-tooling")
    //debugImplementation("androidx.compose.ui:ui-test-manifest")
}