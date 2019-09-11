#git pull 当前目录先的工程(.bat)

```
@echo off
setlocal enabledelayedexpansion

for /d %%i in (%cd%\*) do (
  echo %%i
  cd %%i
  git pull>nul 2>nul
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

```

#git强制覆盖本地命令（单条执行）
```
git fetch --all && git reset --hard origin/master && git pull
```

#git恢复到指定版本
```
 git log
 git reset --hard    目标版本号
 git push -f

```