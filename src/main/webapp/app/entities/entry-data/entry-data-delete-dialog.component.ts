import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EntryData } from './entry-data.model';
import { EntryDataPopupService } from './entry-data-popup.service';
import { EntryDataService } from './entry-data.service';

@Component({
    selector: 'jhi-entry-data-delete-dialog',
    templateUrl: './entry-data-delete-dialog.component.html'
})
export class EntryDataDeleteDialogComponent {

    entryData: EntryData;

    constructor(
        private entryDataService: EntryDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entryDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entryDataListModification',
                content: 'Deleted an entryData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entry-data-delete-popup',
    template: ''
})
export class EntryDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entryDataPopupService: EntryDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.entryDataPopupService
                .open(EntryDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
