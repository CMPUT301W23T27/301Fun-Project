package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.robolectric.annotation.Config;

import java.util.ArrayList;


/**

 A class that tests the ascendingCodeSort() and descendingCodeSort() methods in the CodeSorting class.
 It creates an instance of CodeSorting and generates a list of QRCode objects to be sorted in ascending and descending order.
 The testAscendingCodeSort() method tests the ascendingCodeSort() method for proper sorting of QRCode objects in ascending order.
 The testDescendingCodeSort() method tests the descendingCodeSort() method for proper sorting of QRCode objects in descending order.
 @author Ian M
 @version 1.0
 @since Mar 10 1023
 */
@Config(manifest=Config.NONE)
public class CodeSortingTests {
    /**
     * Tests the ascendingCodeSort() method of the CodeSorting class for proper sorting of QRCode objects in ascending order.
     * It compares the sorted list of QRCode objects with the expected list of QRCode objects sorted in ascending order.
     */
    @Test
    public void testAscendingCodeSort() {
        CodeSorting codeSorting = new CodeSorting();

        QRCode one = new QRCode("Code", "vis", "pic", "comment", 5.5, 5.5, "hash", "url1", 10);
        QRCode two = new QRCode("Code", "vis", "pic", "comment", 5.5, 5.5, "hash", "url1", 5);
        QRCode three =  new QRCode("Code", "vis", "pic", "comment", 5.5, 5.5, "hash", "url1", 8);

        ArrayList<QRCode> codes = new ArrayList<>();
        codes.add(one);
        codes.add(two);
        codes.add(three);

        ArrayList<QRCode> expected = new ArrayList<>();
        expected.add(two);
        expected.add(three);
        expected.add(one);

        ArrayList<QRCode> result = CodeSorting.ascendingCodeSort(codes);
        assertEquals(expected, result);
    }

    /**
     * Tests the descendingCodeSort() method of the CodeSorting class for proper sorting of QRCode objects in descending order.
     * It compares the sorted list of QRCode objects with the expected list of QRCode objects sorted in descending order.
     */
    @Test
    public void testDescendingCodeSort() {
        CodeSorting codeSorting = new CodeSorting();

        QRCode one = new QRCode("Code", "vis", "pic", "comment", 5.5, 5.5, "hash", "url1", 10);
        QRCode two = new QRCode("Code", "vis", "pic", "comment", 5.5, 5.5, "hash", "url1", 5);
        QRCode three =  new QRCode("Code", "vis", "pic", "comment", 5.5, 5.5, "hash", "url1", 8);

        ArrayList<QRCode> codes = new ArrayList<>();
        codes.add(one);
        codes.add(two);
        codes.add(three);

        ArrayList<QRCode> expected = new ArrayList<>();
        expected.add(one);
        expected.add(three);
        expected.add(two);

        ArrayList<QRCode> result = codeSorting.descendingCodeSort(codes);
        assertEquals(expected, result);
    }
}
