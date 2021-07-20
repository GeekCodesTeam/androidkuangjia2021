#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_fosung_lighthouse_test_JNIUtils_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_libbase_nativendk_JNIUtils2_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
