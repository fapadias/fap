import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CostGride } from './cost-gride.model';
import { CostGrideService } from './cost-gride.service';

@Component({
    selector: 'jhi-cost-gride-detail',
    templateUrl: './cost-gride-detail.component.html'
})
export class CostGrideDetailComponent implements OnInit, OnDestroy {

    costGride: CostGride;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private costGrideService: CostGrideService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCostGrides();
    }

    load(id) {
        this.costGrideService.find(id)
            .subscribe((costGrideResponse: HttpResponse<CostGride>) => {
                this.costGride = costGrideResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCostGrides() {
        this.eventSubscriber = this.eventManager.subscribe(
            'costGrideListModification',
            (response) => this.load(this.costGride.id)
        );
    }
}
