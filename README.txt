Capelin OPAC

Project URL: http://code.google.com/p/capline-opac/

Setup JAVA_HOME and ANT_HOME you should able to type
> ant -f init.xml
This will get all dependency library from Ivy/Maven

> ant war
This should generate the identical war of project download. The war file should in ../dist/

For more information
> ant -projecthelp
