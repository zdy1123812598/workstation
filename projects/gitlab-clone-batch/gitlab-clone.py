from urllib.request import urlopen
import json
import subprocess, shlex
import time
import os


gitlabToken = 'glpat-s1o7e7V1WspjW_AsQ7rJ'
gitlabAddr = 'https://gitlab.com'
target =    'openjump-gis'

def get_next(group_id):
    print(group_id)
    url = gen_next_url(group_id)
    allProjects     = urlopen(url)
    allProjectsDict = json.loads(allProjects.read().decode())
    if len(allProjectsDict) == 0:
        return
    for thisProject in allProjectsDict: 
        try:
            thisProjectURL  = thisProject['ssh_url_to_repo']
            thisProjectPath = thisProject['path_with_namespace']
            if os.path.exists(thisProjectPath):
                command     = shlex.split('git -C "%s" pull' % (thisProjectPath))
            else:
                command     = shlex.split('git clone %s %s' % (thisProjectURL, thisProjectPath))
            resultCode  = subprocess.Popen(command)
            time.sleep(1)
        except Exception as e:
            print("Error on %s: %s" % (thisProjectURL, e.strerror))
    return resultCode

def have_next_projects(group_id):
    url = gen_next_url(group_id)
    allProjects     = urlopen(url)
    allProjectsDict = json.loads(allProjects.read().decode())
    if len(allProjectsDict) == 0:
        return False
    return True


def get_sub_groups(parent_id):
    url = gen_subgroups_url(parent_id)
    allProjects     = urlopen(url)
    allProjectsDict = json.loads(allProjects.read().decode())
    sub_ids = []
    if len(allProjectsDict) == 0:
        return sub_ids
    for thisProject in allProjectsDict: 
        try:
            id = thisProject['id']
            sub_ids.append(id)
        except Exception as e:
            print("Error on %s: %s" % (id, e.strerror))
    return sub_ids

def cal_next_sub_groupids(parent_id):
    parent = ''
    parent = parent_id
    is_start = 1
    parent_list = []
    sub_ids = get_sub_groups(parent_id)
    print('dadad',sub_ids, parent_id)
    ok = have_next_projects(parent_id) 
    print(ok)
    print(parent_list)
    if len(sub_ids)!=0 and ok == False:
        for i in range(len(sub_ids)):
            print(sub_ids[i])
            parent = sub_ids[i]
            a = cal_next_sub_groupids(sub_ids[i])
            return a
    if len(sub_ids) !=0 and ok == True:
        for i in range(len(sub_ids)):
            print(parent)
            parent = sub_ids[i]
            parent_list.append(sub_ids[i])
            a = cal_next_sub_groupids(sub_ids[i])
            parent_list.extend(a)
    if len(sub_ids) == 0 and ok == True:
        print(is_start)
        parent_list.append(parent)
        return parent_list
    if len(sub_ids) ==0 and ok == False:
        return parent_list
    return parent_list

def download_code(parent_id):
    data =cal_next_sub_groupids(parent_id)
    print('dadada',data)
    for group_id in data:
        get_next(group_id)
    return
 
def gen_next_url(target_id):
    return "https://%s/api/v4/groups/%s/projects?private_token=%s" % (gitlabAddr, target_id, gitlabToken)

def gen_subgroups_url(target_id):
    return "https://%s/api/v4/groups/%s/subgroups?private_token=%s" % (gitlabAddr, target_id, gitlabToken)

def gen_global_url():
    return "http://%s/api/v4/projects?private_token=%s" % (gitlabAddr, gitlabToken)

def download_global_code():
    url = gen_global_url()
    allProjects     = urlopen(url)
    allProjectsDict = json.loads(allProjects.read().decode())
    if len(allProjectsDict) == 0:
        return
    for thisProject in allProjectsDict: 
        try:
            thisProjectURL  = thisProject['ssh_url_to_repo']
            thisProjectPath = thisProject['path_with_namespace']
            print(thisProjectURL + ' ' + thisProjectPath)
            
            if os.path.exists(thisProjectPath):
                command     = shlex.split('git -C "%s" pull' % (thisProjectPath))
            else:
                command     = shlex.split('git clone %s %s' % (thisProjectURL, thisProjectPath))

            resultCode  = subprocess.Popen(command)
            print(resultCode)
            time.sleep(1)
        except Exception as e:
            print("Error on %s: %s" % (thisProjectURL, e.strerror))
    return
 
def main():
    if target == '':
        download_global_code()
    else:
        url     = "https://%s/api/v4/groups?private_token=%s" % (gitlabAddr,gitlabToken)
        allProjects     = urlopen(url)
        allProjectsDict = json.loads(allProjects.read().decode())
        if len(allProjectsDict) == 0:
            return
        target_id = '' 
        for thisProject in allProjectsDict: 
            try:
                this_name = thisProject['name']
                if target == this_name:
                    target_id = thisProject['id']
                    break
            except Exception as e:
                print("Error on %s: %s" % (this_name, e.strerror))
        download_code(target_id)
        return

main()