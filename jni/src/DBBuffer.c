#include <jni.h>
#include <stdlib.h>
#include <string.h>

#include "DBBuffer.h"

/*
//tb/1802
*/

JNIEXPORT jobject JNICALL
Java_DBBuffer_create (JNIEnv * env, jobject jcaller, jlong size)
{
	/* allocate memory */
	char *buf=malloc(size);
	memset(buf, 0, size);
	/* create and fill float array with test values */
	float fs[size/sizeof(float)];
	int i=0;
	for(i=0; i<size/sizeof(float); i++)
	{
		fs[i]=0.001f + i*0.1;
	}
	/* copy to buffer */
	memcpy(buf, &fs, size);
	/* create and (Java) ByteBuffer */
	/* (jobject bb=) */
	return (*env)->NewDirectByteBuffer(env, buf, size);
}
/*EOF*/
