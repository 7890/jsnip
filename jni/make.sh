#!/bin/bash

#//tb/1802

#this needs to be adjusted to local circumstances
PATH_TO_JNI_H="/usr/lib/jvm/java-8-openjdk-amd64/include/"
PATH_TO_JNI_MD_H="/usr/lib/jvm/java-8-openjdk-amd64/include/linux/"
LIB_EXTENSION="so"
FLAGS="-shared -fPIC"
COMPILER=cc

echo "$OSTYPE"|grep -i darwin
ret=$?
if [ $ret -eq 0 ]
then
echo "found mac os"
PATH_TO_JNI_H="/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers/"
PATH_TO_JNI_MD_H="/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers/"
LIB_EXTENSION="dylib"
FLAGS="-dynamiclib"
fi

LINK_FLAGS=
LIBRARY_PATH="."

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

function compile_part
{
	NAME="$1"

	echo "compiling ${NAME}.java"
	javac ${NAME}.java
	echo "creating ${NAME}.h"
	javah -jni ${NAME}
	echo "dummy implement ${NAME}.c"
#..
	echo "compiling ${NAME}.c to lib${NAME}.${LIB_EXTENSION}"
	${COMPILER} ${NAME}.c \
		-o lib${NAME}.${LIB_EXTENSION} \
		${FLAGS} \
		-I"$PATH_TO_JNI_H" \
		-I"$PATH_TO_JNI_MD_H" \
		$LINK_FLAGS
	echo "= running java ${NAME}"
	echo "java -Djava.library.path=$LIBRARY_PATH ${NAME}"
	java -Djava.library.path="$LIBRARY_PATH" -cp . ${NAME}
	echo "done."
}

function clean_part
{
	NAME="$1"
	#clean (previous) builds
	rm -f ${NAME}.class
	rm -f ${NAME}.h
	rm -f lib${NAME}.${LIB_EXTENSION}
}

function clean
{
	echo "cleaning"
	clean_part HelloWorld
	clean_part DBBuffer
	clean_part CreateObject
	rm -f "CreateObject\$MyClass.class"
	rm -f "CreateObject_MyClass.h"
}

function compile
{
	compile_part HelloWorld
	compile_part DBBuffer
	compile_part CreateObject
}

compile
#clean

#EOF
