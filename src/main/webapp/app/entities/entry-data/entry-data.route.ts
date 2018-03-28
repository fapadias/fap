import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EntryDataComponent } from './entry-data.component';
import { EntryDataDetailComponent } from './entry-data-detail.component';
import { EntryDataPopupComponent } from './entry-data-dialog.component';
import { EntryDataDeletePopupComponent } from './entry-data-delete-dialog.component';

export const entryDataRoute: Routes = [
    {
        path: 'entry-data',
        component: EntryDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.entryData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entry-data/:id',
        component: EntryDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.entryData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entryDataPopupRoute: Routes = [
    {
        path: 'entry-data-new',
        component: EntryDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.entryData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entry-data/:id/edit',
        component: EntryDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.entryData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entry-data/:id/delete',
        component: EntryDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.entryData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
