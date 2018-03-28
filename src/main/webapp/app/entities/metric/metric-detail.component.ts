import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Metric } from './metric.model';
import { MetricService } from './metric.service';

@Component({
    selector: 'jhi-metric-detail',
    templateUrl: './metric-detail.component.html'
})
export class MetricDetailComponent implements OnInit, OnDestroy {

    metric: Metric;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private metricService: MetricService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMetrics();
    }

    load(id) {
        this.metricService.find(id)
            .subscribe((metricResponse: HttpResponse<Metric>) => {
                this.metric = metricResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMetrics() {
        this.eventSubscriber = this.eventManager.subscribe(
            'metricListModification',
            (response) => this.load(this.metric.id)
        );
    }
}
