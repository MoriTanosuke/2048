package de.kopis.twothousand;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
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

        // check that the rendered playfield is present
        tester.assertComponent("tiles", MultiLineLabel.class);

        // assert that controls are present
        tester.assertComponent("up", Link.class);
        tester.assertComponent("down", Link.class);
        tester.assertComponent("left", Link.class);
        tester.assertComponent("right", Link.class);
    }
}
