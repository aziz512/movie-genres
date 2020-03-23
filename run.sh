classPath="./src:./lib/*"
javac -cp "$classPath" src/Main.java
java -cp "$classPath" Main
open dist/index.html