package highlighting.presets;

import static org.junit.jupiter.api.Assertions.*;

import highlighting.core.HighlightRegion;
import highlighting.regex.Token;
import java.util.List;
import org.junit.jupiter.api.Test;

class MiniJavaTokensTest {

  // Tests zu:
  // Treffer am Anfang / in der Mitte / am Ende eines Textes & Mehrere Treffer im selben Text

  @Test
  public void javadoc_comment_token_findet_treffer_am_anfang_in_der_mitte_und_am_ende() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(0);
    String text = "/** start */ int x; /** mitte */ /** ende */";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(3, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(12, regions.get(0).end());
    assertEquals(20, regions.get(1).start());
    assertEquals(32, regions.get(1).end());
    assertEquals(33, regions.get(2).start());
    assertEquals(44, regions.get(2).end());
  }

  @Test
  public void block_comment_token_findet_treffer_am_anfang_in_der_mitte_und_am_ende() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(1);
    String text = "/* start */ int x; /* mitte */ /* ende */";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(3, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(11, regions.get(0).end());
    assertEquals(19, regions.get(1).start());
    assertEquals(30, regions.get(1).end());
    assertEquals(31, regions.get(2).start());
    assertEquals(41, regions.get(2).end());
  }

  @Test
  public void line_comment_token_findet_treffer_am_anfang_in_der_mitte_und_am_ende() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(2);
    String text = "// start\nint x; // mitte\n// ende";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(3, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(8, regions.get(0).end());
    assertEquals(16, regions.get(1).start());
    assertEquals(24, regions.get(1).end());
    assertEquals(25, regions.get(2).start());
    assertEquals(32, regions.get(2).end());
  }

  @Test
  public void string_token_findet_treffer_am_anfang_in_der_mitte_und_am_ende() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(3);
    String text = "\"start\" int x = 0; \"mitte\" \"ende\"";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(3, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(7, regions.get(0).end());
    assertEquals(19, regions.get(1).start());
    assertEquals(26, regions.get(1).end());
    assertEquals(27, regions.get(2).start());
    assertEquals(33, regions.get(2).end());
  }

  @Test
  public void character_token_findet_treffer_am_anfang_in_der_mitte_und_am_ende() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(4);
    String text = "'a' int x = 0; 'b' 'c'";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(3, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(3, regions.get(0).end());
    assertEquals(15, regions.get(1).start());
    assertEquals(18, regions.get(1).end());
    assertEquals(19, regions.get(2).start());
    assertEquals(22, regions.get(2).end());
  }

  @Test
  public void annotation_token_findet_treffer_am_anfang_in_der_mitte_und_am_ende() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(5);
    String text = "@Start int x; @Middle @End";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(3, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(6, regions.get(0).end());
    assertEquals(14, regions.get(1).start());
    assertEquals(21, regions.get(1).end());
    assertEquals(22, regions.get(2).start());
    assertEquals(26, regions.get(2).end());
  }

  @Test
  public void keyword_token_findet_treffer_am_anfang_in_der_mitte_und_am_ende() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(6);
    String text = "public int x; private return";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(3, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(6, regions.get(0).end());
    assertEquals(14, regions.get(1).start());
    assertEquals(21, regions.get(1).end());
    assertEquals(22, regions.get(2).start());
    assertEquals(28, regions.get(2).end());
  }

  // Tests zu:
  // Kein Treffer

  @Test
  public void javadoc_comment_token_findet_keinen_treffer() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(0);
    String text = "/* normaler block kommentar */ // line comment";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(0, regions.size());
  }

  @Test
  public void block_comment_token_findet_keinen_treffer() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(1);
    String text = "// nur ein line comment\npublic class Test";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(0, regions.size());
  }

  @Test
  public void line_comment_token_findet_keinen_treffer() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(2);
    String text = "public class Test { }";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(0, regions.size());
  }

  @Test
  public void string_token_findet_keinen_treffer() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(3);
    String text = "public class Test { }";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(0, regions.size());
  }

  @Test
  public void character_token_findet_keinen_treffer() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(4);
    String text = "'' und 'ab' public class Test";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(0, regions.size());
  }

  @Test
  public void annotation_token_findet_keinen_treffer() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(5);
    String text = "Override Test Deprecated";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(0, regions.size());
  }

  @Test
  public void keyword_token_findet_keinen_treffer_wenn_keyword_nur_teil_eines_wortes_ist() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(6);
    String text = "classification newValue iffy elsewhere";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(0, regions.size());
  }

  // Tests zu:
  // Grenzfälle

  @Test
  public void line_comment_token_erkennt_kommentar_mit_keyword_aehnlichem_text() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(2);
    String text = "// public class @Test \"hello\"";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(1, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(29, regions.get(0).end());
  }

  @Test
  public void block_comment_token_erkennt_kommentar_mit_keyword_aehnlichem_text() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(1);
    String text = "/* public class @Test \"hello\" */";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(1, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(32, regions.get(0).end());
  }

  @Test
  public void javadoc_comment_token_erkennt_kommentar_mit_keyword_aehnlichem_text() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(0);
    String text = "/** public class @Test \"hello\" */";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(1, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(33, regions.get(0).end());
  }

  @Test
  public void annotation_token_erkennt_annotation_am_zeilenanfang_und_nach_leerzeichen() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(5);
    String text = "@Start public class Test {\n    @Over-ride\n}";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(2, regions.size());

    assertEquals(0, regions.get(0).start());
    assertEquals(6, regions.get(0).end());

    assertEquals(31, regions.get(1).start());
    assertEquals(41, regions.get(1).end());
  }

  @Test
  public void string_token_erkennt_string_mit_line_comment_zeichen_im_inhalt() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(3);
    String text = "\"das ist kein // kommentar\"";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(1, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(27, regions.get(0).end());
  }

  @Test
  public void string_token_erkennt_string_mit_block_comment_zeichen_im_inhalt() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(3);
    String text = "\"das ist kein /* kommentar */\"";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(1, regions.size());
    assertEquals(0, regions.get(0).start());
    assertEquals(30, regions.get(0).end());
  }

  // dies ist ein Fehler den der vereinfachte Token nicht abfängt
  // das Leerzeichen wird als Character gezählt, obwohl links und rechts davon dieses '' Paar
  // existiert
  // (außer man sagt das es gewollt und sieht es nicht als Fehler an)
  @Test
  public void character_token_ungewollter_Character() {
    // given
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token token = tokens.get(4);
    String text = "'' 'ab' public class Test";

    // when
    List<HighlightRegion> regions = token.test(text);

    // then
    assertEquals(1, regions.size());
    assertEquals(1, regions.get(0).start());
    assertEquals(4, regions.get(0).end());
  }
}
