# coding=utf-8

import os
import time
import keyboard
from pykeyboard import PyKeyboard

class KeyEventInputter(object):
    def __init__(self, key):
        self.key = key
        self.once_time = time.time()  # 临时取一次时间

    def input_keyevnet(self, timeout):
        # 循环获取当前时间，与once_time变量做时间戳做减法
        while time.time() - self.once_time <= timeout:
            print("Pressed the %s key" % self.key)
            k = PyKeyboard()
            k.press_keys(['Command', 's'])
            k.press_keys(['Return'])  # 按下某个键
            time.sleep(5)  # 每5秒间隔一次


if __name__ == '__main__':
    # 第一步，新建一个键盘事件输入器的对象，执行2个小时的按键事件
    k_obj = KeyEventInputter("scroll lock")
    k_obj.input_keyevnet(30)

    # 第二步，执行2个小时的按键事件
    # os.system("shutdown /s /t 0")  # windows立马关机
    os.system("sudo shutdown -h now")  # mac立马关机