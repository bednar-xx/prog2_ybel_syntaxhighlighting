package highlighting.antlr;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaColours;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;

// TODO Phase III — AntlrTokenCollector (token-based syntax highlighting).

// This highlighter uses the ANTLR-generated MiniJavaLexer to turn the input text into a token
// stream. {@code collectMatches(String)} is the only method you need to implement: extract tokens
// of interest and map them to {@code HighlightRegions} using the colours from {@code
// MiniJavaColours}. Sorting, filtering of invalid regions, and conflict handling are performed by
// the base class {@code SyntaxHighlighter} via the template method {@code computeRegions(...)}.
public class AntlrTokenCollector extends SyntaxHighlighter {

  // TODO (Phase III — implement this method): Use the token stream produced by the ANTLR-generated
  // {@code MiniJavaLexer} to collect highlight regions.
  //
  // Requirements / hints:
  // - Iterate over the lexer tokens (typically via {@code CommonTokenStream}); ignore the EOF
  // token.
  // - For each token type that should be coloured (e.g., keywords, string/char literals, comments),
  // create a {@code HighlightRegion} with the corresponding colour from {@code MiniJavaColours}.
  // - Use {@code Token#getStartIndex()} and {@code Token#getStopIndex()} (inclusive) to compute
  // {@code [start, end)} ranges: {@code start = startIndex, end = stopIndex + 1}.
  // - Do not sort, merge, or resolve overlaps here; return all candidates as you find them.
  // Normalisation and conflict resolution are handled later by the template method.
  // - Annotation highlighting: colour '@' and the immediately following IDENTIFIER token (if
  // present).
  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<HighlightRegion> matches = new ArrayList<>();
    var input = CharStreams.fromString(text);
    var lexer = new MiniJavaLexer(input);
    var tokens = new CommonTokenStream(lexer);
    tokens.fill();
    for (int i = 0; i < tokens.size(); i++) {
      var current = tokens.get(i);
      if (current.getType() != Token.EOF) {

        Color color = null;

        switch (current.getType()) {
          case MiniJavaLexer.STRING_LITERAL:
            color = MiniJavaColours.STRING_LITERAL_COLOUR;
            break;

          case MiniJavaLexer.PACKAGE,
          MiniJavaLexer.IMPORT,
          MiniJavaLexer.CLASS,
          MiniJavaLexer.PUBLIC,
          MiniJavaLexer.PRIVATE,
          MiniJavaLexer.FINAL,
          MiniJavaLexer.RETURN,
          MiniJavaLexer.NULL,
          MiniJavaLexer.NEW,
          MiniJavaLexer.IF,
          MiniJavaLexer.ELSE,
          MiniJavaLexer.WHILE,
          MiniJavaLexer.EXTENDS,
          MiniJavaLexer.IMPLEMENTS:
            color = MiniJavaColours.KEYWORD_COLOUR;
            break;

          case MiniJavaLexer.CHAR_LITERAL:
            color = MiniJavaColours.CHAR_LITERAL_COLOUR;
            break;

          case MiniJavaLexer.LINE_COMMENT:
            color = MiniJavaColours.LINE_COMMENT_COLOUR;
            break;

          case MiniJavaLexer.JAVADOC_COMMENT:
            color = MiniJavaColours.JAVADOC_COMMENT_COLOUR;
            break;

          case MiniJavaLexer.BLOCK_COMMENT:
            color = MiniJavaColours.BLOCK_COMMENT_COLOUR;
            break;

          case MiniJavaLexer.AT:
            if (i + 1 < tokens.size()) {
              var next = tokens.get(i + 1);
              if (next.getType() == MiniJavaLexer.IDENTIFIER) {
                HighlightRegion region =
                    new HighlightRegion(
                        current.getStartIndex(),
                        next.getStopIndex() + 1,
                        MiniJavaColours.ANNOTATION_COLOUR);
                matches.add(region);
                continue;
              }
            }
            break;
        }

        if (color != null) {
          HighlightRegion region =
              new HighlightRegion(current.getStartIndex(), current.getStopIndex() + 1, color);
          matches.add(region);
        }
      }
    }
    return matches;
  }
}
