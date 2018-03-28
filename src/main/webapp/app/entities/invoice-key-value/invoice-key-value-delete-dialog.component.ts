import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InvoiceKeyValue } from './invoice-key-value.model';
import { InvoiceKeyValuePopupService } from './invoice-key-value-popup.service';
import { InvoiceKeyValueService } from './invoice-key-value.service';

@Component({
    selector: 'jhi-invoice-key-value-delete-dialog',
    templateUrl: './invoice-key-value-delete-dialog.component.html'
})
export class InvoiceKeyValueDeleteDialogComponent {

    invoiceKeyValue: InvoiceKeyValue;

    constructor(
        private invoiceKeyValueService: InvoiceKeyValueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invoiceKeyValueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'invoiceKeyValueListModification',
                content: 'Deleted an invoiceKeyValue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invoice-key-value-delete-popup',
    template: ''
})
export class InvoiceKeyValueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invoiceKeyValuePopupService: InvoiceKeyValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.invoiceKeyValuePopupService
                .open(InvoiceKeyValueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
