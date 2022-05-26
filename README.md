# CursorHelper

A tool to help you move your mouse accurately. It contains a mouse coordinate tracker, a mouse mover, and a window selector to determine the exact dimensions of an open window.

## Building

This project makes use of native code. To compile in Windows, first install Cygwin. Install mingw64-x86_64-gcc-g++ and launch the Cygwin terminal.

Then, run the following command to assemble the C++ code (ensure JDK is the main path):

```
x86_64-w64-mingw32-g++ -c -I%JAVA_HOME%\include -I%JAVA_HOME%\include\win32 -I"src\cpp" "src\cpp\com_thistestuser_cursorhelper_MainScreen.cpp" -o "com_thistestuser_cursorhelper_MainScreen.o"
```
Finally, create a dll file:

```
x86_64-w64-mingw32-g++ -shared -o "src\lib\mouseutils.dll" "com_thistestuser_cursorhelper_MainScreen.o" -Wl,--add-stdcall-alias
```

For details on how to compile for other operating systems, see the tutorial at [Baeldung](https://www.baeldung.com/jni).
