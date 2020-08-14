package fr.gouv.finances.soda.example.testpic;

import java.net.MalformedURLException;
import java.net.URL;

import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.runner.RunWith;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.gouv.finances.soda.example.testpic.browser.IBrowser;
import fr.gouv.finances.soda.example.testpic.config.Config;
import fr.gouv.finances.soda.example.testpic.config.ConfigException;
import fr.gouv.finances.soda.example.testpic.config.SeleniumBrowserConfigProperties;
import io.appium.java_client.AppiumDriver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class PicIhmTest extends FluentTest {

    private static final Logger log = LoggerFactory.getLogger(PicIhmTest.class);

    @Autowired
    private SeleniumBrowserConfigProperties config;

    @Override
    public WebDriver newWebDriver() {
        if (config.useHub()) {
            // Don't log your Grid url because you may expose your SauceLabs/BrowserStack API key :)
            log.info("Running test on Grid using {}", getBrowser());
            return runRemoteWebDriver();
        } else if (config.isMobileSimulator()) {
            log.info("Running test on Appium server {} using {}", getAppiumServerUrl(), getBrowser());
            return runTestOnAppiumServer();
        } else {
            log.info("Running test locally using {}", getBrowser());
            return super.newWebDriver();
        }
    }

    private WebDriver runTestOnAppiumServer() {
        try {
            return new AppiumDriver(
                    new URL(getAppiumServerUrl()), getBrowser().getCapabilities());
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + getAppiumServerUrl(), e);
        }
    }

    private WebDriver runRemoteWebDriver() {
        try {
            return new Augmenter().augment(
                    new RemoteWebDriver(new URL(getRemoteUrl()), getBrowser().getCapabilities()));
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + getRemoteUrl(), e);
        }
    }

    @Override
    public String getWebDriver() {
        return config.getBrowserName();
    }

    private String getAppiumServerUrl() {
        return config.getAppiumServerUrl();
    }

    @Override
    public String getRemoteUrl() {
        return config.getGridUrl();
    }

    @Override
    public Capabilities getCapabilities() {
        return getBrowser().getCapabilities();
    }

    @Override
    public String getBaseUrl() {
        return config.getPageUrl();
    }

    private IBrowser getBrowser() {
        return IBrowser.getBrowser(config.getBrowserName());
    }

}
