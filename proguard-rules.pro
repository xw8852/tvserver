# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-sdk/tools/proguard/proguard-android.txt
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
-dontwarn org.apache.mina.**
-dontwarn org.slf4j.**
#声明第三方jar包,不用管第三方jar包中的.so文件(如果有)
#-libraryjars libs/slf4j-android-1.6.1-RC1.jar
#-libraryjars libs/mina-core-2.0.9.jar
#不混淆第三方jar包中的类
-keep class org.apache.mina.** {*;}
-keep class org.slf4j.** {*;}
-keep class android.support.v4.** {*;}
-keep class android.support.v7.** {*;}