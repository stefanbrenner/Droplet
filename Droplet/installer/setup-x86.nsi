
  !define PLATTFORM_SHORT "x86"
  !define LIBFOLDER "win32"

  !define APPNAME "Droplet"
  !define COMPANYNAME "Stefan Brenner Photography"
  !define DESCRIPTION "Toolkit for High-Speed-Photography"
  !define VERSIONMAJOR 0
  !define VERSIONMINOR 1
  !define VERSIONBUILD 2
  # These will be displayed by the "Click here for support information" link in "Add/Remove Programs"
  # It is possible to use "mailto:" links in here to open the email client
  !define ABOUTURL "http://www.droplet.at" # "Publisher" link
  !define HELPURL "mailto:droplet@stefanbrenner.com"
  # This is the size (in kB) of all the files copied into "Program Files"
  !define INSTALLSIZE 2048

;--------------------------------
;Include Modern UI

  !include "MUI2.nsh"

;--------------------------------
;General

  ;Name and file
  Name ${APPNAME}
  OutFile "setup-${PLATTFORM_SHORT}.exe"
  
  Caption "${APPNAME} Installer"
  VIProductVersion 0.${VERSIONMAJOR}.${VERSIONMINOR}.${VERSIONBUILD}
  VIAddVersionKey ProductName ${APPNAME}
  VIAddVersionKey Comments "Installer for ${APPNAME}"
  VIAddVersionKey CompanyName "${COMPANYNAME}"
  VIAddVersionKey LegalCopyright "${COMPANYNAME}"
  VIAddVersionKey FileDescription "${APPNAME} Installer"
  VIAddVersionKey FileVersion 0.${VERSIONMAJOR}.${VERSIONMINOR}.${VERSIONBUILD}
  VIAddVersionKey ProductVersion 0.${VERSIONMAJOR}.${VERSIONMINOR}.${VERSIONBUILD}
  VIAddVersionKey InternalName "${APPNAME} Installer"
  VIAddVersionKey OriginalFilename "setup-${PLATTFORM_SHORT}.exe"

  ;Default installation folder
  InstallDir "$PROGRAMFILES\${APPNAME}"
  
  
  ;Get installation folder from registry if available
  InstallDirRegKey HKCU "Software\${APPNAME}" ""

  ;Request application privileges for Windows Vista
  RequestExecutionLevel admin
  
  ShowInstDetails "show"
  ShowUninstDetails "show"
  AutoCloseWindow false

;--------------------------------
;Variables

  Var StartMenuFolder

;--------------------------------
;Interface Settings

  !define MUI_ABORTWARNING

;--------------------------------
;Pages

  !insertmacro MUI_PAGE_WELCOME
  #!insertmacro MUI_PAGE_LICENSE "${NSISDIR}\Docs\Modern UI\License.txt"
  #!insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_STARTMENU Application $StartMenuFolder
  !insertmacro MUI_PAGE_INSTFILES
  !insertmacro MUI_PAGE_FINISH
  
  !insertmacro MUI_UNPAGE_WELCOME
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES
  !insertmacro MUI_UNPAGE_FINISH
  
;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "German" ;first language is default
  !insertmacro MUI_LANGUAGE "English"

;--------------------------------
;Installer Sections

Section "install" SecInstall

  SetOutPath "$INSTDIR"
  
  ;ADD YOUR OWN FILES HERE...
  File "..\build\droplet.jar"
  File "..\lib\${LIBFOLDER}\rxtxSerial.dll"
  File "..\icons\droplet.ico"
  
  ;Store installation folder
  WriteRegStr HKCU "Software\${APPNAME}" "" $INSTDIR
  
  # Registry information for add/remove programs
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "DisplayName" "${APPNAME} - ${DESCRIPTION}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "UninstallString" "$\"$INSTDIR\uninstall.exe$\""
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "QuietUninstallString" "$\"$INSTDIR\uninstall.exe$\" /S"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "InstallLocation" "$\"$INSTDIR$\""
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "DisplayIcon" "$\"$INSTDIR\droplet.ico$\""
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "Publisher" "${COMPANYNAME}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "HelpLink" "${HELPURL}"
  #WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "URLUpdateInfo" "${UPDATEURL}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "URLInfoAbout" "${ABOUTURL}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "DisplayVersion" "${VERSIONMAJOR}.${VERSIONMINOR}.${VERSIONBUILD}"
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "VersionMajor" ${VERSIONMAJOR}
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "VersionMinor" ${VERSIONMINOR}
  # There is no option for modifying or repairing the install
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "NoRepair" 1
  # Set the INSTALLSIZE constant (!defined at the top of this script) so Add/Remove Programs can accurately report the size
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}" "EstimatedSize" ${INSTALLSIZE}
  
  ;Create uninstaller
  WriteUninstaller "$INSTDIR\Uninstall.exe"
  
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    
    ;Create shortcuts
    CreateDirectory "$SMPROGRAMS\$StartMenuFolder"
    #C:\Windows\System32\javaw.exe -jar "C:\Program Files (x86)\Droplet\droplet.jar"
    CreateShortCut "$SMPROGRAMS\$StartMenuFolder\${APPNAME}.lnk" '"$SYSDIR\javaw.exe"' '-jar "$INSTDIR\droplet.jar"' "$INSTDIR\droplet.ico"
    CreateShortCut "$SMPROGRAMS\$StartMenuFolder\Uninstall.lnk" "$INSTDIR\Uninstall.exe"
    
  
  !insertmacro MUI_STARTMENU_WRITE_END

SectionEnd

;--------------------------------
;Installer Functions

Function .onInit

  !insertmacro MUI_LANGDLL_DISPLAY

FunctionEnd


;--------------------------------
;Descriptions

  ;Language strings
  #LangString DESC_SecDummy ${LANG_ENGLISH} "A test section."
  #LangString DESC_SecDummy ${LANG_GERMAN} "Eine test Sektion."

  ;Assign language strings to sections
  #!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  #  !insertmacro MUI_DESCRIPTION_TEXT ${SecDummy} $(DESC_SecDummy)
  #!insertmacro MUI_FUNCTION_DESCRIPTION_END

;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ;ADD YOUR OWN FILES HERE...
  Delete $INSTDIR\droplet.jar
  Delete $INSTDIR\droplet.ico
  Delete $INSTDIR\rxtxSerial.dll

  Delete "$INSTDIR\Uninstall.exe"

  RMDir "$INSTDIR"

  !insertmacro MUI_STARTMENU_GETFOLDER Application $StartMenuFolder
    
  Delete "$SMPROGRAMS\$StartMenuFolder\${APPNAME}.lnk"  
  Delete "$SMPROGRAMS\$StartMenuFolder\Uninstall.lnk"
  RMDir "$SMPROGRAMS\$StartMenuFolder"

  DeleteRegKey /ifempty HKCU "Software\${APPNAME}"
  
  # Remove uninstaller information from the registry
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}"

SectionEnd