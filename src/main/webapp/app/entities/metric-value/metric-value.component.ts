import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MetricValue } from './metric-value.model';
import { MetricValueService } from './metric-value.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-metric-value',
    templateUrl: './metric-value.component.html'
})
export class MetricValueComponent implements OnInit, OnDestroy {
metricValues: MetricValue[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private metricValueService: MetricValueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.metricValueService.query().subscribe(
            (res: HttpResponse<MetricValue[]>) => {
                this.metricValues = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMetricValues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MetricValue) {
        return item.id;
    }
    registerChangeInMetricValues() {
        this.eventSubscriber = this.eventManager.subscribe('metricValueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
