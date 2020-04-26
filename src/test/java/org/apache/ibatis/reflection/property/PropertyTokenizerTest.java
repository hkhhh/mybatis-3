package org.apache.ibatis.reflection.property;

import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyTokenizerTest {

  @Test
  public void testPropertyTokenizer() {
    String fullname = "user.emails[0].address";
    PropertyTokenizer propertyTokenizer = new PropertyTokenizer(fullname);
    assertEquals("user", propertyTokenizer.getName());
    assertEquals("user", propertyTokenizer.getIndexedName());
    assertNull(propertyTokenizer.getIndex());
    assertTrue(propertyTokenizer.hasNext());
    assertEquals("emails", propertyTokenizer.next().getName());
    assertEquals("emails[0]", propertyTokenizer.next().getIndexedName());
    assertEquals("0", propertyTokenizer.next().getIndex());
  }

}
