docker build -t ubuntu-desktop-ssh .


docker run -d -p 2222:22 --name ubuntu-desktop ubuntu-desktop-ssh


ssh root@localhost -p 2222


ssh -X root@localhost -p 2222


startx