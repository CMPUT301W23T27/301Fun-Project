package com.example.qrmon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;



public class CodeSortingTests {
    @Test
    public void testAscendingCodeSort() {
        CodeSorting codeSorting = new CodeSorting();

        QRCode one = new QRCode("Code", "vis", "pic", "comment", "geo", "hash", "url1", 10);
        QRCode two = new QRCode("Code", "vis", "pic", "comment", "geo", "hash", "url1", 5);
        QRCode three =  new QRCode("Code", "vis", "pic", "comment", "geo", "hash", "url1", 8);

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

    @Test
    public void testDescendingCodeSort() {
        CodeSorting codeSorting = new CodeSorting();

        QRCode one = new QRCode("Code", "vis", "pic", "comment", "geo", "hash", "url1", 10);
        QRCode two = new QRCode("Code", "vis", "pic", "comment", "geo", "hash", "url1", 5);
        QRCode three =  new QRCode("Code", "vis", "pic", "comment", "geo", "hash", "url1", 8);

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
