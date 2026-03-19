package com.ibm.sterling.customization;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.logging.Logger;

/**
 * TEST FILE — Intentional rule violations for ICA agent testing.
 *
 * Violations:
 *  - STERLING_PERF_001 : invokeAPI called inside a loop
 *  - STERLING_CONF_001 : Hardcoded status codes "1100" and "9000"
 *  - STERLING_CONF_002 : Hardcoded business values "ONLINE", "US", equals("STORE")
 *  - STERLING_SEC_001  : Logging customer email and card token
 *  - STERLING_LOG_001  : Error log missing correlation identifier
 */
public class ShipmentProcessor {

    private static final Logger logger = Logger.getLogger(ShipmentProcessor.class.getName());

    public void processShipments(NodeList shipmentList, YFSEnvironment env) throws Exception {

        for (int i = 0; i < shipmentList.getLength(); i++) {
            Element shipment = (Element) shipmentList.item(i);

            // VIOLATION: STERLING_CONF_001 — hardcoded status code
            String status = shipment.getAttribute("Status");
            if ("1100".equals(status) || "9000".equals(status)) {

                // VIOLATION: STERLING_PERF_001 — Sterling API invoked inside loop
                Document inputDoc = buildInputDoc(shipment);
                Document result = env.invokeAPI("getShipmentList", inputDoc);
            }
        }
    }

    public void routeOrder(Element orderElement) {

        // VIOLATION: STERLING_CONF_002 — hardcoded business values
        String channel = orderElement.getAttribute("OrderChannel");
        String country = orderElement.getAttribute("Country");

        if (channel.equals("ONLINE") || country == "US") {
            // route to online fulfillment
        }

        String enterpriseCode = orderElement.getAttribute("EnterpriseCode");
        if (enterpriseCode.equals("STORE") || enterpriseCode.equals("DEFAULT")) {
            // route to store
        }
    }

    public void logPaymentDetails(String email, String cardToken, String orderId) {

        // VIOLATION: STERLING_SEC_001 — logging sensitive data
        logger.info("Processing payment for email: " + email);
        logger.info("Card token used: " + cardToken);
    }

    public void handleProcessingError(Exception e) {

        // VIOLATION: STERLING_LOG_001 — no OrderNo/ShipmentNo/TransactionId in error log
        logger.severe("An error occurred during shipment processing: " + e.getMessage());
    }

    private Document buildInputDoc(Element shipment) {
        // stub
        return null;
    }
}
