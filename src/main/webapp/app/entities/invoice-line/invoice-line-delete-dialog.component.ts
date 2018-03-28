import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InvoiceLine } from './invoice-line.model';
import { InvoiceLinePopupService } from './invoice-line-popup.service';
import { InvoiceLineService } from './invoice-line.service';

@Component({
    selector: 'jhi-invoice-line-delete-dialog',
    templateUrl: './invoice-line-delete-dialog.component.html'
})
export class InvoiceLineDeleteDialogComponent {

    invoiceLine: InvoiceLine;

    constructor(
        private invoiceLineService: InvoiceLineService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invoiceLineService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'invoiceLineListModification',
                content: 'Deleted an invoiceLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invoice-line-delete-popup',
    template: ''
})
export class InvoiceLineDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invoiceLinePopupService: InvoiceLinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.invoiceLinePopupService
                .open(InvoiceLineDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
