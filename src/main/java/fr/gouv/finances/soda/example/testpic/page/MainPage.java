package fr.gouv.finances.soda.example.testpic.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@PageUrl("/")
public class MainPage extends FluentPage {

    @FindBy(linkText = "Projets")
    private FluentWebElement projetsLink;

    @FindBy(className = "about-page-projects-link")
    private FluentWebElement content;

    public MainPage verifyIfIsLoaded() {
        assertThat(content).isPresent();
        return this;
    }

    public void clickOnProjetsLink() {
        projetsLink.click();
    }
}
