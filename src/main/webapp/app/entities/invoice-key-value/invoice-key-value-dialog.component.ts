import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InvoiceKeyValue } from './invoice-key-value.model';
import { InvoiceKeyValuePopupService } from './invoice-key-value-popup.service';
import { InvoiceKeyValueService } from './invoice-key-value.service';
import { InvoiceLine, InvoiceLineService } from '../invoice-line';

@Component({
    selector: 'jhi-invoice-key-value-dialog',
    templateUrl: './invoice-key-value-dialog.component.html'
})
export class InvoiceKeyValueDialogComponent implements OnInit {

    invoiceKeyValue: InvoiceKeyValue;
    isSaving: boolean;

    invoicelines: InvoiceLine[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private invoiceKeyValueService: InvoiceKeyValueService,
        private invoiceLineService: InvoiceLineService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.invoiceLineService.query()
            .subscribe((res: HttpResponse<InvoiceLine[]>) => { this.invoicelines = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.invoiceKeyValue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.invoiceKeyValueService.update(this.invoiceKeyValue));
        } else {
            this.subscribeToSaveResponse(
                this.invoiceKeyValueService.create(this.invoiceKeyValue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InvoiceKeyValue>>) {
        result.subscribe((res: HttpResponse<InvoiceKeyValue>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InvoiceKeyValue) {
        this.eventManager.broadcast({ name: 'invoiceKeyValueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInvoiceLineById(index: number, item: InvoiceLine) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-invoice-key-value-popup',
    template: ''
})
export class InvoiceKeyValuePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invoiceKeyValuePopupService: InvoiceKeyValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.invoiceKeyValuePopupService
                    .open(InvoiceKeyValueDialogComponent as Component, params['id']);
            } else {
                this.invoiceKeyValuePopupService
                    .open(InvoiceKeyValueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
