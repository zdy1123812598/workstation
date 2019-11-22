@echo off
setlocal enabledelayedexpansion

for /d %%i in (%cd%\*) do (
  echo %%i
  cd %%i
  git fetch --all && git reset --hard origin/master && git pull>nul 2>nul
  if %errorlevel% == 0 (echo pull success) else (echo pull error)
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