#!/bin/sh
javac -source 1.6 -target 1.6 *.java && java TextToGraphics && ls -1 out.png
