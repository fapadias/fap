import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    CostGrideService,
    CostGridePopupService,
    CostGrideComponent,
    CostGrideDetailComponent,
    CostGrideDialogComponent,
    CostGridePopupComponent,
    CostGrideDeletePopupComponent,
    CostGrideDeleteDialogComponent,
    costGrideRoute,
    costGridePopupRoute,
} from './';

const ENTITY_STATES = [
    ...costGrideRoute,
    ...costGridePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CostGrideComponent,
        CostGrideDetailComponent,
        CostGrideDialogComponent,
        CostGrideDeleteDialogComponent,
        CostGridePopupComponent,
        CostGrideDeletePopupComponent,
    ],
    entryComponents: [
        CostGrideComponent,
        CostGrideDialogComponent,
        CostGridePopupComponent,
        CostGrideDeleteDialogComponent,
        CostGrideDeletePopupComponent,
    ],
    providers: [
        CostGrideService,
        CostGridePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationCostGrideModule {}
