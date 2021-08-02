#your dex.loader patterns here
-keep class com.fosung.xuanchuanlan.common.app.ConfApplication {
    <init>(...);
    void attachBaseContext(android.content.Context);
}

-keepclasseswithmembernames class android.app.Application
-keep class * extends android.app.Application {
     <init>();
     void attachBaseContext(android.content.Context);
}

#如果有引用vmultidex包可以添加下面这行
-keep class android.support.multidex.MultiDex


