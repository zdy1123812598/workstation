Set ws = WScript.CreateObject("WScript.Shell")
ws.run "wsl -d Ubuntu-22.04 -u root /etc/init.wsl start", vbhide




#注册版本 wsl -l --all
wsl --setdefault wsl名称
# 比如
wsl --setdefault Ubuntu-20.04