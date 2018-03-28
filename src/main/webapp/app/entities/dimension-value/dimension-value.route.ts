import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DimensionValueComponent } from './dimension-value.component';
import { DimensionValueDetailComponent } from './dimension-value-detail.component';
import { DimensionValuePopupComponent } from './dimension-value-dialog.component';
import { DimensionValueDeletePopupComponent } from './dimension-value-delete-dialog.component';

export const dimensionValueRoute: Routes = [
    {
        path: 'dimension-value',
        component: DimensionValueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimensionValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dimension-value/:id',
        component: DimensionValueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimensionValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dimensionValuePopupRoute: Routes = [
    {
        path: 'dimension-value-new',
        component: DimensionValuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimensionValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dimension-value/:id/edit',
        component: DimensionValuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimensionValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dimension-value/:id/delete',
        component: DimensionValueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimensionValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
