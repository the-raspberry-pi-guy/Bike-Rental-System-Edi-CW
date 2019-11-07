JFLAGS = -g
JAVAC = javac
CLASSPATH = bin:testbin:lib/junit-platform-console-standalone-1.5.2.jar
.SUFFIXES: .java .class
SRCDIR = src/uk/ac/ed/bikerental
BINDIR = bin/uk/ac/ed/bikerental
SRCS = $(wildcard $(SRCDIR)/*.java)
CLSS := $(patsubst $(SRCDIR)/%.java,$(BINDIR)/%.class,$(SRCS))
TESTDIR = tests/uk/ac/ed/bikerental
TESTBINDIR = testbin/uk/ac/ed/bikerental
TESTSRCS = $(wildcard $(TESTDIR)/*.java)
TESTCLSS := $(patsubst $(TESTDIR)/%.java,$(TESTBINDIR)/%.class,$(TESTSRCS))
JAVA = java

default: srcs tests

$(BINDIR):
	mkdir -p $(BINDIR)

$(TESTBINDIR):
	mkdir -p $(TESTBINDIR)

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) $(JFLAGS) -sourcepath src -d bin $<

$(TESTBINDIR)/%.class: $(TESTDIR)/%.java
	$(JAVAC) -cp $(CLASSPATH) $(JFLAGS) -sourcepath tests -d testbin $<

srcs: $(BINDIR) $(CLSS)

tests: srcs $(TESTBINDIR) $(TESTCLSS)

test: tests
	$(JAVA) -cp $(CLASSPATH) -ea org.junit.platform.console.ConsoleLauncher -p uk.ac.ed.bikerental

testlog: tests
	-$(JAVA) -cp $(CLASSPATH) -ea org.junit.platform.console.ConsoleLauncher --disable-ansi-colors -p uk.ac.ed.bikerental > output.log 2>&1

submission: src tests testlog
	zip -r submission.zip src tests output.log report.pdf

clean:
	$(RM) bin/uk/ac/ed/bikerental/*.class testbin/uk/ac/ed/bikerental/*.class