Set ws = WScript.CreateObject("WScript.Shell")
ws.run "C:\Windows\System32\bash.exe -c 'sudo /etc/init.wsl'",0
ws.run "C:\Windows\System32\bash.exe -c 'sudo /etc/init.d/ssh start'",0