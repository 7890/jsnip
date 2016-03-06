#!/bin/sh

tmpfile="`mktemp`"
cat - > "$tmpfile" << _EOF_
a=1
b=2.3
c=foo\nbar
d=false
_EOF_

javac *.java && java LPropsTest "$tmpfile"

rm -f "$tmfile"

exit
###################
values before load:
var a: 23
var b: 78.9
var c: hallo velo
var d: false

values after load:
var a: 1
var b: 2.3
var c: foo
bar
var d: false
