import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InvoiceKeyValue } from './invoice-key-value.model';
import { InvoiceKeyValueService } from './invoice-key-value.service';

@Component({
    selector: 'jhi-invoice-key-value-detail',
    templateUrl: './invoice-key-value-detail.component.html'
})
export class InvoiceKeyValueDetailComponent implements OnInit, OnDestroy {

    invoiceKeyValue: InvoiceKeyValue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private invoiceKeyValueService: InvoiceKeyValueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInvoiceKeyValues();
    }

    load(id) {
        this.invoiceKeyValueService.find(id)
            .subscribe((invoiceKeyValueResponse: HttpResponse<InvoiceKeyValue>) => {
                this.invoiceKeyValue = invoiceKeyValueResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInvoiceKeyValues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'invoiceKeyValueListModification',
            (response) => this.load(this.invoiceKeyValue.id)
        );
    }
}
