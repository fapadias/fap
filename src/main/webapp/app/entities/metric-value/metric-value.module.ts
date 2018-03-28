import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    MetricValueService,
    MetricValuePopupService,
    MetricValueComponent,
    MetricValueDetailComponent,
    MetricValueDialogComponent,
    MetricValuePopupComponent,
    MetricValueDeletePopupComponent,
    MetricValueDeleteDialogComponent,
    metricValueRoute,
    metricValuePopupRoute,
} from './';

const ENTITY_STATES = [
    ...metricValueRoute,
    ...metricValuePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MetricValueComponent,
        MetricValueDetailComponent,
        MetricValueDialogComponent,
        MetricValueDeleteDialogComponent,
        MetricValuePopupComponent,
        MetricValueDeletePopupComponent,
    ],
    entryComponents: [
        MetricValueComponent,
        MetricValueDialogComponent,
        MetricValuePopupComponent,
        MetricValueDeleteDialogComponent,
        MetricValueDeletePopupComponent,
    ],
    providers: [
        MetricValueService,
        MetricValuePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationMetricValueModule {}
