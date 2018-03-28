import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    MetricService,
    MetricPopupService,
    MetricComponent,
    MetricDetailComponent,
    MetricDialogComponent,
    MetricPopupComponent,
    MetricDeletePopupComponent,
    MetricDeleteDialogComponent,
    metricRoute,
    metricPopupRoute,
} from './';

const ENTITY_STATES = [
    ...metricRoute,
    ...metricPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MetricComponent,
        MetricDetailComponent,
        MetricDialogComponent,
        MetricDeleteDialogComponent,
        MetricPopupComponent,
        MetricDeletePopupComponent,
    ],
    entryComponents: [
        MetricComponent,
        MetricDialogComponent,
        MetricPopupComponent,
        MetricDeleteDialogComponent,
        MetricDeletePopupComponent,
    ],
    providers: [
        MetricService,
        MetricPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationMetricModule {}
