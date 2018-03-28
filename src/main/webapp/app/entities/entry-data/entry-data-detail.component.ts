import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EntryData } from './entry-data.model';
import { EntryDataService } from './entry-data.service';

@Component({
    selector: 'jhi-entry-data-detail',
    templateUrl: './entry-data-detail.component.html'
})
export class EntryDataDetailComponent implements OnInit, OnDestroy {

    entryData: EntryData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entryDataService: EntryDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntryData();
    }

    load(id) {
        this.entryDataService.find(id)
            .subscribe((entryDataResponse: HttpResponse<EntryData>) => {
                this.entryData = entryDataResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntryData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entryDataListModification',
            (response) => this.load(this.entryData.id)
        );
    }
}
