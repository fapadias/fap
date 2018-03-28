import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DimensionValue } from './dimension-value.model';
import { DimensionValueService } from './dimension-value.service';

@Injectable()
export class DimensionValuePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dimensionValueService: DimensionValueService

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
                this.dimensionValueService.find(id)
                    .subscribe((dimensionValueResponse: HttpResponse<DimensionValue>) => {
                        const dimensionValue: DimensionValue = dimensionValueResponse.body;
                        this.ngbModalRef = this.dimensionValueModalRef(component, dimensionValue);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dimensionValueModalRef(component, new DimensionValue());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dimensionValueModalRef(component: Component, dimensionValue: DimensionValue): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dimensionValue = dimensionValue;
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
