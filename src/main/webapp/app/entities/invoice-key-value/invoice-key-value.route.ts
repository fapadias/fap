import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { InvoiceKeyValueComponent } from './invoice-key-value.component';
import { InvoiceKeyValueDetailComponent } from './invoice-key-value-detail.component';
import { InvoiceKeyValuePopupComponent } from './invoice-key-value-dialog.component';
import { InvoiceKeyValueDeletePopupComponent } from './invoice-key-value-delete-dialog.component';

export const invoiceKeyValueRoute: Routes = [
    {
        path: 'invoice-key-value',
        component: InvoiceKeyValueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.invoiceKeyValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'invoice-key-value/:id',
        component: InvoiceKeyValueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.invoiceKeyValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invoiceKeyValuePopupRoute: Routes = [
    {
        path: 'invoice-key-value-new',
        component: InvoiceKeyValuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.invoiceKeyValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invoice-key-value/:id/edit',
        component: InvoiceKeyValuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.invoiceKeyValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invoice-key-value/:id/delete',
        component: InvoiceKeyValueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.invoiceKeyValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
