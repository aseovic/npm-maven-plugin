Build Status: [![Build Status](https://travis-ci.org/aseovic/npm-maven-plugin.png?branch=master)](https://travis-ci.org/aseovic/npm-maven-plugin)

# Introduction

This plugin allows you to integrate `npm` (and indirectly the rest of the Node.js
toolchain) into a larger Maven build containing a mix of Java and Node projects. 
It accomplishes this by keeping things simple and making the following assumptions:

1. Node projects should use the standard Node project structure, not Maven project
   structure. *Maven should get out of the way.*
1. JavaScript developers should use the standard Node/JavaScript tools, such as 
   [npm](https://www.npmjs.com), [Grunt](http://gruntjs.com), [Bower](http://bower.io),
   [Mocha](http://mochajs.org) and [Karma](http://karma-runner.github.io/0.13/index.html)
   for dependency management, build orchestration, unit and integration testing, packaging
   and publishing. *Maven should get out of the way.*      
1. There is already `package.json` that can be used to test, bundle and publish 
   Node package using `npm`, so all Maven needs to do is delegate to `npm`... 
   *and get out of the way.*

This plugin defines `npm` packaging type for Maven project and delegates all phases
of the lifecycle to `npm`. As long as there is a script for the Maven lifecycle
phase in `package.json`, it will be executed.

# Prerequisites

You should have `node` and `npm` executables in the path. 

All other tools should be listed in `devDependencies` section of `package.json` 
so they can be installed into the local `node_modules` (and `node_modules/.bin`) 
by simply doing `npm install` (possibly via Maven, as the example below demonstrates).

# Usage

In order to leverage `npm-maven-plugin`, you need to create `pom.xml` in the root
directory of the project (right next to the existing `package.json` file).
 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.mycompany</groupId>
  <artifactId>my-node-project</artifactId>
  <version>1.0.0</version>
  <packaging>npm</packaging>

  <build>
    <plugins>
      <plugin>
        <groupId>com.seovic.maven.plugins</groupId>
        <artifactId>npm-maven-plugin</artifactId>
        <version>1.0.1</version>
        <extensions>true</extensions>
      </plugin>
    </plugins>
  </build>

</project>
```
                      
Notice that the `packaging` in the example above is set to `npm`, and that the
`extensions` are enabled within plugin definition. This ensures that all the phases
of the [default lifecycle](http://maven.apache.org/ref/3.3.3/maven-core/lifecycles.html#default_Lifecycle),
[clean lifecycle](http://maven.apache.org/ref/3.3.3/maven-core/lifecycles.html#clean_Lifecycle) and
[site lifecycle](http://maven.apache.org/ref/3.3.3/maven-core/lifecycles.html#site_Lifecycle)
are bound to `npm:run` goal, which in turn executes `npm run <script>` command,
using lifecycle phase as the script name.

This means that all you need to do is define the scripts for the phases you care 
about in `package.json` and you are done:

```json
{
  "name":        "my-node-project",
  "version":     "1.0.0",
  "description": "My Node.js project with Maven integration",
  "main":        "index.js",
  "scripts": 
    {
    "clean":            "rimraf dist coverage && npm prune",
    "initialize":       "npm install",
    "compile":          "grunt",
    "test":             "mocha --recursive -R spec",
    "package":          "npm pack",
    "integration-test": "karma start karma.conf.js",
    "deploy":           "npm publish"
    }
}  
```
                                        
The above will:

1. Delete `dist` and `coverage` directories and prune `node_modules`
directory when you execute 
`mvn clean` 
1. Update dependencies, run `grunt` (which in turn can run `jshint`, `browserify` 
and any other supported tool), package module into a tarball and run unit and 
integration tests using `mocha` and `karma` respectively when you execute 
`mvn install`
1. Do all of the above and publish module to http://npmjs.com if you run 
`mvn clean deploy`

A really nice thing about the integration is that you can rely on the Maven 
lifecycle to run multiple scripts in the correct order. For example

```bash
mvn test
```

will run `initialize`, `compile` and `test` scripts automatically and in that order, while

```bash
mvn clean test
```

will also run the `clean` script beforehand.

## The not so useful feature

You can also run individual plugin goals directly:

```bash
mvn npm:exec -Dnpm.command=list
mvn npm:install
mvn npm:run -Dnpm.script=my-script
```

However, there isn't much point in doing so, as you can just as easily (or even easier) do:

```bash
npm list
npm install
npm run my-script
```

## To build this project:
`mvn clean install`

## Issues, Contributing
Please post any issues on the Github's Issue tracker. Pull requests are welcome!

### License
Apache 2.0
