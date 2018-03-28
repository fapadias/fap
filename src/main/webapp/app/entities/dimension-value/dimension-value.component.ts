import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DimensionValue } from './dimension-value.model';
import { DimensionValueService } from './dimension-value.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-dimension-value',
    templateUrl: './dimension-value.component.html'
})
export class DimensionValueComponent implements OnInit, OnDestroy {
dimensionValues: DimensionValue[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dimensionValueService: DimensionValueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.dimensionValueService.query().subscribe(
            (res: HttpResponse<DimensionValue[]>) => {
                this.dimensionValues = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDimensionValues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DimensionValue) {
        return item.id;
    }
    registerChangeInDimensionValues() {
        this.eventSubscriber = this.eventManager.subscribe('dimensionValueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
