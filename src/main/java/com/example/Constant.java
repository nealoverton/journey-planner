package com.example;

import java.util.List;

public class Constant {
    private Constant() {
        // Hide implicit public constructor
    }

    public static final List<String> FLIGHTS = List.of(
            "AB800",
            "BC900",
            "CD400",
            "DE400",
            "BF400",
            "CE300",
            "DE300",
            "EB600",
            "CE200",
            "DC700",
            "EB500",
            "FD200"
    );
    public static final String NO_OUTBOUND_FLIGHT = "No outbound flight";
    public static final String NO_INBOUND_FLIGHT = "No inbound flight";
}
