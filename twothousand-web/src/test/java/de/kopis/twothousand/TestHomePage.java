package de.kopis.twothousand;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

public class TestHomePage {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void homepageRendersSuccessfully() {
        //start and render the test page
        tester.startPage(HomePage.class);

        //assert rendered page class
        tester.assertRenderedPage(HomePage.class);

        // check that the footer is rendered properly
        tester.assertContains("6.20.0");
        //TODO check that the rendered playfield is present
        //tester.assertContains("<td>X</td>");
        tester.assertContains("No playfield initialized");
    }
}
