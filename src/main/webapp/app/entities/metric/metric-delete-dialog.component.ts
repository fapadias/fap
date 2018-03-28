import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Metric } from './metric.model';
import { MetricPopupService } from './metric-popup.service';
import { MetricService } from './metric.service';

@Component({
    selector: 'jhi-metric-delete-dialog',
    templateUrl: './metric-delete-dialog.component.html'
})
export class MetricDeleteDialogComponent {

    metric: Metric;

    constructor(
        private metricService: MetricService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.metricService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'metricListModification',
                content: 'Deleted an metric'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-metric-delete-popup',
    template: ''
})
export class MetricDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private metricPopupService: MetricPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.metricPopupService
                .open(MetricDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
