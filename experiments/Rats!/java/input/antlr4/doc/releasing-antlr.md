# Cutting an ANTLR Release

## Github

Create a release candidate tag 4.x-rc-1 or full 4.5 tag

```bash
git tag -a 4.5 -m 'ANTLR final release 4.5'
git push origin 4.5
```

Create a pre-release or full release at github; [Example 4.5-rc-1](https://github.com/antlr/antlr4/releases/tag/4.5-rc-1).

## Bump version

Edit the repository looking for 4.5 or whatever and update it. Bump version in the following files:

 * runtime/Java/src/org/antlr/v4/runtime/RuntimeMetaData.java
 * runtime/Python2/setup.py
 * runtime/Python2/src/antlr4/Recognizer.py
 * runtime/Python3/setup.py
 * runtime/Python3/src/antlr4/Recognizer.py
 * runtime/CSharp/Antlr4.Runtime/Properties/AssemblyInfo.cs
 * runtime/JavaScript/src/antlr4/package.json
 * runtime/JavaScript/src/antlr4/Recognizer.js
 * tool/src/org/antlr/v4/codegen/target/CSharpTarget.java
 * tool/src/org/antlr/v4/codegen/target/JavaScriptTarget.java
 * tool/src/org/antlr/v4/codegen/target/Python2Target.java
 * tool/src/org/antlr/v4/codegen/target/Python3Target.java

Here is a simple script to display any line from the critical files with, say, `4.5` in it:

```bash
find /tmp/antlr4 -type f -exec grep -l '4\.5' {} \;
```

Commit to repository.

## Maven Repository Settings

First, make sure you have maven set up to communicate with staging servers etc...  Create file `~/.m2/settings.xml` with appropriate username/password for staging server and gpg.keyname/passphrase for signing. Make sure it has strict visibility privileges to just you. On unix, it looks like:

```bash
beast:~/.m2 $ ls -l settings.xml 
-rw-------  1 parrt  staff  914 Jul 15 14:42 settings.xml
```

Here is the file template

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
  User-specific configuration for maven. Includes things that should not
  be distributed with the pom.xml file, such as developer identity, along with
  local settings, like proxy information.
-->
<settings>
   <servers>
        <server>
          <id>sonatype-nexus-staging</id>
          <username>sonatype-username</username>
          <password>XXX</password>
        </server>
        <server>
          <id>sonatype-nexus-snapshots</id>
          <username>sonatype-username</username>
          <password>XXX</password>
        </server>
   </servers>
    <profiles>
            <profile>
              <activation>
                    <activeByDefault>false</activeByDefault>
              </activation>
              <properties>
                    <gpg.keyname>UUU</gpg.keyname>
                    <gpg.passphrase>XXX</gpg.passphrase>
              </properties>
            </profile>
    </profiles>
</settings>
```

## Maven release

The maven deploy lifecycle phased deploys the artifacts and the poms for the ANTLR project to the [sonatype remote staging server](https://oss.sonatype.org/content/repositories/snapshots/).

```bash
mvn deploy -DskipTests
```

```bash
mvn release:prepare
```

It will start out by asking you the version number:

```
...
What is the release version for "ANTLR 4"? (org.antlr:antlr4-master) 4.5.2: : 4.5.2
What is the release version for "ANTLR 4 Runtime"? (org.antlr:antlr4-runtime) 4.5.2: : 
What is the release version for "ANTLR 4 Tool"? (org.antlr:antlr4) 4.5.2: : 
What is the release version for "ANTLR 4 Maven plugin"? (org.antlr:antlr4-maven-plugin) 4.5.2: : 
What is the release version for "ANTLR 4 Runtime Test Generator"? (org.antlr:antlr4-runtime-testsuite) 4.5.2: : 
What is the release version for "ANTLR 4 Tool Tests"? (org.antlr:antlr4-tool-testsuite) 4.5.2: : 
What is SCM release tag or label for "ANTLR 4"? (org.antlr:antlr4-master) antlr4-master-4.5.2: : 4.5.2
What is the new development version for "ANTLR 4"? (org.antlr:antlr4-master) 4.5.3-SNAPSHOT:
...
```

Maven will go through your pom.xml files to update versions from 4.5.2-SNAPSHOT to 4.5.2 for release and then to 4.5.3-SNAPSHOT after release, which is done with:

```bash
mvn release:perform
```

Maven will use git to push pom.xml changes. (big smile)

Now, go here:

&nbsp;&nbsp;&nbsp;&nbsp;[https://oss.sonatype.org/#welcome](https://oss.sonatype.org/#welcome)

and on the left click "Staging Repositories". You click the staging repo and close it, then you refresh, click it and release it. It's done when you see it here:

&nbsp;&nbsp;&nbsp;&nbsp;[http://repo1.maven.org/maven2/org/antlr/antlr4-runtime/](http://repo1.maven.org/maven2/org/antlr/antlr4-runtime/)

Copy the jars to antlr.org site and update download/index.html

```bash
cp ~/.m2/repository/org/antlr/antlr4-runtime/4.5.2/antlr4-runtime-4.5.2.jar ~/antlr/sites/website-antlr4/download/antlr-runtime-4.5.2.jar
cp ~/.m2/repository/org/antlr/antlr4/4.5.2/antlr4-4.5.2.jar ~/antlr/sites/website-antlr4/download/antlr-4.5.2-complete.jar
cd ~/antlr/sites/website-antlr4/download
git add antlr-4.5.2-complete.jar
git add antlr-runtime-4.5.2.jar 
git commit -a -m 'add 4.5.2 jars'
git push origin gh-pages
```

Update on site:

*   download.html
*   index.html
*   scripts/topnav.js

## Deploying Targets

### JavaScript

```bash
cd runtime/JavaScript/src
zip -r /tmp/antlr-javascript-runtime-4.5.2.zip antlr4
cp /tmp/antlr-javascript-runtime-4.5.2.zip ~/antlr/sites/website-antlr4/download
# git add, commit, push
```

Move target to website

```bash
pushd ~/antlr/sites/website-antlr4/download
git add antlr-javascript-runtime-4.5.2.zip
git commit -a -m 'update JS runtime'
git push origin gh-pages
popd
```

### CSharp

```bash
cd ~/antlr/code/antlr4/runtime/CSharp/runtime/CSharp
# kill previous ones manually as "xbuild /t:Clean" didn't seem to do it
rm Antlr4.Runtime/bin/net20/Release/Antlr4.Runtime.dll
rm Antlr4.Runtime/obj/net20/Release/Antlr4.Runtime.dll
# build
xbuild /p:Configuration=Release Antlr4.Runtime/Antlr4.Runtime.mono.csproj
# zip it up to get a version number on zip filename
zip --junk-paths /tmp/antlr-csharp-runtime-4.5.2.zip Antlr4.Runtime/bin/net35/Release/Antlr4.Runtime.dll
cp /tmp/antlr-csharp-runtime-4.5.2.zip ~/antlr/sites/website-antlr4/download
```

Move target to website

```bash
pushd ~/antlr/sites/website-antlr4/download
git add antlr-csharp-runtime-4.5.2.zip
git commit -a -m 'update C# runtime'
git push origin gh-pages
popd
```

### Python

The Python targets get deployed with `setup.py`. First, set up `~/.pypirc` with tight privileges:

```bash
beast:~ $ ls -l ~/.pypirc
-rw-------  1 parrt  staff  267 Jul 15 17:02 /Users/parrt/.pypirc
```

```
[distutils] # this tells distutils what package indexes you can push to
index-servers =
    pypi
    pypitest

[pypi]
repository: https://pypi.python.org/pypi
username: parrt
password: XXX

[pypitest]
repository: https://testpypi.python.org/pypi
username: parrt
```

Then run the usual python set up stuff:

```bash
cd ~/antlr/code/antlr4/runtime/Python2
# assume you have ~/.pypirc set up
python setup.py register -r pypi
python setup.py sdist bdist_wininst upload -r pypi
```

and do again for Python 3 target

```bash
cd ~/antlr/code/antlr4/runtime/Python3
# assume you have ~/.pypirc set up
python setup.py register -r pypi
python setup.py sdist bdist_wininst upload -r pypi
```

Add links to the artifacts from download.html

## Update javadoc for runtime and tool

First gen javadoc:

```bash
$ cd antlr4
$ mvn -DskipTests javadoc:jar install
```

Then copy to website:

```bash
cd ~/antlr/sites/website-antlr4/api
git checkout gh-pages
git pull origin gh-pages
cd Java
jar xvf ~/.m2/repository/org/antlr/antlr4-runtime/4.5.2/antlr4-runtime-4.5.2-javadoc.jar
cd ../JavaTool
jar xvf ~/.m2/repository/org/antlr/antlr4/4.5.2/antlr4-4.5.2-javadoc.jar
git commit -a -m 'freshen api doc'
git push origin gh-pages
```

## Update Intellij plug-in

Rebuild antlr plugin with new antlr jar.
