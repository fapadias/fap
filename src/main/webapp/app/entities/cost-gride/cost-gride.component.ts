import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CostGride } from './cost-gride.model';
import { CostGrideService } from './cost-gride.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-cost-gride',
    templateUrl: './cost-gride.component.html'
})
export class CostGrideComponent implements OnInit, OnDestroy {
costGrides: CostGride[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private costGrideService: CostGrideService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.costGrideService.query().subscribe(
            (res: HttpResponse<CostGride[]>) => {
                this.costGrides = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCostGrides();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CostGride) {
        return item.id;
    }
    registerChangeInCostGrides() {
        this.eventSubscriber = this.eventManager.subscribe('costGrideListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
