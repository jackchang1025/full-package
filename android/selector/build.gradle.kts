plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting
        val jvmMain by getting
        val androidMain by getting {
            dependsOn(jvmMain)
        }
    }
}

android {
    namespace = "li.songe.selector"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<Test> {
    enabled = false
}
