import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import { JhipsterSampleApplicationAdminModule } from '../../admin/admin.module';
import {
    TransporterService,
    TransporterPopupService,
    TransporterComponent,
    TransporterDetailComponent,
    TransporterDialogComponent,
    TransporterPopupComponent,
    TransporterDeletePopupComponent,
    TransporterDeleteDialogComponent,
    transporterRoute,
    transporterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...transporterRoute,
    ...transporterPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        JhipsterSampleApplicationAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TransporterComponent,
        TransporterDetailComponent,
        TransporterDialogComponent,
        TransporterDeleteDialogComponent,
        TransporterPopupComponent,
        TransporterDeletePopupComponent,
    ],
    entryComponents: [
        TransporterComponent,
        TransporterDialogComponent,
        TransporterPopupComponent,
        TransporterDeleteDialogComponent,
        TransporterDeletePopupComponent,
    ],
    providers: [
        TransporterService,
        TransporterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTransporterModule {}
