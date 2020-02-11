#!bin/sh

cd $(dirname $0)/..

if [ -d build ]
then
rm -r build/
fi

mkdir build/

cd src/
javac -d ../build */*.java
cp -rf ressources/ ../build
cd ..
