@echo off
:: ===================
:: ������͸�ͻ��˽ű�
:: ===================
cls
set port=80
set host=www
set /p port=������Ҫӳ��ı��ض˿ںţ�%port%����
:port
if %port% gtr 99999999999999999999999999999999 (
    cls
    set /p port=��������Ч�Ķ˿ںţ�0��65535����
    goto port
) else if %port% leq 0 (
    set port=Nan
    goto port
) else if %port% geq 65535 (
    set port=Nan
    goto port
)
:input
set /p host=������Ҫӳ�����������
if "%host%"=="www" (
    goto input
)
ngrok -config ngrok.cfg -subdomain=%host% %port%