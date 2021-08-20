package com.stw.sieve.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Sieve of Erastothenes</h1>
 * The sieve determines a list prime numbers less than a supplied positive integer
 * <p>
 *
 * @author  Susan White
 * @version 1.0
 * @since   2021-08-14
 */
public class sieve {
    public static String getPrimes(int inputValue) {
        List<String> outValues = new ArrayList<>();
        outValues.add("List of positive prime numbers up to "+inputValue+" are :");
        boolean[] bool = new boolean[inputValue];
        Arrays.fill(bool, true);
        for (int i = 2; i < Math.sqrt(inputValue); i++) {
            if (bool[i]) {
                for (int j = (i * i); j < inputValue; j = j + i) {
                    bool[j] = false;
                }
            }
        }
        for (int i = 2; i < bool.length; i++) {
            if (bool[i]) {
               outValues.add(String.valueOf(i));
            }
        }
        return String.join(" ", outValues);
    }
}