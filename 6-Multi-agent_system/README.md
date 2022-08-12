# Multi-agent System

### Configuration

This project uses AspectJ, which is set as dependency in `pom.xml`. Also need to change how the project is compiled. There are 2 options:

* Set up the IDE to use AspectJ and **ajc** compiler. There is an example in this [link](http://tzachsolomon.blogspot.com/2015/08/how-to-create-hello-world-with-intellij.html).
* Modify `pom.xml` to build and run the project always through maven.

### Disclaimer

⚠️ Well, after configuring, reading, prototyping and thinking about it, `AspectJ` **is not a good solution at all** for what I want to achieve.

My goal was to remove these ugly `firePropertyChange` calls just incrementing a counter and as a consequence the dependency with `PropertyChangeSupport`.

Nevertheless, I want to practice some things just for fun, so let's get my hands dirty.