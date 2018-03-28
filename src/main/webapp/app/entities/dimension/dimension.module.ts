import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    DimensionService,
    DimensionPopupService,
    DimensionComponent,
    DimensionDetailComponent,
    DimensionDialogComponent,
    DimensionPopupComponent,
    DimensionDeletePopupComponent,
    DimensionDeleteDialogComponent,
    dimensionRoute,
    dimensionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dimensionRoute,
    ...dimensionPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DimensionComponent,
        DimensionDetailComponent,
        DimensionDialogComponent,
        DimensionDeleteDialogComponent,
        DimensionPopupComponent,
        DimensionDeletePopupComponent,
    ],
    entryComponents: [
        DimensionComponent,
        DimensionDialogComponent,
        DimensionPopupComponent,
        DimensionDeleteDialogComponent,
        DimensionDeletePopupComponent,
    ],
    providers: [
        DimensionService,
        DimensionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDimensionModule {}
