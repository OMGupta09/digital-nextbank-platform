package com.ogbuilds.digitalbank.account.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGenerator {

    private final Random random = new Random();

    public String generate() {

        long number =
                1000000000L +
                        Math.abs(random.nextLong() % 9000000000L);

        return String.valueOf(number);

    }

}