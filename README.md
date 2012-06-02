# Puritan: Referentially Transparent IO for Scala #
![Puritan](https://github.com/downloads/ymasory/puritan/scarlet-letter.jpg)

## About ##
It's tough using `scalaz.IO` in the impure JVM environment.
Puritan aims to fix that, providing wrappers and completely pure IO functions,
nicely integrated with Scalaz 7.

## Goals (for the future ... not there yet) ##
- Puritan is made up of **only referentially transparent functions**, whether those functions are public or not.
- Puritan uses **only the most primitive impure functions available** on the JVM.
For example, `read` not `readLine`.
Such JDK primitive functions always have `native` in their signature.
- Puritan tightly integrates with Scalaz 7's types.
- Puritan organizes code and modules similarly to Scalaz 7.
- Puritan relies only on JDK 6, Scala 2.9 and Scalaz 7.
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

