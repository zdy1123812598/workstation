#!/bin/bash

/usr/sbin/sshd -D
x11vnc -forever -usepw -create

tail -f /dev/null