#include <jni.h>

#include "CreateObject.h"

/*
//tb/1802
*/

JNIEXPORT jobject JNICALL
Java_CreateObject_getMyClassObject (JNIEnv * env, jobject caller)
{	
	jclass cls = (*env)->FindClass(env, "CreateObject$MyClass"); /* $: inner class */
	/* get constructor for (string,long) */
	jmethodID constructor = (*env)->GetMethodID(env, cls, "<init>"
		, "(Ljava/lang/String;J)V");

	const char *str_="foo bar ¢€@éä END";
	jstring jstr_=(*env)->NewStringUTF(env, str_);

	/* create and return Java object */
	/* (jobject object=) */
	return (*env)->NewObject(env, cls, constructor
		, /*arg1*/ jstr_, /*arg2*/ 9999999999991L);
}

/*
Type Signatures

The JNI uses the Java VM’s representation of type signatures.

Java Type
Z boolean
B byte
C char
S short
I int
J long
F float
D double
L fully-qualified-class ;fully-qualified-class
[ type type[]

( arg-types ) ret-type

For example, the Java method:

	long f (int n, String s, int[] arr);

has the following type signature:

	(ILjava/lang/String;[I)J


"void(V)"
"void()V"

*/

/*EOF*/
