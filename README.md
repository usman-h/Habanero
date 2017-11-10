# Habanero
This is a test automation framework allowing users to create tests using Cucumber-JVM

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

(Appium capability setup example: clean test -DdriverType=appium "-Dcucumber.options=--tags @smoketest" -Dcapabilities=platformName:iOS,deviceName:MandadapusiPhone,browserName:safari,automationName:XCUITest
 
 Assuming mobile execution will start after successful installation of latest Appium and its dependencies if any.)

This will then execute the tests using your locally installed browser.

