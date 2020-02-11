Adds ability to work with [GitExtensions](https://github.com/gitextensions/gitextensions) application from IDEs based on the IntelliJ platform

## **Usage instructions**
### **Windows**
If you have GitExtensions application installed in the system, this plugin should be able to find executable file automatically and work properly.

If you are using portable version of GitExtensions or plugin can't find the executable, you can provide path to the GitExtensions.exe file via IDE settings menu *(File | Settings... | GitExtensions)*.

### **Linux**
On Linux you can try to create simple shell script, for example
```shell
#!/bin/bash
mono /home/user/GitExtensions/GitExtensions.exe $1 $2 $3
```
and provide path to your script instead of GitExtensions.exe file via IDE settings menu *(File | Settings... | GitExtensions)*.

## **Changelog**
#### 0.4.2
* Fix usages of deprecated API
* Minimum supported version of IntelliJ platform is 193.0
#### 0.4.1
* Update supported versions of IntelliJ platform
#### 0.4.0
* Display branch name on the Commit button for the file opened in the editor or item selected in the project view
* Added "Display current branch name on the Commit button" settings item
* Added "Maximum branch name length" settings item
#### 0.3.1
* Minor UI fix
#### 0.3.0
* Added support for portable version of GitExtensions
You can set the executable file path in the IDE settings menu
#### 0.2.2
* Added dark theme icons
#### 0.2.1
* Added GitExtensions ToolBar to MainToolBar and NavigationToolBar
#### 0.2.0
* Support for any installation path of GitExtensions
#### 0.1.0
* Support for commands via IDE main menu and editor/project window context menu
* Only default installation path (C:\Program Files (x86)\GitExtensions\GitExtensions.exe) is supported

Original icons by [Yusuke Kamiyamane](http://p.yusukekamiyamane.com/) ([CCA/3.0](http://creativecommons.org/licenses/by/3.0/)).
