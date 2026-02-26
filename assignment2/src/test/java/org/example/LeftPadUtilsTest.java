package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LeftPadUtilsTest {
    /*
    Partitions
    Individual Input
    (str | padStr):
    - null
    - empty string
    - string of length == 1
    - string of length > 1
    (size):
    - negative
    - zero
    - positive
    Combination Input
    (str & padStr & size):
    - NOTE:
    - positive should be split into 2 separate cases
        - size <= len(str)
        - size > len(str)

    - !str & !padStr & positive     : null strings and positive size
    - !str & !padStr & zero         : null strings and size of zero
    - !str & !padStr & negative     : null strings and negative size
    - !str & padStr & positive      : null str, non-null padStr, and positive size
    - !str & padStr & zero          : null str, non-null padStr, and size of zero
    - !str & padStr & negative      : null str, non-null padStr, and negative size
    - str & !padStr & positive      : non-null str, null padStr, and positive size
    - str & !padStr & zero          : non-null str, null padStr, and size of zero
    - str & !padStr & negative      : non-null str, null padStr, and negative size
    - "" & "" & positive            : empty strings and positive size
    - "" & "" & zero                : empty strings and size of zero
    - "" & "" & negative            : empty strings and negative size
    - "" & padStr & positive        : empty str, padStr, and positive size
    - "" & padStr & zero            : empty str, padStr, and size of zero
    - "" & padStr & negative        : empty str, padStr, and negative size
    - str & "" & positive           : str, empty padStr, and positive size
    - str & "" & zero               : str, empty padStr, and size of zero
    - str & "" & negative           : str, empty padStr, and negative size
    - str & padStr & positive       : non-null strings and positive size
    - str & padStr & zero           : non-null strings and size of zero
    - str & padStr & negative       : non-null strings and negative size

    - ADDITIONS:
    - size > len(str)               : size greater than length of str
    - size = len(str)               : size equal to length of str
    - size < len(str)               : size less than length of str
    Output:
    - null
    - empty string
    - string of length == 1
    - string of length > 1
     */

    /*
    null, positive, null --> null
    null, zero, null --> null
    null, negative, null --> null
    null, positive, padStr --> null
    null, zero, padStr --> null
    null, negative, padStr --> null
     */
    @Test
    @Tag("specification")
    void leftPadWhenStringNull() {
//        assertThat(LeftPadUtils.leftPad(null, 2, null)).isNull();
//        assertThat(LeftPadUtils.leftPad(null, 0, null)).isNull();
        assertThat(LeftPadUtils.leftPad(null, -2, null)).isNull();
        assertThat(LeftPadUtils.leftPad(null, 2, "a")).isNull();
//        assertThat(LeftPadUtils.leftPad(null, 0, "a")).isNull();
//        assertThat(LeftPadUtils.leftPad(null, -2, "a")).isNull();
    }

    /*
    "", 2, null --> "  "
    "", zero, null --> ""
    "", negative, null --> ""
    "", 2, "e" --> "ee"
    "", zero, padStr --> ""
    "", negative, padStr --> ""
     */
    @Test
    @Tag("specification")
    void leftPadWhenStringEmpty() {
        assertThat(LeftPadUtils.leftPad("", 2, null)).isEqualTo("  ");
//        assertThat(LeftPadUtils.leftPad("", 0, null)).isEmpty();
        assertThat(LeftPadUtils.leftPad("", -2, null)).isEmpty();
        assertThat(LeftPadUtils.leftPad("", 4, "c")).isEqualTo("cccc");
        assertThat(LeftPadUtils.leftPad("", 0, "a")).isEmpty();
//        assertThat(LeftPadUtils.leftPad("", -2, "a")).isEmpty();
    }

    /*
    str, size <= len(str), "" --> str
    str, size > len(str), "" --> " " + str
    str, zero, "" --> str
    str, negative, "" --> str
    str, size <= len(str), null --> str
    str, size > len(str), null --> " " + str
    str, zero, null --> str
    str, negative, null --> str
     */
    @Test
    @Tag("specification")
    void leftPadWhenPadStringEmptyOrNull() {
        String str = "abc";
        assertThat(LeftPadUtils.leftPad(str, 2, "")).isEqualTo(str);
        assertThat(LeftPadUtils.leftPad(str, 5, "")).isEqualTo("  " + str);
//        assertThat(LeftPadUtils.leftPad(str, 0, "")).isEqualTo(str);
//        assertThat(LeftPadUtils.leftPad(str, -2, "")).isEqualTo(str);
//        assertThat(LeftPadUtils.leftPad(str, 3, null)).isEqualTo(str);
        assertThat(LeftPadUtils.leftPad(str, 6, null)).isEqualTo("   " + str);
//        assertThat(LeftPadUtils.leftPad(str, 0, null)).isEqualTo(str);
        assertThat(LeftPadUtils.leftPad(str, -4, null)).isEqualTo(str);
    }

    /*
    "a", 2, "b" --> "ba"
    str, zero, padStr --> str
    str, negative, padStr --> str
     */
    @Test
    @Tag("specification")
    void leftPadWithLengthOneInputStrings() {
        assertThat(LeftPadUtils.leftPad("b", 3, "c")).isEqualTo("ccb");
        assertThat(LeftPadUtils.leftPad("b", 0, "c")).isEqualTo("b");
//        assertThat(LeftPadUtils.leftPad("b", -4, "c")).isEqualTo("b");
    }

    /*
    "ab", 4, "bc" --> "bcba"
    str, zero, padStr --> str
    str, negative, padStr --> str
     */
    @Test
    @Tag("specification")
    void leftPadWithLengthGreaterThanOneInputStrings() {
        assertThat(LeftPadUtils.leftPad("bc", 5, "cee")).isEqualTo("ceebc");
//        assertThat(LeftPadUtils.leftPad("bc", 0, "cad")).isEqualTo("bc");
        assertThat(LeftPadUtils.leftPad("bc", -4, "cas")).isEqualTo("bc");
    }

    /*
    "ab", 2, "bc" --> "ab"
     */
    @Test
    @Tag("specification")
    void leftPadWithSizeEqualToString() {
        assertThat(LeftPadUtils.leftPad("bc", 2, "ca")).isEqualTo("bc");
    }

    /*
    "ab", 7, "bca" --> "bcabcab" or "bca" + "bc" + "ab"
     */
    @Test
    @Tag("structural")
    void leftPadWithLeftOverPadStringForGivenSize() {
        assertThat(LeftPadUtils.leftPad("ab", 7, "bca")).isEqualTo("bcabcab");
        assertThat(LeftPadUtils.leftPad("abc", 7, "bca")).isEqualTo("bcababc");
        assertThat(LeftPadUtils.leftPad("abaaa", 6, "bca")).isEqualTo("babaaa");
    }
}
