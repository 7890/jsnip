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
apriv=99
_EOF_

javac -source 1.6 -target 1.6 *.java && java LPropsTest "$tmpfile"

rm -f "$tmfile"

exit
###########
values before load:
ai=23
al=24
af=78.9
ad=79.909090909
as=hallo velo
ac=@
ab=false
apriv=0

values after load:
ai=66
al=77
af=88.9
ad=99.909090909
as=foo
ac=`
ab=true
apriv=0
