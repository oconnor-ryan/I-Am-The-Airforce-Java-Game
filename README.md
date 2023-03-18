# I Am The Airforce!

## About this Project
This is a 2D "shoot-em up" action game where the player controls a
fighter jet to fend off against waves of enemies, ranging from other
fighter jets to anti-aircraft guns to battleships. The player must utilize
their gun, missles, and bombs to destroy enemies and their flares to redirect
enemy missles away from themselves. They must also take advantage of powerups
to replenish health, increase their speed, or increase their rate of fire.
There are 8 levels total, with each one getting progressively more difficult.


## Origin of this Project
This is my very first video game I made during my senior year of high school.
It was originally meant to be my final project for my AP Java Programming
course, but due to the pandemic, we switched to remote learning and the
project was changed. Despite this, I worked on the project on my own to
improve my Java programming skills. Because I made this project the same
year I first started programming, some of my design choices in this project
are very questionable. However, I want to post this project publicly to
motivate people who want to start programming, but are too worried about 
writing bad code to start. Many mistakes will be made when learning a new
skill, so it's better to make those mistakes early on rather than never
learning that skill.

## Previews from Game:

![Gameplay from 'I Am The Airforce!' Game](https://user-images.githubusercontent.com/128189530/226136822-e7661dd5-2360-4fa7-b256-7f60f1dfb390.gif)




!(Boss Fight from 'I Am The Airforce!' Game](https://user-images.githubusercontent.com/128189530/226136848-0ec7d470-d29e-4291-aab0-7b4684f5d4c2.mp4)



## Issues

* The generated JAR file will only properly run on Windows.
  * On Mac, all resource files embedded in the JAR file cannot be opened. This is because the method java.lang.ClassLoader.getResourceAsStream(String name) returns null, meaning that the JAR is unable to load its embedded resource files. However, when the JAR is run on Windows, the method returns a valid InputStream for every resource file.
  * On Linux (Debian 11 distribution), no errors are reported, but the GUI does not get displayed.
  * All three machines I tested this on used the Java 17 JVM, although my Linux machine used OpenJDK 17, my Windows machine used Amazon Corretto 17(which uses OpenJDK), and my Mac machine used Homebrew's version of OpenJDK 17.
