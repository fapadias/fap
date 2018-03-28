import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Transporter } from './transporter.model';
import { TransporterService } from './transporter.service';

@Component({
    selector: 'jhi-transporter-detail',
    templateUrl: './transporter-detail.component.html'
})
export class TransporterDetailComponent implements OnInit, OnDestroy {

    transporter: Transporter;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private transporterService: TransporterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTransporters();
    }

    load(id) {
        this.transporterService.find(id)
            .subscribe((transporterResponse: HttpResponse<Transporter>) => {
                this.transporter = transporterResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTransporters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'transporterListModification',
            (response) => this.load(this.transporter.id)
        );
    }
}
