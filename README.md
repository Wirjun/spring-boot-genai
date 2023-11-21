# Spring-Boot-Template Project

This is a template project to quickly start with your next great idea. 

## Getting Started

### Prerequisites

+ your favourite IDE (IntelliJ or Eclipse)
+ OpenJDK (server side)
+ latest Apache Tomcat (server side)

### Deploying

#### Packaging
``<packaging>war</packaging>`` in your ``pom.xml`` is required build your project as a WAR file.
Therefor use the Maven-Plugin for IntelliJ and run ``mvn package``. 
#### Install latest Tomcat Version
   + Windows [download](https://tomcat.apache.org/download-90.cgi) and install
   + Linux use following:
 
```
wget http://www-us.apache.org/dist/tomcat/tomcat-9/v9.0.26/bin/apache-tomcat-9.0.30.tar.gz
tar xzf apache-tomcat-9.0.30.tar.gz
sudo mv apache-tomcat-9.0.30 /usr/local/tomcat9
```
#### Configure Environment Variables
Now configure the required environment variables for the Tomcat. JAVA_HOME and JRE_HOME should be enought. If there are any problem consider also setting CATALINA_HOME

```
echo "export CATALINA_HOME="/usr/local/tomcat9"" >> ~/.bashrc
echo "export JAVA_HOME="/usr/lib/jvm/java-11-oracle"" >> ~/.bashrc
echo "export JRE_HOME="/usr/lib/jvm/java-11-oracle"" >> ~/.bashrc
source ~/.bashrc
```
#### Copying WAR file and starting Server
Finally you have to copy your WAR file into ```var/webapps/``` folder of your tomcat.
Tomcat is very easy to use, There is no need to compile its source. You simply extract the archive and start the tomcat server. Tomcat by default start on port 8080, So make sure no other application using the same port.
 
```
cd /usr/local/apache-tomcat9
chmod +x ./bin/startup.sh
./bin/startup.sh
```
## Running the tests

TODO


See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License

