package com.ogbuilds.digitalbank.transaction.util;

public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String TRANSACTION_COMPLETED =
            "transaction-completed";

    public static final String TRANSACTION_FAILED =
            "transaction-failed";

}