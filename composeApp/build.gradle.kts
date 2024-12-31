import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            binaryOption("bundleId", "br.com.playtips.ComposeApp")
            binaryOption("bundleVersion", "1")
            binaryOption("bundleShortVersionString", "1.0.0")
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(compose.uiTooling)
            implementation(libs.androidx.activity.ktx)
        }
        commonMain.dependencies {
            implementation("com.ionspin.kotlin:bignum:0.3.9")
            implementation("io.github.thechance101:chart:Beta-0.0.5")
            implementation("io.github.kevinnzou:compose-webview-multiplatform:1.9.40")
            implementation(libs.permissions.compose)
            implementation(libs.jetbrains.adaptive)
            implementation(libs.adaptive.layout)
            implementation(libs.jetbrains.adaptive.navigation)
            implementation(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)
            implementation("org.jetbrains.compose.material:material-navigation:1.7.0-beta02")
            implementation(libs.coil3.coil.compose)
            implementation(libs.coil3.coil.network.ktor3)
            implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.1")
            implementation(libs.coil.svg)
            implementation(libs.russhwolf.multiplatform.settings.no.arg)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.datetime)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}

android {
    namespace = "br.com.fernandosini.bookishadventure"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "br.com.fernandosini.bookishadventure"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

