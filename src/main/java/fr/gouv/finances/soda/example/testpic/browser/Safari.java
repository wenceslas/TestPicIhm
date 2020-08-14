package fr.gouv.finances.soda.example.testpic.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.safari.SafariOptions;

class Safari implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        SafariOptions safariOptions = new SafariOptions();
        safariOptions.setCapability("browser_version", "12.0");
        safariOptions.setCapability("os_version", "Mojave");
        return safariOptions;
    }

    @Override
    public String getDriverExecutableName() {
        return "safaridriver";
    }

    @Override
    public String getDriverSystemPropertyName() {
        return "webdriver.safari.driver";
    }

    @Override
    public String toString() {
        return "Safari";
    }
}
