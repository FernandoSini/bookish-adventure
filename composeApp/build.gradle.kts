import com.android.build.api.dsl.androidLibrary
import com.android.build.gradle.internal.packaging.defaultExcludes
import com.android.tools.analytics.AnalyticsSettings.disable
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.target.AppleConfigurables
import org.jetbrains.kotlin.konan.target.AppleConfigurablesImpl
import org.jetbrains.kotlin.konan.target.linker
import java.io.FileInputStream
import java.lang.System.load
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.room)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)

}


val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties().apply {
    if (keystorePropertiesFile.exists()) {
        keystorePropertiesFile.inputStream().use { load(it) }
    }
}
val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))


room {
    schemaDirectory("$projectDir/schemas")
}

/*ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}*/
kotlin {

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class) compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            javaParameters.set(true)

        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class) instrumentedTestVariant.sourceSetTree.set(
            KotlinSourceSetTree.test
        )
        @OptIn(ExperimentalKotlinGradlePluginApi::class) instrumentedTestVariant {
            dependencies {
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.ui.test.junit4)
                implementation(libs.androidx.test.junit)
                debugImplementation(libs.androidx.test.manifest)
            }
        }

    }

    tasks.withType<Test> {
        if (name == "compileDebugAndroidTestKotlinAndroid") {
            enabled = false

        }
        useJUnitPlatform()
        enabled = true

        testLogging {
            events("passed", "skipped", "failed")
        }
    }


//val xc = XCFramework()
    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        //val xc = XCFramework()
        iosTarget.binaries.framework {

            // outputDirectory = layout.buildDirectory.dir("bin/${iosTarget.name}/a/debugFramework").get().asFile
            binaryOption("bundleId", "br.com.flemis.bookishadventure.iphone.ComposeApp")
            binaryOption("bundleVersion", "1")
            binaryOption("bundleShortVersionString", "1.0.0")
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts("-ld_classic")
            linkerOpts.add("-lsqlite3")
            optimized = true
            linkerOpts.add("-framework")
            linkerOpts.add("-FirebaseCore")
            linkerOpts.add("-ldl")
            linkerOpts.add("-lz")
            linkerOpts("-PurchasesHybridCommon")
            linkerOpts("-FirebaseCore")
            //linkerOpts.add("-F/Users/fernandosini/Library/Developer/Xcode/DerivedData/iosApp-bidasoyzswsczqagzxdtxdiylwcq/SourcePackages/checkouts/firebase-ios-sdk")
            linkerOpts.add("-Xlinker -no_warn_duplicate_libraries")
            //linkerOpts("-framework", "FirebaseCore")
            // xc.add(this)

        }


    }/*val localProperties =
        rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use {
            Properties().apply {
                load(it)
            }
        }

    val apiKey = localProperties?.getProperty("API_KEY_NAME")
    if (!apiKey.isNullOrEmpty()) {
        val generateApiKeyFile by tasks.registering {
            val outputDir = layout.buildDirectory.dir("generated/source/apiKey")
            val file = outputDir.map { it.file("ApiKeys.kt") }

            inputs.property("API_KEY", apiKey)
            outputs.file(file)

            doLast {
                file.get().asFile.apply {
                    parentFile.mkdirs()
                    writeText(
                        """
                package br.com.flemis.bookishadventure

                object ApiKeys {
                    const val KEY = $apiKey
                }
                """.trimIndent()
                    )
                }
            }
        }

        tasks.named("preBuild") {
            dependsOn(generateApiKeyFile)
        }
    }*/


    sourceSets {
        named { it.lowercase().startsWith("ios") }.configureEach {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }

        androidMain.dependencies {

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(compose.uiTooling)
            implementation(libs.androidx.activity.ktx)
            implementation(libs.android.billing)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

        }

        commonTest {
            kotlin.srcDir("build/generated/source/apikey")
            resources.srcDir("src/commonTest/composeResources")

        }

        commonTest.dependencies {

            //até ser corrigido vai ficar comentado pq da unsolved reference no @test isso é só pra versoes abaixo de 2.0.0
            // implementation(kotlin("test"))
            // implementation(libs.gitlive.firebase.kotlin.crashlytics)
            defaultExcludes.contains("dev.gitlive")
            implementation(libs.kotlin.test)
            //implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(libs.assertk)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        commonMain {
            kotlin.srcDir("build/generated/source/apikey")
            resources.srcDir("src/commonMain/composeResources")
        }

        commonMain.dependencies {

            /* implementation(libs.purchases.core)
             implementation(libs.purchases.datetime)
             implementation(libs.purchases.either)
             implementation(libs.purchases.result)
              if (name == "commonTest") {
                configurations["linkDebugTestIosSimulatorArm64"].dependencies.removeAll {
                    it.group == "dev.gitlive.firebase"
                }

            } else {
                implementation(libs.gitlive.firebase.common)
                implementation(libs.gitlive.firebase.kotlin.crashlytics)
                implementation(libs.gitlive.firebase.kotlin.remote.config)
                implementation(libs.gitlive.firebase.kotlin.analytics)
            }
             *//* testImplementation(libs.koin.test)
            testImplementation(libs.koin.test.junit4)
            testImplementation(libs.koin.test.junit5)*/
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.napier)
            implementation(libs.firebase.core)
            implementation(libs.firebase.config)/*  implementation(libs.gitlive.firebase.common)
              implementation(libs.gitlive.firebase.kotlin.crashlytics)
              implementation(libs.gitlive.firebase.kotlin.remote.config)
              implementation(libs.gitlive.firebase.kotlin.analytics)*/
            implementation(libs.kmp.player)
            implementation(libs.sqlite.bundled)
            implementation(libs.room.runtime)
            implementation(libs.bignum)
            implementation(libs.chart)
            implementation(libs.compose.webview.multiplatform)
            implementation(libs.permissions.compose)
            implementation(libs.jetbrains.material3)
            //implementation(libs.jetbrains.adaptive.layout)
            //implementation(libs.jetbrains.adaptive.navigation)
            //implementation(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.jetbrains.lifecycle.runtime.compose)
            implementation(libs.jetbrains.navigation.compose)
            //implementation(libs.jetbrains.material.navigation)
            implementation(libs.coil3.coil.compose)
            implementation(libs.coil3.coil.network.ktor3)
            //implementation(libs.jetbrains.window.size)
            //implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.1")
            implementation(libs.coil.svg)
            implementation(libs.russhwolf.multiplatform.settings.no.arg)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.websockets)
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
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.permissions.compose.camera)
            implementation(libs.permissions.compose.motion)
            implementation(libs.permissions.compose.bluetooth)
            implementation(libs.permissions.compose.contacts)
            implementation(libs.permissions.compose.gallery)
            implementation(libs.permissions.compose.location)
            implementation(libs.permissions.compose.microphone)
            implementation(libs.permissions.compose.notifications)
            implementation(libs.permissions.compose.storage)
            // implementation(libs.androidx.lifecycle.runtime.compose)

        }/* val iosX64Main by getting
         val iosArm64Main by getting
         val iosSimulatorArm64Main by getting
         val iosMain by creating {
             dependsOn(commonMain.get())
             iosX64Main.dependsOn(this)
             iosArm64Main.dependsOn(this)
             iosSimulatorArm64Main.dependsOn(this)
             dependencies {
                 implementation(libs.ktor.client.darwin)

             }
         }*/

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            //implementation(libs.firebase.core)
            // implementation(libs.firebase.config)
        }
    }
}

