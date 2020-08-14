package fr.gouv.finances.soda.example.testpic.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;

class Firefox implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("os", "Windows");
        return options;
    }

    @Override
    public String getDriverExecutableName() {
        return "geckodriver";
    }

    @Override
    public String getDriverSystemPropertyName() {
        return "webdriver.gecko.driver";
    }

    @Override
    public String toString() {
        return "Firefox";
    }
}
