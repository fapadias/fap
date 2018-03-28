import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Dimension } from './dimension.model';
import { DimensionPopupService } from './dimension-popup.service';
import { DimensionService } from './dimension.service';

@Component({
    selector: 'jhi-dimension-delete-dialog',
    templateUrl: './dimension-delete-dialog.component.html'
})
export class DimensionDeleteDialogComponent {

    dimension: Dimension;

    constructor(
        private dimensionService: DimensionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dimensionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dimensionListModification',
                content: 'Deleted an dimension'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dimension-delete-popup',
    template: ''
})
export class DimensionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dimensionPopupService: DimensionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dimensionPopupService
                .open(DimensionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
