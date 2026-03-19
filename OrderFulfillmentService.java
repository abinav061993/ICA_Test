package com.ibm.sterling.customization;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * TEST FILE — Intentional rule violations for ICA agent testing.
 *
 * Violations:
 *  - STERLING_TXN_001 : Direct UPDATE on YFS_ table
 *  - STERLING_TXN_002 : Swallowed exception (no rethrow)
 *  - STERLING_QUAL_002: getAttribute() called without null check
 *  - STERLING_NAMING_001: Variables not prefixed with type indicator
 */
public class OrderFulfillmentService {

    public void updateOrderStatus(Connection conn, String orderId, String newStatus) {
        try {
            // VIOLATION: STERLING_TXN_001 — direct DB write to YFS table
            String query = "UPDATE YFS_ORDER_HEADER SET STATUS = ? WHERE ORDER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setString(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            // VIOLATION: STERLING_TXN_002 — exception swallowed, no rethrow or wrap
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public String extractShipNode(Document orderDoc) {
        // VIOLATION: STERLING_QUAL_002 — getAttribute called without null check
        // VIOLATION: STERLING_NAMING_001 — should be 'eleOrderHeader', 'strShipNode'
        Element orderHeader = (Element) orderDoc.getElementsByTagName("OrderHeader").item(0);
        String shipNode = orderHeader.getAttribute("ShipNode");
        return shipNode;
    }
}
