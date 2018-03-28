import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MetricValue } from './metric-value.model';
import { MetricValueService } from './metric-value.service';

@Component({
    selector: 'jhi-metric-value-detail',
    templateUrl: './metric-value-detail.component.html'
})
export class MetricValueDetailComponent implements OnInit, OnDestroy {

    metricValue: MetricValue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private metricValueService: MetricValueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMetricValues();
    }

    load(id) {
        this.metricValueService.find(id)
            .subscribe((metricValueResponse: HttpResponse<MetricValue>) => {
                this.metricValue = metricValueResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMetricValues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'metricValueListModification',
            (response) => this.load(this.metricValue.id)
        );
    }
}
