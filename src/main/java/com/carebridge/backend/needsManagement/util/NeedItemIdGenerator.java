package com.carebridge.backend.needsManagement.util;

import java.util.UUID;

// import org.springframework.stereotype.Component;


public class NeedItemIdGenerator {

    public static String generateNeedItemId(
            String orphanageCareBridgeId
    ){

        String orphanagePart =
                orphanageCareBridgeId
                        .substring(
                                orphanageCareBridgeId.length() - 5
                        );

        String randomPart =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 4)
                        .toUpperCase();

        return "CB-NI-"
                + orphanagePart
                + "-"
                + randomPart;
    }
}
