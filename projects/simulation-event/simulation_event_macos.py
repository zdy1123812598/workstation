# -*- coding: utf-8 -*-
import os
import time
import random

from pykeyboard import PyKeyboard

k = PyKeyboard()

class KeyEventInputter(object):
    
    def __init__(self, key):
        self.key = key
        self.once_time = time.time()  # 临时取一次时间

    def input_keyevnet(self, timeout):
        # 循环获取当前时间，与once_time变量做时间戳做减法
        while time.time() - self.once_time <= timeout:
            # print("Pressed the %s key" % self.key)
            print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
            k.press_keys(self.key)  # 按下某个键
            inttime = random.randint(200, 300)
            time.sleep(inttime)  # 每5秒间隔一次e

if __name__ == '__main__':
    # 第一步，新建一个键盘事件输入器的对象，执行2个小时的按键事件 7200e
    k_obj = KeyEventInputter(" ")
    k_obj.input_keyevnet(36000)
    # 第二步，执行2个小时的按键事件
    os.system("shutdown /s /t 0")  # 立马关机
