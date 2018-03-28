import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    EntryDataService,
    EntryDataPopupService,
    EntryDataComponent,
    EntryDataDetailComponent,
    EntryDataDialogComponent,
    EntryDataPopupComponent,
    EntryDataDeletePopupComponent,
    EntryDataDeleteDialogComponent,
    entryDataRoute,
    entryDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...entryDataRoute,
    ...entryDataPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EntryDataComponent,
        EntryDataDetailComponent,
        EntryDataDialogComponent,
        EntryDataDeleteDialogComponent,
        EntryDataPopupComponent,
        EntryDataDeletePopupComponent,
    ],
    entryComponents: [
        EntryDataComponent,
        EntryDataDialogComponent,
        EntryDataPopupComponent,
        EntryDataDeleteDialogComponent,
        EntryDataDeletePopupComponent,
    ],
    providers: [
        EntryDataService,
        EntryDataPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntryDataModule {}
