import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.room)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

room {
    schemaDirectory("$projectDir/schemas")
}

/*ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}*/
kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            javaParameters.set(true)

        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
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


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            binaryOption("bundleId", "br.com.fernandosini.bookishadventure.ComposeApp")
            binaryOption("bundleVersion", "1")
            binaryOption("bundleShortVersionString", "1.0.0")
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")

        }
    }
    val localProperties =
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
                package br.com.fernandosini.bookishadventure

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
    }


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
        }
        commonTest.dependencies {
            //até ser corrigido vai ficar comentado pq da unsolved reference no @test isso é só pra versoes abaixo de 2.0.0
            // implementation(kotlin("test"))
            implementation(libs.gitlive.firebase.kotlin.crashlytics)
            implementation(libs.kotlin.test)
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

            implementation(libs.purchases.core)
            implementation(libs.purchases.datetime)
            implementation(libs.purchases.either)
            implementation(libs.purchases.result)
            implementation(libs.firebase.core)
            implementation(libs.firebase.config)
            implementation(libs.gitlive.firebase.common)
            implementation(libs.gitlive.firebase.kotlin.crashlytics)
            implementation(libs.gitlive.firebase.kotlin.remote.config)
            implementation(libs.gitlive.firebase.kotlin.analytics)
            implementation(libs.kmp.player)
            implementation(libs.sqlite.bundled)
            implementation(libs.androidx.room.runtime)
            implementation(libs.bignum)
            implementation(libs.chart)
            implementation(libs.compose.webview.multiplatform)
            implementation(libs.permissions.compose)
            implementation(libs.jetbrains.adaptive)
            implementation(libs.adaptive.layout)
            implementation(libs.jetbrains.adaptive.navigation)
            implementation(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)
            implementation(libs.material.navigation)
            implementation(libs.coil3.coil.compose)
            implementation(libs.coil3.coil.network.ktor3)
            implementation(libs.android.window.size)
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
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

        }
        val iosX64Main by getting
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
        }
    }
}

android {
    testVariants.all {
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase",
            module = "dev.gitlive:firebase-crashlytics"
        )
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase",
            module = "dev.gitlive:firebase-crashlytics-ktx"
        )
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase",
            module = "dev.gitlive:firebase-config"
        )
        compileConfiguration.exclude(
            group = "dev.gitlive.firebase",
            module = "dev.gitlive:firebase-analytics"
        )
    }
    namespace = "br.com.fernandosini.bookishadventure"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applicationId = "br.com.fernandosini.bookishadventure"
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
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
    debugImplementation(compose.uiTooling)
    implementation(libs.androidx.startup.runtime)
    //implementation("com.google.firebase:firebase-common-ktx:21.0.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")
// androidTestImplementation("androidx.compose.ui:ui-test-junit4-android:1.6.8")
    debugImplementation(libs.androidx.test.manifest)
//debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")
//androidTestImplementation(libs.androidx.runner)
// androidTestImplementation("androidx.test:runner:1.6.2")
    androidTestImplementation(libs.androidx.rules)
//androidTestImplementation("androidx.test:rules:1.6.1")
// Optional -- UI testing with Espresso
// androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
// Optional -- UI testing with UI Automator
// androidTestImplementation("androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1")
//androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
    androidTestImplementation(libs.androidx.uiautomator)
// Optional -- UI testing with Compose
// androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    androidTestImplementation(libs.androidx.ui.test.junit4)


    /* add("kspAndroid", libs.androidx.room.compiler)
     add("kspIosSimulatorArm64", libs.androidx.room.compiler)
     add("kspIosX64", libs.androidx.room.compiler)
     add("kspIosArm64", libs.androidx.room.compiler)*/
    listOf(
        "kspAndroid",
// "kspJvm",
        "kspIosSimulatorArm64",
        "kspIosX64",
        "kspIosArm64"
    ).forEach {
        add(it, libs.androidx.room.compiler)
    }


}


/*   val generateApiKeyFile by tasks.registering {
       val outputDir = layout.buildDirectory.dir("generated/source/apiKey").get().asFile
       val file = File(outputDir, "ApiKeys.kt")

       doLast {
           outputDir.mkdirs()
           file.writeText(
               """
       package br.com.fernandosini.bookishadventure

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