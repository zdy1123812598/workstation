Set ws = WScript.CreateObject("WScript.Shell")
ws.run "wsl -d Ubuntu-22.04 -u root /etc/init.wsl restart"