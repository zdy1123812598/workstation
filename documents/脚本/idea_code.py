# coding=gbk
# Copyright 20170611 . All Rights Reserved.
# Prerequisites:
# Python 2.7
# gzip, subprocess, numpy
#
# ==============================================================================
"""Functions for downloading and uzip MNIST data."""
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import os
import shutil
import sys
import urllib
import zipfile

reload(sys)
sys.setdefaultencoding("gbk")

data_dir = 'C:\Users\Administrator\AppData\Local\Temp'
# idea
# data_keys = ['jihuoma.zip']
# source_url = 'http://idea.medeming.com/a/jihuoma1.zip'
# dir_file_name = '2018.2之后的版本用这个.txt'


# python
# data_keys = ['jihuoma.zip']
# source_url = 'http://idea.medeming.com/a/jihuoma2.zip'
# dir_file_name = '2018.2之后的版本用这个.txt'


# webstrom
data_keys = ['jihuoma.zip']
source_url = 'http://idea.medeming.com/a/jihuoma3.zip'
dir_file_name = '2018.2之后的版本用这个.txt'


def get_file(source_url, target_path):
    urllib.urlretrieve(source_url, target_path)


def check_file(data_dir):
    if os.path.exists(data_dir):
        return True
    else:
        return False


# 读取 txt 文件，返回文件内容
def read_txt(fileUrl):
    f = open(str(fileUrl).strip().decode('gbk'), 'r')  # 里面为文件路径
    print(f.read())


def un_zip(data_dir, data_keys, dir_file_name):
    """unzip zip file"""
    file_name = os.path.join(data_dir, data_keys)
    zip_file = zipfile.ZipFile(file_name)
    unzip_file_name = file_name[0:-4]
    if os.path.isdir(unzip_file_name):
        pass
    else:
        os.mkdir(unzip_file_name)
    for names in zip_file.namelist():
        zip_file.extract(names, unzip_file_name)
    zip_file.close()
    unzip_file_name_text = os.path.join(unzip_file_name, dir_file_name)
    read_txt(unzip_file_name_text)


def read_data_sets():
    target_path = os.path.join(data_dir, data_keys[0])
    if check_file(target_path):
        os.remove(target_path)

    unzip_file_name = target_path[0:-4]
    # del_list = os.listdir(unzip_file_name)
    if os.path.exists(unzip_file_name):
        shutil.rmtree(unzip_file_name)
    os.makedirs(unzip_file_name)
    get_file(source_url, target_path)

    # zip
    if os.path.isfile(target_path):
        un_zip(data_dir, data_keys[0], dir_file_name)


if __name__ == '__main__':
    read_data_sets()
