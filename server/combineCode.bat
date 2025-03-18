@echo off
setlocal enabledelayedexpansion

:: Define directory (change as needed)
set "JAVA_DIR=C:\Git\Numerosity\server\src\main\java"
cd /d "%JAVA_DIR%"

:: Define output file
echo. > all_java_code.txt

:: Loop through all Java files in the directory and subdirectories
for /r %%F in (*.java) do (
    echo ==== File: %%F ==== >> all_java_code.txt
    echo. >> all_java_code.txt
    type "%%F" >> all_java_code.txt
    echo. >> all_java_code.txt
    echo ====================== >> all_java_code.txt
    echo. >> all_java_code.txt
)

echo Java files have been combined into all_java_code.txt
pause
