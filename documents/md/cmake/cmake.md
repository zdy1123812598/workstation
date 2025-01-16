#cmake centos

-To update CMake on CentOS to a version higher than 3.13, you can follow these steps:
```

1.Remove the existing CMake version:

  sudo yum remove cmake


2.Install dependencies:
 
 sudo yum install -y epel-release
 sudo yum install -y wget


3.Download the latest version of CMake:
 
  wget https://github.com/Kitware/CMake/releases/download/v3.25.1/cmake-3.25.1.tar.gz


4.Extract the downloaded file:
  
  tar -zxvf cmake-3.25.1.tar.gz
  cd cmake-3.25.1


5.Build and install CMake:
  
  ./bootstrap
  gmake
  sudo make install

6.Verify the installation:
 
 cmake --version


 which cmake


```