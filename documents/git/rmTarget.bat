@echo off
setlocal enabledelayedexpansion
:: delete target folder
for /f "delims=" %%i in ('dir %~dp0 /b') do (
  if exist "%~dp0%%i\" (
    if exist %~dp0%%i\target\ (
      rmdir /S /Q %~dp0%%i\target\
    )
  )
)

goto end

:exit
echo.
echo program end with error.
exit /b 1

:end
echo.
echo program end.
exit /b 0
