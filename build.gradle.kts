// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.android.library") version "8.1.4" apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}