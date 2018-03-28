import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MetricValue } from './metric-value.model';
import { MetricValuePopupService } from './metric-value-popup.service';
import { MetricValueService } from './metric-value.service';
import { Metric, MetricService } from '../metric';

@Component({
    selector: 'jhi-metric-value-dialog',
    templateUrl: './metric-value-dialog.component.html'
})
export class MetricValueDialogComponent implements OnInit {

    metricValue: MetricValue;
    isSaving: boolean;

    metrics: Metric[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private metricValueService: MetricValueService,
        private metricService: MetricService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.metricService.query()
            .subscribe((res: HttpResponse<Metric[]>) => { this.metrics = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.metricValue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.metricValueService.update(this.metricValue));
        } else {
            this.subscribeToSaveResponse(
                this.metricValueService.create(this.metricValue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MetricValue>>) {
        result.subscribe((res: HttpResponse<MetricValue>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MetricValue) {
        this.eventManager.broadcast({ name: 'metricValueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMetricById(index: number, item: Metric) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-metric-value-popup',
    template: ''
})
export class MetricValuePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private metricValuePopupService: MetricValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.metricValuePopupService
                    .open(MetricValueDialogComponent as Component, params['id']);
            } else {
                this.metricValuePopupService
                    .open(MetricValueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
