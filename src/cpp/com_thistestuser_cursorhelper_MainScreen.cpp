#include "com_thistestuser_cursorhelper_MainScreen.h"
#include <Windows.h>

/*
 * Class:     com_thistestuser_cursorhelper_MainScreen
 * Method:    setMouseLocation
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_thistestuser_cursorhelper_MainScreen_setMouseLocation(JNIEnv* env, jclass clazz, jint x, jint y) {
	  return SetCursorPos(x, y);
}

/*
 * Class:     com_thistestuser_cursorhelper_MainScreen
 * Method:    getMouseLocation
 * Signature: ()Ljava/awt/Point;
 */
JNIEXPORT jobject JNICALL Java_com_thistestuser_cursorhelper_MainScreen_getMouseLocation(JNIEnv* env, jclass clazz) {
	  POINT point;
	  int x = -1;
	  int y = -1;
      if (GetCursorPos(&point)) {
		  x = point.x;
		  y = point.y;
	  }
	  jclass pointClass = env->FindClass("java/awt/Point");
	  jmethodID constructor = env->GetMethodID(pointClass, "<init>", "(II)V");
	  jobject pointObj = env->NewObject(pointClass, constructor, x, y);
	  return pointObj;
}

/*
 * Class:     com_thistestuser_cursorhelper_MainScreen
 * Method:    setupDPI
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_thistestuser_cursorhelper_MainScreen_setupDPI(JNIEnv* env, jclass clazz) {
	  SetProcessDPIAware();
}
