from urllib.request import urlopen
import json
import os


def clone_ssh(thisProject):
    # ssh 下载
    thisProjectURL = thisProject['ssh_url_to_repo']
    thisProjectPath = thisProject['path_with_namespace']
    print('download: ', thisProjectURL, ' ', thisProjectPath)
    if os.path.exists(thisProjectPath):
        command = 'git -C "%s" pull' % (thisProjectPath)
    else:
        command = 'git clone %s %s' % (thisProjectURL, thisProjectPath)
    os.system(command)


def clone_http(thisProject):
    thisProjectURL = thisProject['http_url_to_repo']
    thisProjectPath = thisProject['path_with_namespace']
    print('download: ', thisProjectURL, ' ', thisProjectPath)
    if os.path.exists(thisProjectPath):
        command = 'git clone %s' % (thisProjectPath)
    else:
        command = 'git clone %s %s' % (thisProjectURL, thisProjectPath)
    os.system(command)


def download(gitlabAddr, gitlabToken, ssh_or_http):
    for index in range(100):
        url = "%s/api/v4/projects?private_token=%s&per_page=100&page=%d&order_by=name" % (
            gitlabAddr, gitlabToken, index)
        print(url)
        allProjects = urlopen(url)
        allProjectsDict = json.loads(allProjects.read().decode())
        print(allProjectsDict)
        print("###################")
        print("###################")
        if len(allProjectsDict) == 0:
            print("###################")
            print("nothing")
            print("###################")
            break
        for thisProject in allProjectsDict:
            path = thisProject['http_url_to_repo'] + ' ' + thisProject['path_with_namespace']
            print(path)

        print("download all")
        filter_str = input()

        filter = []
        if len(filter_str.lstrip()) != 0:
            filter = filter_str.split(',')

        for thisProject in allProjectsDict:
            try:
                # http下载
                path = thisProject['http_url_to_repo'] + ' ' + thisProject['path_with_namespace']
                if len(filter) == 0:
                    if ssh_or_http == "http":
                        clone_http(thisProject)
                    else:
                        clone_ssh(thisProject)
                else:
                    for f in filter:
                        if f in path:
                            if ssh_or_http == "http":
                                clone_http(thisProject)
                            else:
                                clone_ssh(thisProject)
                        else:
                            print('skip download: ', path)
                # time.sleep(1)
            except Exception as e:
                print(e)


if __name__ == '__main__':
    while True:
        try:
            #print("gitlabAddr:(eg:http://git.cbim.org.cn)")
            gitlabAddr = "https://gitlab.com/openjump-gis"

            #print("gitlabToken:(eg:FQ_H2eYjDeFCe7GaxzsE")
            gitlabToken = "glpat-s1o7e7V1WspjW_AsQ7rJ"

            download(gitlabAddr, gitlabToken, "http")
        except Exception as e:
            print(e)
