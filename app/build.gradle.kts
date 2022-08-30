plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = Config.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdk = Config.MIN_SDK_VERSION
        targetSdk = Config.TARGET_SDK_VERSION
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME
        testInstrumentationRunner = Config.ANDROID_TEST_INSTRUMENTATION

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    // Temporary fix until alpha10
    packagingOptions {
        resources.excludes.add ("META-INF/proguard/androidx-annotations.pro")
        resources.excludes.add ("META-INF/gradle/incremental.annotation.processors")

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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.Kotlin.COROUTINES_ANDROID)
    implementation(Dependencies.Dagger.DAGGER_HILT)
    kapt(Dependencies.Dagger.DAGGER_HILT_COMPILER)
    kapt(Dependencies.Dagger.DAGGER_HILT_COMPILER_X)
    implementation(Dependencies.Dagger.DAGGER_HILT_COMPOSE)
    implementation(Dependencies.Glide.GLIDE)
    implementation(Dependencies.Square.MOSHI)
    kapt(Dependencies.Square.MOSHI_CODEGEN)
    implementation(Dependencies.Square.MOSHI_ADAPTER)
    implementation(Dependencies.Square.MOSHI_CONVERTER)
    implementation(Dependencies.Square.RETROFIT)
    implementation(Dependencies.Square.RETROFIT_JSON)
    implementation(Dependencies.Android.MATERIAL)
    implementation(Dependencies.AndroidX.APP_COMPAT)
    implementation(Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependencies.AndroidX.DATASTORE_ANDROID)
    implementation(Dependencies.AndroidX.Ktx.CORE)
    implementation(Dependencies.AndroidX.Ktx.LIFECYCLE_ANNOTATIONS)
    implementation(Dependencies.AndroidX.Ktx.LIFECYCLE_VIEWMODEL)
  //  implementation(Dependencies.AndroidX.Ktx.LIFECYCLE_VIEWMODEL_EXTENSIONS)
    implementation(Dependencies.AndroidX.Ktx.LIFECYCLE_RUNTIME)
    implementation(Dependencies.AndroidX.Ktx.FRAGMENT)
    implementation(Dependencies.AndroidX.Ktx.Navigation.FRAGMENT)
    implementation(Dependencies.AndroidX.Ktx.Navigation.UI)

    androidTestImplementation(Dependencies.AndroidX.J_UNIT)
    androidTestImplementation(Dependencies.AndroidX.ESPRESSO)

}