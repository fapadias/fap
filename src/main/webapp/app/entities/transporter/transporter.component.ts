import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Transporter } from './transporter.model';
import { TransporterService } from './transporter.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-transporter',
    templateUrl: './transporter.component.html'
})
export class TransporterComponent implements OnInit, OnDestroy {
transporters: Transporter[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private transporterService: TransporterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.transporterService.query().subscribe(
            (res: HttpResponse<Transporter[]>) => {
                this.transporters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTransporters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Transporter) {
        return item.id;
    }
    registerChangeInTransporters() {
        this.eventSubscriber = this.eventManager.subscribe('transporterListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
