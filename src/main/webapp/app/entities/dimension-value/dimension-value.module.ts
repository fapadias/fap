import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    DimensionValueService,
    DimensionValuePopupService,
    DimensionValueComponent,
    DimensionValueDetailComponent,
    DimensionValueDialogComponent,
    DimensionValuePopupComponent,
    DimensionValueDeletePopupComponent,
    DimensionValueDeleteDialogComponent,
    dimensionValueRoute,
    dimensionValuePopupRoute,
} from './';

const ENTITY_STATES = [
    ...dimensionValueRoute,
    ...dimensionValuePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DimensionValueComponent,
        DimensionValueDetailComponent,
        DimensionValueDialogComponent,
        DimensionValueDeleteDialogComponent,
        DimensionValuePopupComponent,
        DimensionValueDeletePopupComponent,
    ],
    entryComponents: [
        DimensionValueComponent,
        DimensionValueDialogComponent,
        DimensionValuePopupComponent,
        DimensionValueDeleteDialogComponent,
        DimensionValueDeletePopupComponent,
    ],
    providers: [
        DimensionValueService,
        DimensionValuePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDimensionValueModule {}
