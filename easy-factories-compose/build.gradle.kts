plugins {
    plugin(Plugins.Android.Library)
    plugin(Plugins.Kotlin.Android)
    plugin(Plugins.Dokka)
    `publishing-config`
}

android {
    compileSdk = AndroidSdkVersion.Compile

    defaultConfig {
        minSdk = AndroidSdkVersion.Min
        targetSdk = AndroidSdkVersion.Target
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.CompilerVersion
    }
}

dependencies {
    api(project(":easy-factories"))
    api(Libs.AndroidX.Compose.Ui)
    api(Libs.AndroidX.Lifecycle.ViewModel.Ktx)
    api(Libs.AndroidX.Lifecycle.ViewModel.Compose)
    api(Libs.AndroidX.Lifecycle.ViewModel.SavedState)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val sourcesJar by tasks.registering(Jar::class) {
    from((android.sourceSets["main"].kotlin as com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet).srcDirs)
    archiveClassifier.set("sources")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("easyFactoriesCompose") {
                from(components["release"])
                artifact(sourcesJar)
                artifact(javadocJar)
            }
        }
    }
}