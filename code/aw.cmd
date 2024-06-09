@echo off
rem Build application for Windows.
call checkProps4Win.cmd
rmdir /s /q target
mkdir target
call baw.cmd
