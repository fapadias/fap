import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Dimension } from './dimension.model';
import { DimensionPopupService } from './dimension-popup.service';
import { DimensionService } from './dimension.service';
import { CostGride, CostGrideService } from '../cost-gride';
import { Metric, MetricService } from '../metric';

@Component({
    selector: 'jhi-dimension-dialog',
    templateUrl: './dimension-dialog.component.html'
})
export class DimensionDialogComponent implements OnInit {

    dimension: Dimension;
    isSaving: boolean;

    costgrides: CostGride[];

    metrics: Metric[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dimensionService: DimensionService,
        private costGrideService: CostGrideService,
        private metricService: MetricService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.costGrideService.query()
            .subscribe((res: HttpResponse<CostGride[]>) => { this.costgrides = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.metricService.query()
            .subscribe((res: HttpResponse<Metric[]>) => { this.metrics = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dimension.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dimensionService.update(this.dimension));
        } else {
            this.subscribeToSaveResponse(
                this.dimensionService.create(this.dimension));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Dimension>>) {
        result.subscribe((res: HttpResponse<Dimension>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Dimension) {
        this.eventManager.broadcast({ name: 'dimensionListModification', content: 'OK'});
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

    trackMetricById(index: number, item: Metric) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dimension-popup',
    template: ''
})
export class DimensionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dimensionPopupService: DimensionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dimensionPopupService
                    .open(DimensionDialogComponent as Component, params['id']);
            } else {
                this.dimensionPopupService
                    .open(DimensionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
