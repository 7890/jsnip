#!/bin/sh

#//tb/1802

#this needs to be adjusted to local circumstances
PATH_TO_JNI_H="/usr/lib/jvm/java-8-openjdk-amd64/include/"
PATH_TO_JNI_MD_H="/usr/lib/jvm/java-8-openjdk-amd64/include/linux/"

echo "test header file existence 'jni.h', 'jni_md.h'"

if [ ! -f "$PATH_TO_JNI_H"/jni.h ]
then
	echo "jni.h not found"
	echo "set \$PATH_TO_JNI_H in make.sh"
	exit 1
fi
if [ ! -f "$PATH_TO_JNI_MD_H"/jni_md.h ]
then
	echo "jni_md.h not found"
	echo "set \$PATH_TO_JNI_MD_H in make.sh"
	exit 1
fi

echo "tools check"

which java >/dev/null || (echo "java not found" && return 1) || return 1
which javac >/dev/null || (echo "javac not found" && return 1) || return 1
which javah >/dev/null || (echo "javah not found" && return 1) || return 1
which cc >/dev/null || (echo "cc not found" && return 1) || return 1

#clean (previous) builds

rm -f HelloWorld.class
rm -f HelloWorld.h
rm -f libHelloWorld

echo "compiling HelloWorld.java"
javac HelloWorld.java
echo "creating HelloWorld.h"
javah -jni HelloWorld
echo "dummy implement HelloWorld.c"
#..
echo "compiling HelloWorld.c to libHelloWorld.so"
cc -o libHelloWorld.so HelloWorld.c \
	-shared -fPIC \
	-I"$PATH_TO_JNI_H" \
	-I"$PATH_TO_JNI_MD_H"
echo "running java Hello World"
echo "java -Djava.library.path=. HelloWorld"
java -Djava.library.path=. HelloWorld
echo "done."
