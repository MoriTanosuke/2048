package de.kopis.twothousand;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

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
            try {
                Direction direction = parseMethod.parse();
                logger.info("Player input: {} Now moving playfield {}", direction, playfield);
                playfield.move(direction);
            } catch (IOException e) {
                logger.error("Can not parse player input", e);
            }
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
            final StringBuilder buffer = new StringBuilder();
            buffer.append(pf.maxX);
            buffer.append("|");
            buffer.append(pf.maxY);
            buffer.append("|");
            for (int x = 0; x < pf.maxX; x++) {
                for (int y = 0; y < pf.maxY; y++) {
                    final Tile tile = pf.getTile(x, y);
                    if (tile != null) {
                        buffer.append(tile.value);
                    } else {
                        buffer.append("X");
                    }
                    buffer.append(",");
                }
                buffer.append(";");
            }
            buffer.append("|").append(control.getDescription());

            final PrintWriter pw = new PrintWriter(out);
            pw.write(buffer.toString());
            pw.flush();
        }
    };

    public static Direction parse() {
        Direction d = null;
        if (parameters != null && !parameters.get("direction").isEmpty()) {
            final StringValue direction = parameters.get("direction");
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
        logger.debug("parsed direction: {}", d);
        return d;
    }

    public HomePage(final PageParameters parameters) {
        super(parameters);

        // add controls
        addLinkWithParameter("direction", "up");
        addLinkWithParameter("direction", "down");
        addLinkWithParameter("direction", "left");
        addLinkWithParameter("direction", "right");
        // TODO add score

        try {
            // add playfield
            final WebComponent tilesComponent = renderPlayfield("tiles", parameters);
            add(tilesComponent);
        } catch (IOException e) {
            logger.error("Can not render playfield", e);
        }

        add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

        // TODO try to move
        controls.run(HomePage::parse, playfield);
    }

    /**
     * Adds a link to this page with given parameter name and value.
     *
     * @param name  URL parameter name
     * @param value URL parameter value
     */
    private void addLinkWithParameter(final String name, final String value) {
        add(new Link(value) {
            @Override
            public void onClick() {
                final PageParameters pageParameters = new PageParameters();
                pageParameters.add(name, value);
                setResponsePage(HomePage.class, pageParameters);
            }
        });
    }

    private WebComponent renderPlayfield(final String wicketId, final PageParameters parameters) throws IOException {
        // remember parameters for control parsing - DO NOT REMOVE
        this.parameters = parameters;

        // create playfield representation
        logger.debug("Playfield: {}", playfield);
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        display.print(bos, playfield, controls);

        //TODO now parse the playfield and create a nice WebComponent
        // maxX|maxY|xxxxxxxxxxxxxxxx|description goes here
        logger.debug("Unmarshalled playfield: {}", bos.toString());

        final String[] data = bos.toString().split("\\|");
        final int maxX = Integer.valueOf(data[0]);
        final int maxY = Integer.valueOf(data[1]);
        final String[] tiles = data[2].split(";");
        final String description = data[3];

        logger.debug("Unmarshalled size: {},{}", maxX, maxY);
        logger.debug("Unmarshalled tiles: {}", Arrays.deepToString(tiles));

        return new MultiLineLabel(wicketId) {
            @Override
            public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
                final Response response = getRequestCycle().getResponse();
                response.write("<table>");
                for (int x = 0; x < maxX; x++) {
                    response.write("<tr>");
                    final String[] values = tiles[x].split(",");
                    for (int y = 0; y < maxY; y++) {
                        final String value = values[y];
                        response.write("<td>" + value + "</td>");
                    }
                    response.write("</tr>");
                }
                response.write("</table>");
                response.write("<p>" + description + "</p>");
            }
        };
    }
}
