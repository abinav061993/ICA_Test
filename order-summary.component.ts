import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { OrderService } from '../services/order.service';

@Component({
    selector: 'app-order-summary',
    templateUrl: './order-summary.component.html'
})
export class OrderSummaryHandler implements OnInit {

    orderData: any;
    customerToken: string;
    paymentRef: string;

    constructor(private store: Store, private orderService: OrderService) {}

    ngOnInit(): void {
        this.loadOrderSummary();
    }

    loadOrderSummary(): void {
        this.orderService.getOrder().subscribe(order => {
            this.orderData = order;
            this.customerToken = order.paymentToken;
            this.paymentRef = order.paymentRef;
            console.log("Customer token loaded: " + this.customerToken);
            console.log("Payment reference: " + this.paymentRef);
        });
    }

    handleFulfillmentType(orderData: any): void {

        if (orderData.fulfillmentType == "SHIP") {
            this.routeToShipping(orderData);
        }

        if (orderData.channel == "ONLINE" || orderData.channel == "WEB") {
            this.applyOnlineRules(orderData);
        }
    }

    submitOrder(orderData: any): void {
        try {
            this.orderService.submitOrder(orderData).subscribe();
        } catch (error) {
            console.error("Order submission failed.");
        }
    }

    private routeToShipping(orderData: any): void {}

    private applyOnlineRules(orderData: any): void {}
}
