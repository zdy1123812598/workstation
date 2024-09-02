 
# 制作的包含ffmpeg和openjdr11的docker镜像

FROM centos:7

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN yum provides '*/applydeltarpm'; \
    yum install -y deltarpm; \
    yum install -y tzdata openssl openssl-devel curl ca-certificates fontconfig gzip tar
    
ENV JAVA_VERSION jdk11u

RUN set -eux; \
    ARCH="$(uname -m)"; \
    case "${ARCH}" in \
       aarch64|arm64) \
         ESUM='b741fd2d9eee97f39195d72ee11c3cc53360bd361890f34e37547c06915fd4d3'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk11u-2021-01-27-13-56/OpenJDK11U-jre_aarch64_linux_hotspot_2021-01-27-13-56.tar.gz'; \
         ;; \
       armhf|armv7l) \
         ESUM='7a4b472e0000bdaf68751e057c708fda2a08cf51f38cf168a5b5fa320c5253ea'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk11u-2021-01-27-07-02/OpenJDK11U-jre_arm_linux_hotspot_2021-01-27-07-02.tar.gz'; \
         ;; \
       ppc64el|ppc64le) \
         ESUM='2c90d2ee69570d4c33bb464b8d3fb103ccc440a6e9071e4986fe320448950fe2'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk11u-2021-01-27-13-56/OpenJDK11U-jre_ppc64le_linux_hotspot_2021-01-27-13-56.tar.gz'; \
         ;; \
       s390x) \
         ESUM='e671eb9534f9bfab13d9588007d4295b1ed062cd95b46282a2eb368ffb4ec38e'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk11u-2021-01-27-13-56/OpenJDK11U-jre_s390x_linux_hotspot_2021-01-27-13-56.tar.gz'; \
         ;; \
       amd64|x86_64) \
         ESUM='7592d8027b4585246d166d3abf163ccc2db64564de2ad6d36a6fe5c98e0d49d6'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk11u-2021-01-27-07-02/OpenJDK11U-jre_x64_linux_hotspot_2021-01-27-07-02.tar.gz'; \
         ;; \
       *) \
         echo "Unsupported arch: ${ARCH}"; \
         exit 1; \
         ;; \
    esac; \
    curl -LfsSo /tmp/openjdk.tar.gz ${BINARY_URL}; \
    echo "${ESUM} */tmp/openjdk.tar.gz" | sha256sum -c -; \
    mkdir -p /opt/java/openjdk; \
    cd /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1; \
    rm -rf /tmp/openjdk.tar.gz;

ENV JAVA_HOME=/opt/java/openjdk \
    PATH="/opt/java/openjdk/bin:$PATH"
    
#安装ffmpeg
# 遵循编译指南 ： https://trac.ffmpeg.org/wiki/CompilationGuide/Centos


RUN mkdir -p /opt/ffmpeg/ffmpeg_source; \
    FFMPEG_SOURCE="/opt/ffmpeg/ffmpeg_source"; \
    mkdir -p /opt/ffmpeg/ffmpeg_build; \
    FFMPEG_BUILD="/opt/ffmpeg/ffmpeg_build"; \
    mkdir -p /opt/ffmpeg/ffmpeg_bin; \
    FFMPEG_BIN="/opt/ffmpeg/ffmpeg_bin";
    
ENV FFMPEG_HOME=/opt/ffmpeg/ffmpeg_bin \
    PATH="/opt/ffmpeg/ffmpeg_bin:$PATH"
    
