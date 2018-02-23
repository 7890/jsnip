#include <jni.h>
#include <stdlib.h>
#include <string.h>

/*
https://www.developer.com/java/data/how-to-create-a-jvm-instance-in-jni.html

//tb/1802 

cc -o start_jvm start_jvm.c \
	-I/usr/lib/jvm/java-7-openjdk-amd64/include/ \
	-I/usr/lib/jvm/java-7-openjdk-amd64/include/linux/ \
	-L/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/amd64/server/ \
	-ljvm

javac Demo.java -d .

LD_LIBRARY_PATH=/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/amd64/server/ \
./start_jvm

*/

int main(int argc, char **argv)
{
	JavaVM *javaVM;
	JNIEnv *env;

	JavaVMOption jvmopt[1];
	jvmopt[0].optionString = "-Djava.class.path=.";

	JavaVMInitArgs vmArgs;
	vmArgs.version = JNI_VERSION_1_2;
	vmArgs.nOptions = 1;
	vmArgs.options = jvmopt;
	vmArgs.ignoreUnrecognized = JNI_TRUE;

	// Create the JVM
	long flag = JNI_CreateJavaVM(&javaVM, (void**)&env, &vmArgs);

	if (flag == JNI_ERR)
	{
		fprintf(stderr,"Error creating VM. Exiting...\n");
		return 1;
	}

	jclass jcls = (*env)->FindClass(env, "org/jnijvm/Demo");
	if (jcls == NULL)
	{
		(*env)->ExceptionDescribe(env);
		(*javaVM)->DestroyJavaVM(javaVM);
		return 1;
	}
	if (jcls != NULL)
	{
		jmethodID methodId = (*env)->GetStaticMethodID(env, jcls,
			"greet", "(Ljava/lang/String;)V");
		if (methodId != NULL)
		{
			const char *str_="foo bar";
			jstring str = (*env)->NewStringUTF(env, str_);

			int i=0;
			for(i=0; i<1000; i++)
			{
				(*env)->CallStaticVoidMethod(env, jcls, methodId, str);
				if ((*env)->ExceptionCheck(env))
				{
					(*env)->ExceptionDescribe(env);
					(*env)->ExceptionClear(env);
				}
			}
		}
	}

	(*javaVM)->DestroyJavaVM(javaVM);
	return 0;
}/* end main() */
/*EOF*/
