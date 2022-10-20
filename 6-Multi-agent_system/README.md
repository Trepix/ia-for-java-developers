# Multi-agent System

### Configuration

This project uses AspectJ, which is set as dependency in `pom.xml`. Also need to change how the project is compiled. There are 2 options:

* Set up the IDE to use AspectJ and **ajc** compiler. There is an example in this [link](http://tzachsolomon.blogspot.com/2015/08/how-to-create-hello-world-with-intellij.html).
* Modify `pom.xml` to build and run the project always through maven.

### Disclaimer

⚠️ Well, after configuring, reading, prototyping and thinking about it, `AspectJ` **is not a good solution at all** for what I want to achieve.

My goal was to remove these ugly `firePropertyChange` calls just incrementing a counter and as a consequence the dependency with `PropertyChangeSupport`.

Nevertheless, I want to practice some things just for fun, so let's get my hands dirty.


### 2nd Disclaimer

In a production code, I would **never** leave the initialization inside the constructor using a Random generator. It's a pain, a harmful and awful pain 🤕.

I'm applying the golden master strategy to allow me to do aggressive and fast refactors.
I don't intend to back to this code, so I'm not concerned about testing maintainability. Thus, I don't want to invest more time doing a builder or factory to fix this.
