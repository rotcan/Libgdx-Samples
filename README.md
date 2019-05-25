# Sample
This is a list of examples done in libgdx/box2d. 

To run this code in windows as desktop application, do the following steps
1) Build->Make Project
2) Go to Edit configurations, Select JAR Application
3) In Path to Jar, Select Desktop/build/libs/desktop.jar
4) At the bottom of the screen, in tab Before launch: Gradle task, add two lines as follows
  a) Click + -> Run Gradle Task. In Gradle Project, select desktop, click ok.
  b) Again, Click + -> Run Gradle Task. In Gradle Project, select desktop. In Tasks type dist and click ok.
Hopefully it should run now :)
