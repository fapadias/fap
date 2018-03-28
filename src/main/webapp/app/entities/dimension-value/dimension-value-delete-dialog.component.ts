import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DimensionValue } from './dimension-value.model';
import { DimensionValuePopupService } from './dimension-value-popup.service';
import { DimensionValueService } from './dimension-value.service';

@Component({
    selector: 'jhi-dimension-value-delete-dialog',
    templateUrl: './dimension-value-delete-dialog.component.html'
})
export class DimensionValueDeleteDialogComponent {

    dimensionValue: DimensionValue;

    constructor(
        private dimensionValueService: DimensionValueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dimensionValueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dimensionValueListModification',
                content: 'Deleted an dimensionValue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dimension-value-delete-popup',
    template: ''
})
export class DimensionValueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dimensionValuePopupService: DimensionValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dimensionValuePopupService
                .open(DimensionValueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
