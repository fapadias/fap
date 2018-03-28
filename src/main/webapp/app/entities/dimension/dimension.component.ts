import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Dimension } from './dimension.model';
import { DimensionService } from './dimension.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-dimension',
    templateUrl: './dimension.component.html'
})
export class DimensionComponent implements OnInit, OnDestroy {
dimensions: Dimension[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dimensionService: DimensionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.dimensionService.query().subscribe(
            (res: HttpResponse<Dimension[]>) => {
                this.dimensions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDimensions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Dimension) {
        return item.id;
    }
    registerChangeInDimensions() {
        this.eventSubscriber = this.eventManager.subscribe('dimensionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
