import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Metric } from './metric.model';
import { MetricPopupService } from './metric-popup.service';
import { MetricService } from './metric.service';
import { CostGride, CostGrideService } from '../cost-gride';

@Component({
    selector: 'jhi-metric-dialog',
    templateUrl: './metric-dialog.component.html'
})
export class MetricDialogComponent implements OnInit {

    metric: Metric;
    isSaving: boolean;

    costgrides: CostGride[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private metricService: MetricService,
        private costGrideService: CostGrideService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.costGrideService.query()
            .subscribe((res: HttpResponse<CostGride[]>) => { this.costgrides = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.metric.id !== undefined) {
            this.subscribeToSaveResponse(
                this.metricService.update(this.metric));
        } else {
            this.subscribeToSaveResponse(
                this.metricService.create(this.metric));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Metric>>) {
        result.subscribe((res: HttpResponse<Metric>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Metric) {
        this.eventManager.broadcast({ name: 'metricListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCostGrideById(index: number, item: CostGride) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-metric-popup',
    template: ''
})
export class MetricPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private metricPopupService: MetricPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.metricPopupService
                    .open(MetricDialogComponent as Component, params['id']);
            } else {
                this.metricPopupService
                    .open(MetricDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
