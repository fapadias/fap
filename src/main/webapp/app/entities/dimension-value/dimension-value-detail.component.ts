import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DimensionValue } from './dimension-value.model';
import { DimensionValueService } from './dimension-value.service';

@Component({
    selector: 'jhi-dimension-value-detail',
    templateUrl: './dimension-value-detail.component.html'
})
export class DimensionValueDetailComponent implements OnInit, OnDestroy {

    dimensionValue: DimensionValue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dimensionValueService: DimensionValueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDimensionValues();
    }

    load(id) {
        this.dimensionValueService.find(id)
            .subscribe((dimensionValueResponse: HttpResponse<DimensionValue>) => {
                this.dimensionValue = dimensionValueResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDimensionValues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dimensionValueListModification',
            (response) => this.load(this.dimensionValue.id)
        );
    }
}
