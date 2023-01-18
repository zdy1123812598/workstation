@echo off
for /f "delims=" %%x in (.env) do (set "%%x")
if "%IMANAGER_HOST_IP%"=="" echo "The IMANAGER_HOST_IP variable is not set"&& goto End
if "%TAG%"=="" set TAG=latest
if "%DOCKER_PORT%"=="" set DOCKER_PORT=2375
if "%IAAS_TYPE%"=="" set IAAS_TYPE=DOCKER
if "%IAAS_DOCKER_IP%"=="" set IAAS_DOCKER_IP=%IMANAGER_HOST_IP%
if "%COMPOSE_DOCKER_IP%"=="" set COMPOSE_DOCKER_IP=%IMANAGER_HOST_IP%
if not "%IMANAGER_REGISTRY_URL%"=="" set IMANAGER_REGISTRY_URL=%IMANAGER_REGISTRY_URL%/
if "%LICENSE_SERVER%"=="" set LICENSE_SERVER=""
if "%IMANAGER_HELP_URL%"=="" set IMANAGER_HELP_URL=""
docker-compose -p imgr up -d
docker container logs imanager -f
pause
:End