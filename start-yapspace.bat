
@echo off
title YapSpace Launcher
cd /d "%~dp0"

echo ==========================
echo       YAPSPACE
echo ==========================
echo.
echo Compiling application...

if not exist "out" mkdir out

for /r "src" %%f in (*.java) do (
    if not "%%~nxf"=="TestDatabase.java" if not "%%~nxf"=="TestPostRepository.java" if not "%%~nxf"=="TestUserLogin.java" if not "%%~nxf"=="TestUserRegistration.java" if not "%%~nxf"=="TestUserRepository.java" (
        echo %%f
    )
)

javac -cp "lib/*" -d out src\yapspace\Main.java src\yapspace\exception\*.java src\yapspace\model\*.java src\yapspace\service\*.java src\yapspace\storage\*.java src\yapspace\repository\*.java src\yapspace\web\*.java src\database\*.java

if errorlevel 1 (
    echo.
    echo Compilation failed. Check the errors above.
    pause
    exit /b 1
)

echo.
echo Starting YapSpace...
echo Open http://localhost:8080 in your browser.
echo.

java -cp "out;lib/*" yapspace.Main

pause
