import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntryData } from './entry-data.model';
import { EntryDataService } from './entry-data.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-entry-data',
    templateUrl: './entry-data.component.html'
})
export class EntryDataComponent implements OnInit, OnDestroy {
entryData: EntryData[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private entryDataService: EntryDataService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.entryDataService.query().subscribe(
            (res: HttpResponse<EntryData[]>) => {
                this.entryData = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEntryData();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EntryData) {
        return item.id;
    }
    registerChangeInEntryData() {
        this.eventSubscriber = this.eventManager.subscribe('entryDataListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
