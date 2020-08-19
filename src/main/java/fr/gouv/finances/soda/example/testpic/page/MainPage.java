package fr.gouv.finances.soda.example.testpic.page;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Profile("devlocal")
@ActiveProfiles("devlocal")
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
