package com.ibm.sterling.customization;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.logging.Logger;

public class CarrierBookingService {

    private static final Logger logger = Logger.getLogger(CarrierBookingService.class.getName());

    private static final String STATUS_READY_TO_SHIP = "1300";
    private static final String CARRIER_METAPACK     = "METAPACK";
    private static final String CARRIER_XLOGICS      = "XLOGICS";

    public void bookShipments(NodeList shipmentList, YFSEnvironment env) throws YFSException {

        List<Element> readyShipments = new ArrayList<>();

        for (int i = 0; i < shipmentList.getLength(); i++) {
            Element shipment = (Element) shipmentList.item(i);
            if (shipment == null) continue;

            String strStatus = shipment.getAttribute("Status");
            if (strStatus == null || strStatus.isEmpty()) continue;

            if (STATUS_READY_TO_SHIP.equals(strStatus)) {
                readyShipments.add(shipment);
            }
        }

        if (readyShipments.isEmpty()) return;

        try {
            Document docInput = buildBookingDoc(readyShipments);
            Document docResult = env.invokeAPI("createShipment", docInput);
            processBookingResult(docResult);
        } catch (Exception e) {
            throw new YFSException("BOOKING_FAILED", e.getMessage(), e);
        }
    }

    public String resolveCarrier(Element orderElement) {

        if (orderElement == null) return null;

        String strCountry = orderElement.getAttribute("Country");
        String strCarrier = orderElement.getAttribute("PreferredCarrier");

        if (strCountry == null || strCarrier == null) return CARRIER_METAPACK;

        if ("JP".equals(strCountry)) {
            return CARRIER_XLOGICS;
        }

        return CARRIER_METAPACK;
    }

    public void logBookingEvent(String shipmentNo, String carrierId) {

        if (shipmentNo == null || shipmentNo.isEmpty()) {
            logger.severe("Booking event received with no ShipmentNo.");
            return;
        }

        logger.info("Carrier booking completed for ShipmentNo=" + shipmentNo
                + " CarrierId=" + carrierId);
    }

    public void handleBookingFailure(Exception e, String shipmentNo) throws YFSException {
        logger.severe("Carrier booking failed for ShipmentNo=" + shipmentNo
                + " : " + e.getMessage());
        throw new YFSException("CARRIER_BOOKING_ERROR", e.getMessage(), e);
    }

    private Document buildBookingDoc(List<Element> shipments) {
        return null;
    }

    private void processBookingResult(Document docResult) {
    }
}
