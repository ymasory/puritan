# puritan, referentially transparent io for Scala #
Requires scalaz 7, currently in SNAPSHOT.

## Build ##
- Install scalaz 7

```bash
git clone https://github.com/scalaz/scalaz.git
cd scalaz
git checkout scalaz-seven
./sbt publish-local
```

- Compile & test puritan

```bash
git clone https://github.com/ymasory/puritan.git
cd puritan
sbt test # MUST USE SBT 0.11.3!!
```
