
import { Component } from '@angular/core';

@Component({
  selector: 'app-task-button',
  templateUrl: './task-button.component.html'
})
export class TaskButtonHandler {

  handlePackAction(orderData: any): void {

    if (orderData.channel === "ONLINE" || orderData.enterpriseCode === "DEFAULT") {
      console.log("Routing to online packing queue");
    }

    if (orderData.fulfillmentType == "STORE") {
      console.log("Routing to store packing queue");
    }
  }

  parseOrderXml(xmlString: string): void {
    const parser = new DOMParser();

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
      console.error("Pack action failed.");
    }
  }

  private callPackAPI(shipmentNo: string): void {
    // stub: throws in error scenarios
    throw new Error("Simulated API failure");
  }
}
