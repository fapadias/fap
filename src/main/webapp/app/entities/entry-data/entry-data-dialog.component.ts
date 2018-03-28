import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntryData } from './entry-data.model';
import { EntryDataPopupService } from './entry-data-popup.service';
import { EntryDataService } from './entry-data.service';
import { Transporter, TransporterService } from '../transporter';

@Component({
    selector: 'jhi-entry-data-dialog',
    templateUrl: './entry-data-dialog.component.html'
})
export class EntryDataDialogComponent implements OnInit {

    entryData: EntryData;
    isSaving: boolean;

    transporters: Transporter[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private entryDataService: EntryDataService,
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
        if (this.entryData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entryDataService.update(this.entryData));
        } else {
            this.subscribeToSaveResponse(
                this.entryDataService.create(this.entryData));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EntryData>>) {
        result.subscribe((res: HttpResponse<EntryData>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EntryData) {
        this.eventManager.broadcast({ name: 'entryDataListModification', content: 'OK'});
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
    selector: 'jhi-entry-data-popup',
    template: ''
})
export class EntryDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entryDataPopupService: EntryDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.entryDataPopupService
                    .open(EntryDataDialogComponent as Component, params['id']);
            } else {
                this.entryDataPopupService
                    .open(EntryDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
