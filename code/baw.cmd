@echo off
rem Build application for Windows.
call readProps.cmd appWin.properties
call readProps.cmd deploy.properties
call mvn -f shoring clean install
call mvn -f legacy clean install
copy /b/v/y legacy\target\panopset-jar-with-dependencies.jar %USERPROFILE%\panopset.jar
call lw.cmd
