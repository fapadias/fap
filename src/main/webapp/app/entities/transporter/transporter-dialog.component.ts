import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Transporter } from './transporter.model';
import { TransporterPopupService } from './transporter-popup.service';
import { TransporterService } from './transporter.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-transporter-dialog',
    templateUrl: './transporter-dialog.component.html'
})
export class TransporterDialogComponent implements OnInit {

    transporter: Transporter;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private transporterService: TransporterService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.transporter.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transporterService.update(this.transporter));
        } else {
            this.subscribeToSaveResponse(
                this.transporterService.create(this.transporter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Transporter>>) {
        result.subscribe((res: HttpResponse<Transporter>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Transporter) {
        this.eventManager.broadcast({ name: 'transporterListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transporter-popup',
    template: ''
})
export class TransporterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transporterPopupService: TransporterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.transporterPopupService
                    .open(TransporterDialogComponent as Component, params['id']);
            } else {
                this.transporterPopupService
                    .open(TransporterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
