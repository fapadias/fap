import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CostGride } from './cost-gride.model';
import { CostGridePopupService } from './cost-gride-popup.service';
import { CostGrideService } from './cost-gride.service';

@Component({
    selector: 'jhi-cost-gride-delete-dialog',
    templateUrl: './cost-gride-delete-dialog.component.html'
})
export class CostGrideDeleteDialogComponent {

    costGride: CostGride;

    constructor(
        private costGrideService: CostGrideService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.costGrideService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'costGrideListModification',
                content: 'Deleted an costGride'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cost-gride-delete-popup',
    template: ''
})
export class CostGrideDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costGridePopupService: CostGridePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.costGridePopupService
                .open(CostGrideDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
