package org.ddd.shared.domain.valueobject;

public class WordMother {
  public static String random() {
    return MotherCreator.random().lorem().word();
  }

  public static String random(int length) {
    return MotherCreator.random().lorem().fixedString(length);
  }

  public static String randomMin(int min) {
    return MotherCreator.random().lorem().characters(min, 49);
  }

  public static String randomMinMax(int min, int max) {
    return MotherCreator.random().lorem().characters(min, max);
  }

  public static String randomSentence() {
    return MotherCreator.random().lorem().sentence();
  }

  public static String randomParagraph() {
    return MotherCreator.random().lorem().paragraph();
  }

  public static String randomParagraph(int sentences) {
    return MotherCreator.random().lorem().paragraph(sentences);
  }
}
