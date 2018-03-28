import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MetricValue } from './metric-value.model';
import { MetricValuePopupService } from './metric-value-popup.service';
import { MetricValueService } from './metric-value.service';

@Component({
    selector: 'jhi-metric-value-delete-dialog',
    templateUrl: './metric-value-delete-dialog.component.html'
})
export class MetricValueDeleteDialogComponent {

    metricValue: MetricValue;

    constructor(
        private metricValueService: MetricValueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.metricValueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'metricValueListModification',
                content: 'Deleted an metricValue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-metric-value-delete-popup',
    template: ''
})
export class MetricValueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private metricValuePopupService: MetricValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.metricValuePopupService
                .open(MetricValueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
