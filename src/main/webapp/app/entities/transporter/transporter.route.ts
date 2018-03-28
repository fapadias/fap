import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TransporterComponent } from './transporter.component';
import { TransporterDetailComponent } from './transporter-detail.component';
import { TransporterPopupComponent } from './transporter-dialog.component';
import { TransporterDeletePopupComponent } from './transporter-delete-dialog.component';

export const transporterRoute: Routes = [
    {
        path: 'transporter',
        component: TransporterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transporter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'transporter/:id',
        component: TransporterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transporter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transporterPopupRoute: Routes = [
    {
        path: 'transporter-new',
        component: TransporterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transporter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transporter/:id/edit',
        component: TransporterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transporter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transporter/:id/delete',
        component: TransporterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.transporter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
