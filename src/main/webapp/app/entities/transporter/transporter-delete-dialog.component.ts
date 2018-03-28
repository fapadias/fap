import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Transporter } from './transporter.model';
import { TransporterPopupService } from './transporter-popup.service';
import { TransporterService } from './transporter.service';

@Component({
    selector: 'jhi-transporter-delete-dialog',
    templateUrl: './transporter-delete-dialog.component.html'
})
export class TransporterDeleteDialogComponent {

    transporter: Transporter;

    constructor(
        private transporterService: TransporterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transporterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'transporterListModification',
                content: 'Deleted an transporter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transporter-delete-popup',
    template: ''
})
export class TransporterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transporterPopupService: TransporterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.transporterPopupService
                .open(TransporterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
