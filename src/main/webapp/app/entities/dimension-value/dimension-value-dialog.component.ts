import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DimensionValue } from './dimension-value.model';
import { DimensionValuePopupService } from './dimension-value-popup.service';
import { DimensionValueService } from './dimension-value.service';
import { Dimension, DimensionService } from '../dimension';
import { MetricValue, MetricValueService } from '../metric-value';

@Component({
    selector: 'jhi-dimension-value-dialog',
    templateUrl: './dimension-value-dialog.component.html'
})
export class DimensionValueDialogComponent implements OnInit {

    dimensionValue: DimensionValue;
    isSaving: boolean;

    dimensions: Dimension[];

    metricvalues: MetricValue[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dimensionValueService: DimensionValueService,
        private dimensionService: DimensionService,
        private metricValueService: MetricValueService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dimensionService.query()
            .subscribe((res: HttpResponse<Dimension[]>) => { this.dimensions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.metricValueService.query()
            .subscribe((res: HttpResponse<MetricValue[]>) => { this.metricvalues = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dimensionValue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dimensionValueService.update(this.dimensionValue));
        } else {
            this.subscribeToSaveResponse(
                this.dimensionValueService.create(this.dimensionValue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DimensionValue>>) {
        result.subscribe((res: HttpResponse<DimensionValue>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DimensionValue) {
        this.eventManager.broadcast({ name: 'dimensionValueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDimensionById(index: number, item: Dimension) {
        return item.id;
    }

    trackMetricValueById(index: number, item: MetricValue) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dimension-value-popup',
    template: ''
})
export class DimensionValuePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dimensionValuePopupService: DimensionValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dimensionValuePopupService
                    .open(DimensionValueDialogComponent as Component, params['id']);
            } else {
                this.dimensionValuePopupService
                    .open(DimensionValueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
