package de.kopis.twothousand;

import de.kopis.twothousand.controls.PlayfieldControl;
import de.kopis.twothousand.controls.PlayfieldControlParse;
import de.kopis.twothousand.displays.DisplayPlayfield;
import de.kopis.twothousand.models.Direction;
import de.kopis.twothousand.models.Playfield;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HomePage extends WebPage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    // TODO wtfomg No other way to put the parameters into the PlayfieldControl?
    private static PageParameters parameters;

    // shared playfield for this application, only singleplayer supported
    private static final Playfield playfield = new Playfield(4);
    private static final PlayfieldControl controls = new PlayfieldControl() {
        @Override
        public boolean run(Playfield playfield) {
            //TODO no implementation for this
            return run(HomePage::parse, playfield);
        }

        @Override
        public boolean run(PlayfieldControlParse parseMethod, Playfield playfield) {
            //TODO get player input from PageParameters
            return false;
        }

        @Override
        public String getDescription() {
            return "Use the provided controls on this page to play.";
        }
    };

    private static final DisplayPlayfield display = new DisplayPlayfield() {
        @Override
        public void print(OutputStream out, Playfield pf, PlayfieldControl control) throws IOException {
            //TODO display playfield as HTML
            final PrintWriter pw = new PrintWriter(out);
            pw.write("No playfield initialized" + "\n");
            pw.write(control.getDescription() + "\n");
            pw.flush();
        }
    };

    public HomePage(final PageParameters parameters) {
        super(parameters);

        // TODO add controls
        // TODO add score
        // TODO add playfield

        try {
            final String renderedPlayfield = renderPlayfield(parameters);
            add(new Label("playfield", renderedPlayfield));
        } catch (IOException e) {
            logger.error("Can not render playfield", e);
        }

        add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
    }

    public static Direction parse() {
        final StringValue direction = parameters.get("direction");
        Direction d = null;
        if (!direction.isEmpty()) {
            switch (direction.toString()) {
                case "up":
                    d = Direction.UP;
                    break;
                case "down":
                    d = Direction.DOWN;
                    break;
                case "left":
                    d = Direction.LEFT;
                    break;
                case "right":
                    d = Direction.RIGHT;
                    break;
                default:
                    d = null;
                    break;
            }
        }
        return d;
    }

    private String renderPlayfield(final PageParameters parameters) throws IOException {
        // remember parameters for control parsing - DO NOT REMOVE
        this.parameters = parameters;

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        display.print(bos, playfield, controls);
        return bos.toString();
    }
}
