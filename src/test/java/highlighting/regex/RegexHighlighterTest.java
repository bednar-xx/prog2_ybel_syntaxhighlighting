package highlighting.regex;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import highlighting.core.HighlightRegion;
import highlighting.presets.MiniJavaColours;
import java.util.List;

class RegexHighlighterTest {

    @Test
    public void regex_highlighter_erkennt_einfache_faelle_ohne_ueberlappungen() {
        // given
        RegexHighlighter highlighter = new RegexHighlighter();
        String text = "public \"hi\" @Test";

        // when
        List<HighlightRegion> regions = highlighter.computeRegions(text);

        // then
        assertEquals(3, regions.size());
        assertEquals(0, regions.get(0).start());
        assertEquals(6, regions.get(0).end());
        assertEquals(7, regions.get(1).start());
        assertEquals(11, regions.get(1).end());
        assertEquals(12, regions.get(2).start());
        assertEquals(17, regions.get(2).end());
    }

    @Test
    public void regex_highlighter_verwirft_keyword_innerhalb_von_line_comment() {
        // given
        RegexHighlighter highlighter = new RegexHighlighter();
        String text = "// public class";

        // when
        List<HighlightRegion> regions = highlighter.computeRegions(text);

        // then
        assertEquals(1, regions.size());
        assertEquals(0, regions.get(0).start());
        assertEquals(15, regions.get(0).end());
        assertEquals(MiniJavaColours.LINE_COMMENT_COLOUR, regions.get(0).colour());
    }

    @Test
    public void regex_highlighter_behaelt_javadoc_statt_block_comment_bei_gleichem_match() {
        // given
        RegexHighlighter highlighter = new RegexHighlighter();
        String text = "/** public */";

        // when
        List<HighlightRegion> regions = highlighter.computeRegions(text);

        // then
        assertEquals(1, regions.size());
        assertEquals(0, regions.get(0).start());
        assertEquals(13, regions.get(0).end());
        assertEquals(MiniJavaColours.JAVADOC_COMMENT_COLOUR, regions.get(0).colour());
    }

    @Test
    public void regex_highlighter_behaelt_aufeinanderfolgende_regionen() {
        // given
        RegexHighlighter highlighter = new RegexHighlighter();
        String text = "\"abc\"\"def\"";

        // when
        List<HighlightRegion> regions = highlighter.computeRegions(text);

        // then
        assertEquals(2, regions.size());
        assertEquals(0, regions.get(0).start());
        assertEquals(5, regions.get(0).end());
        assertEquals(5, regions.get(1).start());
        assertEquals(10, regions.get(1).end());
    }

    @Test
    public void regex_highlighter_liefert_keine_regionen_bei_leerstring() {
        // given
        RegexHighlighter highlighter = new RegexHighlighter();
        String text = "";

        // when
        List<HighlightRegion> regions = highlighter.computeRegions(text);

        // then
        assertEquals(0, regions.size());
    }

    @Test
    public void regex_highlighter_liefert_keine_regionen_bei_text_ohne_matches() {
        // given
        RegexHighlighter highlighter = new RegexHighlighter();
        String text = "Ich habe keine Ahnung was ich hier reinschreiben soll";

        // when
        List<HighlightRegion> regions = highlighter.computeRegions(text);

        // then
        assertEquals(0, regions.size());
    }
}
