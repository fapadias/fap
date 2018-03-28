import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Metric } from './metric.model';
import { MetricService } from './metric.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-metric',
    templateUrl: './metric.component.html'
})
export class MetricComponent implements OnInit, OnDestroy {
metrics: Metric[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private metricService: MetricService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.metricService.query().subscribe(
            (res: HttpResponse<Metric[]>) => {
                this.metrics = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMetrics();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Metric) {
        return item.id;
    }
    registerChangeInMetrics() {
        this.eventSubscriber = this.eventManager.subscribe('metricListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
