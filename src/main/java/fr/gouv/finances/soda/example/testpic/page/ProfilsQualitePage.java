package fr.gouv.finances.soda.example.testpic.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@PageUrl("/profiles")
public class ProfilsQualitePage extends FluentPage {

    @FindBy(className = "page-container")
    private FluentWebElement content;

   public void verifyIfIsLoaded() {
        assertThat(content).isPresent();
   }
}
