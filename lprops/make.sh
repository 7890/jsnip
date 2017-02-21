#!/bin/sh

tmpfile="`mktemp`"
cat - > "$tmpfile" << _EOF_
ai=66
al=77
af=88.9
ad=99.909090909
as=foo
ac=\`
ab=true
av=hutse,fluts
apriv=99
lprops_pre_load_dump=a label for a setting-driven (lprops_pre_load_dump=) pre-load config dump
lprops_post_load_dump=a label for a setting-driven (lprops_post_load_dump=) post-load config dump
_EOF_

javac -source 1.6 -target 1.6 *.java

echo "calling LPropsTest with properties file $tmpfile ..."
cat "$tmpfile"

java LPropsTest "$tmpfile"

rm -f "$tmfile"

exit
