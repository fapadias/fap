import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InvoiceKeyValue } from './invoice-key-value.model';
import { InvoiceKeyValueService } from './invoice-key-value.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-invoice-key-value',
    templateUrl: './invoice-key-value.component.html'
})
export class InvoiceKeyValueComponent implements OnInit, OnDestroy {
invoiceKeyValues: InvoiceKeyValue[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private invoiceKeyValueService: InvoiceKeyValueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.invoiceKeyValueService.query().subscribe(
            (res: HttpResponse<InvoiceKeyValue[]>) => {
                this.invoiceKeyValues = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInvoiceKeyValues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InvoiceKeyValue) {
        return item.id;
    }
    registerChangeInInvoiceKeyValues() {
        this.eventSubscriber = this.eventManager.subscribe('invoiceKeyValueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
