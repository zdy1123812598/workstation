#git命令

```
1、初始化

从本节开始，除特殊说明，以下命令均适用于 Git 与 Git-SVN。

# 在当前目录新建一个Git代码库
$ git init
# 下载一个项目和它的整个代码历史 [Git only]
$ git clone [url]


2、配置

# 列举所有配置
$ git config -l
# 为命令配置别名
$ git config --global alias.co checkout
$ git config --global alias.ci commit
$ git config --global alias.st status
$ git config --global alias.br branch
# 设置提交代码时的用户信息
$ git config [--global] user.name [name]
$ git config [--global] user.email [email address]
Git 用户的配置文件位于 ~/.gitconfig
Git 单个仓库的配置文件位于 ~/$PROJECT_PATH/.git/config


3、增删文件

# 添加当前目录的所有文件到暂存区
$ git add .
# 添加指定文件到暂存区
$ git add <file1> <file2> ...
# 添加指定目录到暂存区，包括其子目录
$ git add <dir>
# 删除工作区文件，并且将这次删除放入暂存区
$ git rm [file1] [file2] ...
# 停止追踪指定文件，但该文件会保留在工作区
$ git rm --cached [file]
# 改名文件，并且将这个改名放入暂存区
$ git mv [file-original] [file-renamed]
把文件名 file1 添加到 .gitignore 文件里，Git 会停止跟踪 file1 的状态。


4、分支

# 列出所有本地分支
$ git branch
# 列出所有本地分支和远程分支
$ git branch -a
# 新建一个分支，但依然停留在当前分支
$ git branch [branch-name]
# 新建一个分支，并切换到该分支
$ git checkout -b [new_branch] [remote-branch]
# 切换到指定分支，并更新工作区
$ git checkout [branch-name]
# 合并指定分支到当前分支
$ git merge [branch]
# 选择一个 commit，合并进当前分支
$ git cherry-pick [commit]
# 删除本地分支，-D 参数强制删除分支
$ git branch -d [branch-name]
# 删除远程分支
$ git push [remote] :[remote-branch]


5、提交

# 提交暂存区到仓库区
$ git commit -m [message]
# 提交工作区与暂存区的变化直接到仓库区
$ git commit -a
# 提交时显示所有 diff 信息
$ git commit -v
# 提交暂存区修改到仓库区，合并到上次修改，并修改上次的提交信息
$ git commit --amend -m [message]
# 上传本地指定分支到远程仓库
$ git push [remote] [remote-branch]


6、拉取

# 下载远程仓库的所有变动 (Git only)
$ git fetch [remote]
# 显示所有远程仓库 (Git only)
$ git remote -v
# 显示某个远程仓库的信息 (Git only)
$ git remote show [remote]
# 增加一个新的远程仓库，并命名 (Git only)
$ git remote add [remote-name] [url]
# 取回远程仓库的变化，并与本地分支合并，(Git only), 若使用 Git-SVN，请查看第三节
$ git pull [remote] [branch]
# 取回远程仓库的变化，并与本地分支变基合并，(Git only), 若使用 Git-SVN，请查看第三节
$ git pull --rebase [remote] [branch]


7、撤销

# 恢复暂存区的指定文件到工作区
$ git checkout [file]
# 恢复暂存区当前目录的所有文件到工作区
$ git checkout .
# 恢复工作区到指定 commit
$ git checkout [commit]
# 重置暂存区的指定文件，与上一次 commit 保持一致，但工作区不变
$ git reset [file]
# 重置暂存区与工作区，与上一次 commit 保持一致
$ git reset --hard
# 重置当前分支的指针为指定 commit，同时重置暂存区，但工作区不变
$ git reset [commit]
# 重置当前分支的HEAD为指定 commit，同时重置暂存区和工作区，与指定 commit 一致
$ git reset --hard [commit]
# 新建一个 commit，用于撤销指定 commit
$ git revert [commit]
# 将未提交的变化放在储藏区
$ git stash
# 将储藏区的内容恢复到当前工作区
$ git stash pop


8、查询

# 查看工作区文件修改状态
$ git status
# 查看工作区文件修改具体内容
$ git diff [file]
# 查看暂存区文件修改内容
$ git diff --cached [file]
# 查看版本库修改记录
$ git log
# 查看某人提交记录
$ git log --author=someone
# 查看某个文件的历史具体修改内容
$ git log -p [file]
# 查看某次提交具体修改内容
$ git show [commit]


```