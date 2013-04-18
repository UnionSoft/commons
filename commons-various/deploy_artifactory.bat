@echo off
echo #################################################################
echo Please make sure you have the following setting in your 
echo settings.xml located at C:\Documents and Settings\USERNAME\.m2
echo #################################################################
echo ^<servers^>
echo  ^<server^>
echo   ^<id^>internal.repo^</id^>
echo   ^<username^>admin^</username^>
echo   ^<password^>password^</password^>
echo  ^</server^>
echo ^</servers^>
echo #################################################################
call mvn clean deploy

