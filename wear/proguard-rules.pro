# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/CyTronix/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#General
-dontwarn javax.annotation.**

#Kotlin
-dontwarn kotlin.**
-dontwarn org.jetbrains.**
-keep class org.jetbrains.kotlin.** { *; }
-keep class org.jetbrains.annotations.** { *; }

#Crashlytics
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#Appsee
-keep class com.appsee.** { *; }
-dontwarn com.appsee.**
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keepattributes SourceFile,LineNumberTable

#Retrofit
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn retrofit2.-KotlinExtensions