# Puritan: Referentially Transparent IO for Scala #
<img src="https://github.com/downloads/ymasory/puritan/scarlet-letter.jpg"
alt="The Scarlet Letter" title="The Scarlet Letter" align="right" />

> “There are many things in this world that a child must not ask about ...
> When an uninstructed multitude attempts to see with its eyes, it is exceedingly apt to be deceived.”
> <br><br>- *The Scarlet Letter*, Nathaniel Hawthorne <br><br><br><br><br><br><br><br>

Puritan lets you do IO with [Scalaz 7](https://github.com/scalaz/scalaz/tree/scalaz-seven) without compromising on functional purity. It's also a great way to annoy everyone.

## Goals (for the future ... not there yet) ##
- Puritan is made up of **only referentially transparent functions**, whether those functions are public or not.
- Puritan is **familiar**, providing wrappers for impure Java and Scala
functions that existing Scala and Scalaz developers can use without learning a
brand new API.
- Puritan uses **only the most primitive impure functions available** on the JVM.
For example, `read` not `readLine`.
Such JDK primitive functions often have `native` in their signature, or cannot
be implemented using only public JDK functions.
- Puritan tightly integrates with Scalaz 7's types.
- Puritan organizes code and modules similarly to Scalaz 7.
- Puritan relies only on JDK 6, Scala 2.9 and Scalaz 7.
- Puritan includes JDK 7's new IO functionality for projects requiring JDK 7.
- Puritan is comprehensively documented, with full API documentation and an examples suite.
- Puritan includes a comprehensive test suite.
- Puritan is licensed under the Scala License, just like Scala and Scalaz.

## Build ##
- Install Scalaz 7, currently in SNAPSHOT.
```bash
git clone https://github.com/scalaz/scalaz.git
cd scalaz
git checkout scalaz-seven
./sbt publish-local
```

- Compile & Test Puritan
```bash
git clone https://github.com/ymasory/puritan.git
cd puritan
sbt test # MUST USE SBT 0.11.3!!
```

