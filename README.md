# Puritan: Referentially Transparent IO for Scala #
![Puritan](https://github.com/downloads/ymasory/puritan/scarlet-letter.jpg)

## About ##
It's tough using `scalaz.IO` in the impure JVM environment.
Puritan aims to fix that, providing wrappers and completely pure IO functions,
nicely integrated with Scalaz 7.

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

## Standards & Goals ##
- All functions should be referentially transparent.
- Only the most primitive impure functions should be used from Java.
Everything higher level should be built purely within Puritan.
- Tight integration with Scalaz 7.
- Code/module organization similar to Scalaz 7's.
- No dependencies other than Scala and Scalaz.
- Comprehensive documentation, with examples, prior to any release.
- All code licensed under the Scala License, just like Scala and Scalaz.
- Comprehensive test suite.
