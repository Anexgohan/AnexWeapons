@echo off
setlocal enabledelayedexpansion

::     "version": { "major":"0", "minor": "2", "patch": "9f" },, "minor": "2", "patch": "9f" },
:: Set the input and 7zip directories
set "sevenZip=C:\Program Files\7-Zip\7z.exe"
set "sourceDir=D:\Games\Space-4x\Starsector-mod-dev\mods\Anex Weapons"

:: get the name of the mod from the sourceDir
for %%i in ("!sourceDir!") do set "modName=%%~nxi"

set "mod_info-filePath=!sourceDir!\mod_info.json"

:: Search for the string and save it to a variable
for /f "delims=" %%i in ('findstr /C:"version" "%mod_info-filePath%"') do set "versionLine=%%i"
:: Display the result
:: echo !versionLine!

:: Extract the version number from the string and save it to separate variables
:: tokens define the parts of the string to extract
for /f "tokens=4,6,8 delims=:, " %%i in ("!versionLine!") do (
    set "major=%%i"
    set "minor=%%j"
    set "patch=%%k"
)
:: remove the quotes from the variables
set "vMajor=!major:"=!"
set "vMinor=!minor:"=!"
set "vPatch=!patch:"=!"
:: set a variable to the version number with the format "Anex Weapons-major.minor.patch"
set "versionFormat=!modName!-!vMajor!.!vMinor!.!vPatch!"
echo !versionFormat!

:: Set the output and extract directories using the modName
set "outputDir=D:\Games\Space-4x\Starsector-mod-dev\mods\Anex Weapons\out\zip-temp-delete-if-too-large\!versionFormat!.zip"
set "extractDir=D:\Games\Space-4x\Starsector\mods\!versionFormat!"


:: print multiple lines of text to the console\
echo _______________________________________________________________
echo.
echo This will create the zip file at: 
echo    %outputDir%
echo with the zip name %versionFormat%.zip
echo _______________________________________________________________
echo.
echo Extracting the zip file to: 
echo    %extractDir% 
echo with the folder name "%versionFormat%"
echo _______________________________________________________________
echo.
echo any folder with the name : %versionFormat% will be deleted
echo _______________________________________________________________
echo.
set /p userInput=" Press Enter to continue..."

:: delete the extractDir if it exists
if exist "%extractDir%" rd /s /q "%extractDir%"

:: use the 7zip command to create a zip file from the source directory
"%sevenZip%" a -tzip -bb1 "%outputDir%" "%sourceDir%\data" "%sourceDir%\graphics" "%sourceDir%\jars" "%sourceDir%\sounds" "%sourceDir%\src" "%sourceDir%\Anex_Weapons.version" "%sourceDir%\mod_info.json"
"%sevenZip%" x -bb0 "%outputDir%" -o"%extractDir%"
:: set the size of the zip file and the extracted directory
for %%A in ("%outputDir%") do set "size=%%~zA"
set /a "sizeMB=%size% / 1024 / 1024"
set /a "sizeRemainder=%size% %% (1024 * 1024) * 1000 / (1024 * 1024)"
echo Size of the zip file is %sizeMB%.%sizeRemainder% MB

:: set the size of the extracted directory and display it
for /r "%extractDir%" %%A in (*) do set /a "dirSize+=%%~zA"
set /a "dirSizeMB=%dirSize% / 1024 / 1024"
set /a "dirSizeRemainder=%dirSize% %% (1024 * 1024) * 1000 / (1024 * 1024)"
echo Size of the extracted directory is %dirSizeMB%.%dirSizeRemainder% MB
pause
