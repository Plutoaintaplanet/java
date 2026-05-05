@echo off
echo Compiling Java files...
javac -d . *.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo Starting Student Portal Application...
java studentmarksportal.start
pause
