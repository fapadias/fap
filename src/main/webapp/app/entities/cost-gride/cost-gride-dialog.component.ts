import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CostGride } from './cost-gride.model';
import { CostGridePopupService } from './cost-gride-popup.service';
import { CostGrideService } from './cost-gride.service';
import { Transporter, TransporterService } from '../transporter';

@Component({
    selector: 'jhi-cost-gride-dialog',
    templateUrl: './cost-gride-dialog.component.html'
})
export class CostGrideDialogComponent implements OnInit {

    costGride: CostGride;
    isSaving: boolean;

    transporters: Transporter[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private costGrideService: CostGrideService,
        private transporterService: TransporterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.transporterService.query()
            .subscribe((res: HttpResponse<Transporter[]>) => { this.transporters = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.costGride.id !== undefined) {
            this.subscribeToSaveResponse(
                this.costGrideService.update(this.costGride));
        } else {
            this.subscribeToSaveResponse(
                this.costGrideService.create(this.costGride));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CostGride>>) {
        result.subscribe((res: HttpResponse<CostGride>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CostGride) {
        this.eventManager.broadcast({ name: 'costGrideListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTransporterById(index: number, item: Transporter) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cost-gride-popup',
    template: ''
})
export class CostGridePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costGridePopupService: CostGridePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.costGridePopupService
                    .open(CostGrideDialogComponent as Component, params['id']);
            } else {
                this.costGridePopupService
                    .open(CostGrideDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
