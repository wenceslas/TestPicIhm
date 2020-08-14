package fr.gouv.finances.soda.example.testpic.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

class Opera implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Opera");
        caps.setCapability("browser_version", "12.15");
        caps.setCapability("os", "OS X");
        caps.setCapability("os_version", "Mojave");
        caps.acceptInsecureCerts();
        return caps;
    }

    @Override
    public String getDriverExecutableName() {
        return "operadriver";
    }

    @Override
    public String getDriverSystemPropertyName() {
        return "webdriver.opera.driver";
    }

    @Override
    public String toString() {
        return "Opera";
    }
}
