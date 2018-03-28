import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Dimension } from './dimension.model';
import { DimensionService } from './dimension.service';

@Component({
    selector: 'jhi-dimension-detail',
    templateUrl: './dimension-detail.component.html'
})
export class DimensionDetailComponent implements OnInit, OnDestroy {

    dimension: Dimension;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dimensionService: DimensionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDimensions();
    }

    load(id) {
        this.dimensionService.find(id)
            .subscribe((dimensionResponse: HttpResponse<Dimension>) => {
                this.dimension = dimensionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDimensions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dimensionListModification',
            (response) => this.load(this.dimension.id)
        );
    }
}
