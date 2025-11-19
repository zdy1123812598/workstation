docker build -t ubuntu-desktop-ssh .


docker run -d -p 2222:22 --name ubuntu-desktop ubuntu-desktop-ssh


ssh root@localhost -p 2222


xhost +local:root  # 在Mac的终端中运行此命令以允许root用户访问X server


ssh -X root@localhost -p 2222


startx