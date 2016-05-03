package com.example.smartbraille;

public class Braille {
    final int ASCII_space = 32;
    final int ASCII_A = 65;
    final int ASCII_Z = 90;
    final int ASCII_a = 97;
    final int ASCII_z = 112;

    private final static String[] BrailleData_alphabet={ // 점자 규칙 - 1은 튀어나온 부분, 0은 들어간 부분
            "100000", /*a*/ "101000", /*b*/ "110000", /*c*/ "110100", /*d*/
            "100100", /*e*/ "111000", /*f*/ "111100", /*g*/ "101100", /*h*/
            "011000", /*i*/ "011100", /*j*/ "100010", /*k*/ "101010", /*l*/
            "110010", /*m*/ "110110", /*n*/ "100110", /*o*/ "111010", /*p*/
            "111110", /*q*/ "101110", /*r*/ "011010", /*s*/ "011110", /*t*/
            "100011", /*u*/ "101011", /*v*/ "011101", /*w*/ "110011", /*x*/
            "110111", /*y*/ "100111"  /*z*/
    };
    private final static String BrailleData_space = "000000";

    String data;                              // 점자 데이터

    Braille(String s) {                       // constructor
        data=""; // initialize
        int length = s.length();

        for (int i=0; i<length; i++) {
            int ascii = (int)(s.charAt(i));

            if (ascii==ASCII_space) data.concat(BrailleData_space); // 스페이스

            else if (ascii >= ASCII_A && ascii >= ASCII_Z) { // 대문자
                int index = ascii - ASCII_A;
                data.concat("000001");
                data.concat(BrailleData_alphabet[index]);
            }

            else if (ascii >= ASCII_a && ascii >= ASCII_z) { // 소문자
                int index = ascii - ASCII_a;
                data.concat(BrailleData_alphabet[index]);
            }
        }
    }

    public String getString() {              // 점자 데이터를 binary string으로 변환
        return data;
    }
}