android {
    /*lint {
        disable; "DialogFragmentCallbacksDetector"
    }*/
    testOptions {
        unitTests {
            all {
                it.exclude("**/screen/**")
            }
        }
    }
    sourceSets {
        getByName("androidTest") {
            kotlin.srcDirs("src/link")
        }
    }
    lint {
        disable.add("NullSafeMutableLiveData")
    }
    testVariants.all {
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase", module = "dev.gitlive:firebase-crashlytics"
        )
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase", module = "dev.gitlive:firebase-crashlytics-ktx"
        )
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase", module = "dev.gitlive:firebase-config"
        )
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase", module = "dev.gitlive:firebase-analytics"
        )
    }
    namespace = "br.com.flemis.bookishadventure"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applicationId = "br.com.flemis.bookishadventure"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }
    applicationVariants.all { variant ->
        variant.resValue("string", "versionName", variant.versionName)
        true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        create("release") {
            /* storeFile = file(
                 keystoreProperties.getProperty("storeFile") ?: throw GradleException("Keystore file not specified")
             )
             storePassword = keystoreProperties.getProperty("storePassword")
                 ?: throw GradleException("Keystore password not specified")
             keyAlias = keystoreProperties.getProperty("keyAlias") ?: throw GradleException("Key alias not specified")
             keyPassword =
                 keystoreProperties.getProperty("keyPassword") ?: throw GradleException("Key password not specified")*/
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            resValue("string", "apiKey", localProperties.getProperty("API_KEY_NAME"))
            // signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            //  signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
        // compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {

    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.koin.test.junit5)
    debugImplementation(compose.uiTooling)
    implementation(libs.androidx.startup.runtime)
    //implementation("com.google.firebase:firebase-common-ktx:21.0.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
// androidTestImplementation("androidx.compose.ui:ui-test-junit4-android:1.6.8")
    debugImplementation(libs.androidx.test.manifest)
//debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")
    androidTestImplementation(libs.androidx.runner)
// androidTestImplementation("androidx.test:runner:1.6.2")
    androidTestImplementation(libs.androidx.rules)
//androidTestImplementation("androidx.test:rules:1.6.1")
// Optional -- UI testing with Espresso a versao 3.6.1 da conflito
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.0")
// Optional -- UI testing with UI Automator
// androidTestImplementation("androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1")
//androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
    androidTestImplementation(libs.androidx.uiautomator)
// Optional -- UI testing with Compose
// androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation("androidx.test:core:1.1.0")
    testImplementation("org.robolectric:robolectric:4.7.3")


    /* add("kspAndroid", libs.androidx.room.compiler)
     add("kspIosSimulatorArm64", libs.androidx.room.compiler)
     add("kspIosX64", libs.androidx.room.compiler)
     add("kspIosArm64", libs.androidx.room.compiler)*/
    listOf(
        "kspAndroid",
// "kspJvm",
        "kspIosSimulatorArm64", "kspIosX64", "kspIosArm64"
    ).forEach {
        add(it, libs.room.compiler)

    }


}


/*
@Suppress("")
 val generateApiKeyFile by tasks.registering {
       val outputDir = layout.buildDirectory.dir("generated/source/apiKey").get().asFile
       val file = File(outputDir, "ApiKeys.kt")

       doLast {
           outputDir.mkdirs()
           file.writeText(
               """
       package br.com.flemis.bookishadventure

       object ApiKeys {
           const val KEY = "$apiKey"
       }
       """.trimIndent()
           )
       }

       outputs.file(file)
   }

    // isso não funciona
       // sourceSets["commonMain"].kotlin.srcDir(generateApiKeyFile.map { it.outputs.files })
   */