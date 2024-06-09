call readProps.cmd appWin.properties
call readProps.cmd deploy.properties
call readProps.cmd %USERPROFILE%\Documents\panopset\dev.properties

call validateProp.cmd INSTALLER_PFX %INSTALLER_PFX%
if errorlevel 1 goto errorExit

goto goodExit
:errorExit
echo Terminating due to error.
exit /B
:goodExit
echo *********************************************************
echo Properties load and verification completed successfully.
echo *********************************************************

echo *********************************************************
echo "Defining the INSTALLER variables."
echo *********************************************************
set INSTALLER="%INSTALLER_PFX%%PV%%INSTALLER_SFX%"

