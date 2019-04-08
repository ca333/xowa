# PLAT_NAME must be one of the following: windows_64,linux_64,macosx_64
export plat_name=windows_64

# ROOT_DIR should be created beforehand, and should be in "/" format
export root=c:/xowa_dev

# java settings should match your machine
export jdk=1.7
export JAVA_HOME=C:/xowa_dev/bin/java/jdk_1_8_x64

# directories should be set to whatever exists on your machine
export ant=C:/xowa_dev/bin/apache_ant/bin/ant
export mvn=C:/xowa_dev/bin/apache_maven/bin/mvn

# set "verbose=y" or "verbose="
export verbose=

echo '* XOWA: downloading latest xowa'
cd $root
rm -rf ./src
mkdir src
cd src
git clone https://github.com/gnosygnu/xowa.git
cd ..

echo '* XOWA: copying maven files to root'
cp -rf ./src/xowa/res/dev/make/maven/* ./

# run other shell scripts
sh xowa_maven_files.sh
sh xowa_maven_mvn.sh
