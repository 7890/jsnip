#include <jni.h>
#include <stdio.h>
#include "HelloWorld.h"

//implement header manually
  
JNIEXPORT void JNICALL 
Java_HelloWorld_print(JNIEnv *env, jobject obj)
{
	printf("Hello World!\n");
	return;
}
//EOF