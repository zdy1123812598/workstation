#IDEA使用

##IDEA配置


##IDEA  git项目更新
*          @echo off
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

##IDEA配置docker
*           set curdir=%~dp0
            cd /d %curdir%
            call mvn clean package -U -DskipTests
            call mvn install
            cd /d %curdir%
            del *.jar
            copy %curdir%\target\spring-boot-maker-*.jar spring-boot-maker-*.jar
            ren spring-boot-maker-*.jar spring-boot-maker.jar
            docker build -t spring-boot-maker .
            docker stop spring-boot-maker
            docker rm spring-boot-maker
            docker run -d -p 8080:8080 --name spring-boot-maker spring-boot-maker
            timeout /t 3 /nobreak
            exit
*      spring-boot-maker 项目名称


##插件
*
Lombok
Alibaba Java Coding Guidelines plugin support.
ESLint
Key Promoter X
Maven 2 integrates
Maven Helper
MybatisX

google-java-format
Translation
Leetcode Editor
RestfulToolkit
Jclasslib Bytecode Viewer
CamelCase
Jrebel for Intellij
String Manipulation
Free Mybatis Plugin
SequenceDiagram
GenerateAllSetter
Rainbow Brackets
IDEA QAPlug


*