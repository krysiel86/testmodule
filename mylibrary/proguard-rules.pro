# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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


# 축소(minification) 설정
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

# 난독화(obfuscation) 설정
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# 라이브러리 보존 설정
#-keep class io.github.krysiel86.* { *; }
#-keep interface io.github.krysiel86.** { *; }

-keep class io.github.krysiel86.Krysiel {
#   public <methods>;
#       java.lang.String testA; # 이걸 넣으면 예외 다는듯

    public void krysielTest();

}

-keep class io.github.krysiel86.TestLog {

}

-keep class io.github.krysiel86.mobile.** {
    *;
}

-keep class io.github.krysiel86.mobile.api.** {
    *;
}


