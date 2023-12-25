@echo off
call checkProps4Win.cmd
call mvn -f shoring clean install
call lw.cmd