RUN yum install -y autoconf automake bzip2 bzip2-devel cmake freetype-devel gcc gcc-c++ git libtool make pkgconfig zlib-devel
    
    
RUN cd /opt/ffmpeg/ffmpeg_source; \
    curl -O -L https://www.nasm.us/pub/nasm/releasebuilds/2.15.05/nasm-2.15.05.tar.bz2; \
    tar xjvf nasm-2.15.05.tar.bz2; \
    cd nasm-2.15.05; \
    ./autogen.sh; \
    ./configure --prefix="/opt/ffmpeg/ffmpeg_build" --bindir="/opt/ffmpeg/ffmpeg_bin"; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    curl -O -L https://www.tortall.net/projects/yasm/releases/yasm-1.3.0.tar.gz; \
    tar xzvf yasm-1.3.0.tar.gz; \
    cd yasm-1.3.0; \
    ./configure --prefix="/opt/ffmpeg/ffmpeg_build" --bindir="/opt/ffmpeg/ffmpeg_bin"; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    git clone --depth 1 https://code.videolan.org/videolan/x264.git; \
    cd x264; \
    PKG_CONFIG_PATH="/opt/ffmpeg/ffmpeg_build/lib/pkgconfig" ./configure --prefix="/opt/ffmpeg/ffmpeg_build" --bindir="/opt/ffmpeg/ffmpeg_bin" --enable-static; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    git clone https://bitbucket.org/multicoreware/x265_git; \
    cd x265_git/build/linux; \
    cmake -G "Unix Makefiles" -DCMAKE_INSTALL_PREFIX="/opt/ffmpeg/ffmpeg_build" -DENABLE_SHARED:bool=off ../../source; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    git clone --depth 1 https://github.com/mstorsjo/fdk-aac; \
    cd fdk-aac; \
    autoreconf -fiv; \
    ./configure --prefix="/opt/ffmpeg/ffmpeg_build" --disable-shared; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    curl -O -L https://downloads.sourceforge.net/project/lame/lame/3.100/lame-3.100.tar.gz; \
    tar xzvf lame-3.100.tar.gz; \
    cd lame-3.100; \
    ./configure --prefix="/opt/ffmpeg/ffmpeg_build" --bindir="/opt/ffmpeg/ffmpeg_bin" --disable-shared --enable-nasm; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    curl -O -L https://archive.mozilla.org/pub/opus/opus-1.3.1.tar.gz; \
    tar xzvf opus-1.3.1.tar.gz; \
    cd opus-1.3.1; \
    ./configure --prefix="/opt/ffmpeg/ffmpeg_build" --disable-shared; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    git clone --depth 1 https://chromium.googlesource.com/webm/libvpx.git; \
    cd libvpx; \
    ./configure --prefix="/opt/ffmpeg/ffmpeg_build" --disable-examples --disable-unit-tests --enable-vp9-highbitdepth --as=yasm; \
    make; \
    make install; \
    cd /opt/ffmpeg/ffmpeg_source; \
    curl -O -L http://www.ffmpeg.org/releases/ffmpeg-4.3.tar.gz; \
    tar -xvf ffmpeg-4.3.tar.gz; \
    cd ffmpeg-4.3; \
    PKG_CONFIG_PATH="/opt/ffmpeg/ffmpeg_build/lib/pkgconfig" ./configure \
        --prefix="/opt/ffmpeg/ffmpeg_build" \
        --pkg-config-flags="--static" \
        --extra-cflags="-I/opt/ffmpeg/ffmpeg_build/include" \
         --extra-ldflags="-L/opt/ffmpeg/ffmpeg_build/lib" \
        --extra-libs=-lpthread \
        --extra-libs=-lm \
        --bindir="/opt/ffmpeg/ffmpeg_bin" \
        --enable-gpl \
        --enable-libfdk_aac \
        --enable-libfreetype \
        --enable-libmp3lame \
        --enable-libopus \
        --enable-libvpx \
        --enable-libx264 \
        --enable-libx265 \
        --enable-openssl \
        --enable-nonfree; \
    make; \
    make install; \
    yum remove autoconf bzip2 bzip2-devel freetype-devel git libtool pkgconfig zlib-devel tzdata gzip tar; \
    yum clean all; \
    rm -rf /opt/ffmpeg/ffmpeg_source/*
    #hash -d ffmpeg
