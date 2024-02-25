@echo off
call checkProps4Win.cmd
call mvn -f shoring clean install
call mvn -f legacy clean install
xcopy /y legacy\target\axe-jar-with-dependencies.jar %USERPROFILE%\panopset.jar*
call lw.cmd
