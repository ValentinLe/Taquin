#!bin/sh

cd $(dirname $0)/..

if [ -d build ]
then
rm -rf build/
fi

mkdir build/

cd src/
javac -d ../build */*.java
cp -rf ressources/ ../build/

