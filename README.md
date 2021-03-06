# Habanero
Habanero is a test automation framework that was nominated as a finalist at the prestigious **European Software Testing Awards** for the years 2018 and 2017. Habanero allows users to create tests using Cucumber-JVM. 
Habanero also allows enables all members of a development team to get involved and therefore can aid collaboration between the business and technical team members.

Users include many software companies around the world (based in the USA, India and Europe) as well as Direct Line Group in the UK (which is the biggest insurance company in the UK). 
Habanero has also been adopted and collaborated with, by some of the leading tech consultancies in the world. 

In order to get started please call the dependency into your project pom.xml by using the following writing the 'dependencies' section:

        <dependency>
            <groupId>com.usmanhussain.habanero</groupId>
            <artifactId>Habanero</artifactId>
            <version>[Please see what the latest version is]</version>
        </dependency>

# InBuilt Features
Once this is done, ensure you re-build your project by running:
mvn clean install (-DskipTests, if you choose not to execute all your tests as well)

The framework once installed will allow you to have all your driver classes pre-installed. By Default all tests will be executed using 'PhantomJS'.
To change the running of your tests to point to another browser you simply have to use:
-DdriverType=browserName

BrowserName = chrome, firefox, ie, edge, safari and appium (Android chrome and iOS Safari).
NOTE: These will change according to whats in the market e.g. Zelenium and Docker-Selenium will be added when ready.

(Appium capability setup example: clean test -DdriverType=appium -Dtags=@smoketest -Dcapabilities=platformName:iOS,deviceName:iPhone,browserName:safari,automationName:XCUITest
 
 Assuming mobile execution will start after successful installation of latest Appium and its dependencies if any.)

This will then execute the tests using your locally installed browser.

Habanero also uses other projects to expand the coverage of types of tests that can be automated.
These projects are:

**GHOST** - https://github.com/usman-h/Ghost

**SONORA** - https://github.com/usman-h/Sonora

Please do take a look at these and help contribute to make the whole testing ecosystem a better place!

If you require a new project to set up, try using **Cayenne** (https://github.com/usman-h/Cayenne) which is an ArcheType project that utilises Habanero.

Happy Testing...

Usman H Hussain

