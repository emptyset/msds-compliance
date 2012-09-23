#java -cp target/msds-1.0-SNAPSHOT.jar org.freesideatlanta.msds.App $1
mvn exec:java -Dexec.mainClass="org.freesideatlanta.msds.App" -Dexec.args="$1"
