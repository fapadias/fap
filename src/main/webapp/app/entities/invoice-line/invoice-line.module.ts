import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    InvoiceLineService,
    InvoiceLinePopupService,
    InvoiceLineComponent,
    InvoiceLineDetailComponent,
    InvoiceLineDialogComponent,
    InvoiceLinePopupComponent,
    InvoiceLineDeletePopupComponent,
    InvoiceLineDeleteDialogComponent,
    invoiceLineRoute,
    invoiceLinePopupRoute,
} from './';

const ENTITY_STATES = [
    ...invoiceLineRoute,
    ...invoiceLinePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InvoiceLineComponent,
        InvoiceLineDetailComponent,
        InvoiceLineDialogComponent,
        InvoiceLineDeleteDialogComponent,
        InvoiceLinePopupComponent,
        InvoiceLineDeletePopupComponent,
    ],
    entryComponents: [
        InvoiceLineComponent,
        InvoiceLineDialogComponent,
        InvoiceLinePopupComponent,
        InvoiceLineDeleteDialogComponent,
        InvoiceLineDeletePopupComponent,
    ],
    providers: [
        InvoiceLineService,
        InvoiceLinePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationInvoiceLineModule {}
