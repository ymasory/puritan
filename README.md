# puritan, referentially transparent io for Scala #
Requires scalaz 7, currently in SNAPSHOT.

## build ##
- Install scalaz 7

    git clone https://github.com/scalaz/scalaz.git
    cd scalaz
    git checkout scalaz-seven
    sbt publish-local

- Compile & test puritan

    git clone https://github.com/ymasory/puritan.git
    cd puritan
    sbt test
