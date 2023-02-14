```
Mac OS X 解决HomeBrew更新慢的问题


替换brew.git

cd "$(brew --repo)"
git remote set-url origin https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/brew.git


替换homebrew-core.git
cd "$(brew --repo)/Library/Taps/homebrew/homebrew-core"
git remote set-url origin https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/homebrew-core.git


brew update


清华大学源：
https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/brew.git
https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/homebrew-core.git

中科大源：
https://mirrors.ustc.edu.cn/brew.git
https://mirrors.ustc.edu.cn/homebrew-core.git
————————————————
版权声明：本文为CSDN博主「mickjoust」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/mickjoust/article/details/98876310

```