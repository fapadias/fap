import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    InvoiceKeyValueService,
    InvoiceKeyValuePopupService,
    InvoiceKeyValueComponent,
    InvoiceKeyValueDetailComponent,
    InvoiceKeyValueDialogComponent,
    InvoiceKeyValuePopupComponent,
    InvoiceKeyValueDeletePopupComponent,
    InvoiceKeyValueDeleteDialogComponent,
    invoiceKeyValueRoute,
    invoiceKeyValuePopupRoute,
} from './';

const ENTITY_STATES = [
    ...invoiceKeyValueRoute,
    ...invoiceKeyValuePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InvoiceKeyValueComponent,
        InvoiceKeyValueDetailComponent,
        InvoiceKeyValueDialogComponent,
        InvoiceKeyValueDeleteDialogComponent,
        InvoiceKeyValuePopupComponent,
        InvoiceKeyValueDeletePopupComponent,
    ],
    entryComponents: [
        InvoiceKeyValueComponent,
        InvoiceKeyValueDialogComponent,
        InvoiceKeyValuePopupComponent,
        InvoiceKeyValueDeleteDialogComponent,
        InvoiceKeyValueDeletePopupComponent,
    ],
    providers: [
        InvoiceKeyValueService,
        InvoiceKeyValuePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationInvoiceKeyValueModule {}
