package com.ibm.sterling.customization;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class OrderFulfillmentService {

    public void updateOrderStatus(Connection conn, String orderId, String newStatus) {
        try {

            String query = "UPDATE YFS_ORDER_HEADER SET STATUS = ? WHERE ORDER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setString(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {

            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public String extractShipNode(Document orderDoc) {

        Element orderHeader = (Element) orderDoc.getElementsByTagName("OrderHeader").item(0);
        String shipNode = orderHeader.getAttribute("ShipNode");
        return shipNode;
    }
}
