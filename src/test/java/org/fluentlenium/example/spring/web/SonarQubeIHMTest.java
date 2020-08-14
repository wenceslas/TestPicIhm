package org.fluentlenium.example.spring.web;

import org.fluentlenium.core.annotation.Page;
import org.junit.Test;

import fr.gouv.finances.soda.example.testpic.PicIhmTest;
import fr.gouv.finances.soda.example.testpic.page.ProfilsQualitePage;
import fr.gouv.finances.soda.example.testpic.page.MainPage;

public class SonarQubeIHMTest extends PicIhmTest {

    @Page
    private MainPage mainPage;

    @Page
    private ProfilsQualitePage profilsQualitePage;

    @Test
    public void visitSonarqube() {
        goTo(mainPage)
                .verifyIfIsLoaded()
                .clickOnProjetsLink();
    }

    @Test
    public void visitProfilsQualite() {
        goTo(profilsQualitePage)
                .verifyIfIsLoaded();
    }
}
