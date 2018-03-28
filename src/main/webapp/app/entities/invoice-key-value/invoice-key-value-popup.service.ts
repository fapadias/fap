import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { InvoiceKeyValue } from './invoice-key-value.model';
import { InvoiceKeyValueService } from './invoice-key-value.service';

@Injectable()
export class InvoiceKeyValuePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private invoiceKeyValueService: InvoiceKeyValueService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.invoiceKeyValueService.find(id)
                    .subscribe((invoiceKeyValueResponse: HttpResponse<InvoiceKeyValue>) => {
                        const invoiceKeyValue: InvoiceKeyValue = invoiceKeyValueResponse.body;
                        this.ngbModalRef = this.invoiceKeyValueModalRef(component, invoiceKeyValue);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.invoiceKeyValueModalRef(component, new InvoiceKeyValue());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    invoiceKeyValueModalRef(component: Component, invoiceKeyValue: InvoiceKeyValue): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.invoiceKeyValue = invoiceKeyValue;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
