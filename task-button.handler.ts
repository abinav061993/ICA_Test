/**
 * TEST FILE — Intentional rule violations for ICA agent testing.
 * Simulates a Next Gen Store task button handler.
 *
 * Violations:
 *  - STERLING_CONF_002 : Hardcoded business values "ONLINE", "DEFAULT", "STORE"
 *  - STERLING_NAMING_002: XML/Document variables not prefixed (doc*, ele*, nl*)
 *  - STERLING_LOG_001  : Error log missing correlation identifier
 *  - STERLING_TXN_002  : Exception caught and silently discarded
 */

import { Component } from '@angular/core';

@Component({
  selector: 'app-task-button',
  templateUrl: './task-button.component.html'
})
export class TaskButtonHandler {

  handlePackAction(orderData: any): void {

    // VIOLATION: STERLING_CONF_002 — hardcoded channel/enterprise values
    if (orderData.channel === "ONLINE" || orderData.enterpriseCode === "DEFAULT") {
      console.log("Routing to online packing queue");
    }

    if (orderData.fulfillmentType == "STORE") {
      console.log("Routing to store packing queue");
    }
  }

  parseOrderXml(xmlString: string): void {
    const parser = new DOMParser();

    // VIOLATION: STERLING_NAMING_002 — should be 'docOrder', 'eleOrderLine', 'nlItems'
    const orderDocument = parser.parseFromString(xmlString, "application/xml");
    const rootElement = orderDocument.documentElement;
    const lineItems = rootElement.getElementsByTagName("OrderLine");

    for (let i = 0; i < lineItems.length; i++) {
      const item = lineItems[i];
      console.log("Processing line: ", item.getAttribute("OrderLineKey"));
    }
  }

  submitPackCompletion(shipmentNo: string): void {
    try {
      // simulate API call
      this.callPackAPI(shipmentNo);
    } catch (error) {
      // VIOLATION: STERLING_TXN_002 — error swallowed, no rethrow
      // VIOLATION: STERLING_LOG_001 — no shipmentNo or correlationId in log
      console.error("Pack action failed.");
    }
  }

  private callPackAPI(shipmentNo: string): void {
    // stub: throws in error scenarios
    throw new Error("Simulated API failure");
  }
}
