#!/bin/sh

which javac || (echo "javac not found" && kill 0)
echo "compiling..."
mkdir -p _build
javac -d _build src/*.java && echo "running..." && java -cp _build TilingLayoutExample
