#include <jni.h>
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>

#include "Callback.h"
/*
http://adamish.com/blog/archives/327
http://w01fe.com/blog/2009/05/c-callbacks-into-java-via-jni-made-easyier/

//tb/1802
*/

/* cached refs for later callbacks */
JavaVM *g_vm;
jobject g_obj;
jmethodID g_mid;

/* callback thread -> calling registered Java method */
pthread_t thread1;

long counter=0;

void callback(long val);
void *thread1_function(void* arg);

JNIEXPORT void JNICALL
Java_Callback_register (JNIEnv *env, jobject jcaller)
{
	/* convert local to global reference */
	/* (local will die after this method call) */
	g_obj = (*env)->NewGlobalRef(env, jcaller);

	/* save refs for callback */
	jclass g_clazz = (*env)->GetObjectClass(env, g_obj);
	if (g_clazz == NULL)
	{
		fprintf(stderr, "Failed to find class\n");
	}

	g_mid = (*env)->GetMethodID(env, g_clazz, "callback", "(J)V");
	if (g_mid == NULL)
	{
		fprintf(stderr, "Unable to get method ref\n");
	}

	/* get a handle on vm */
	(*env)->GetJavaVM(env, &g_vm);

	fprintf(stderr, "<");
	pthread_create( &thread1, NULL, thread1_function, NULL);
	fprintf(stderr, ">");
}

void *thread1_function(void* arg)
{
	while(counter<100)
	{
		callback(counter++);
		usleep(10000);
	}
	return 0;
}

void callback(long val)
{
	JNIEnv *g_env;
	/*  double check it's all ok */ 
	int getEnvStat = (*g_vm)->GetEnv(g_vm, (void **)&g_env, JNI_VERSION_1_6);
	if (getEnvStat == JNI_EDETACHED)
	{
/*		fprintf(stderr, "GetEnv: not attached\n");*/
		if ((*g_vm)->AttachCurrentThread(g_vm, (void **) &g_env, NULL) != 0)
		{
			fprintf(stderr, "Failed to attach\n");
		}
	}
	else if (getEnvStat == JNI_OK)
	{
/*		printf("GetEnv: JNI_OK ***********\n");*/
	}
	else if (getEnvStat == JNI_EVERSION)
	{
		fprintf(stderr, "GetEnv: version not supported\n");
	}

	(*g_env)->CallVoidMethod(g_env, g_obj, g_mid, val);

	if ((*g_env)->ExceptionCheck(g_env))
	{
		(*g_env)->ExceptionDescribe(g_env);
	}

	(*g_vm)->DetachCurrentThread(g_vm);
}
/*EOF*/
