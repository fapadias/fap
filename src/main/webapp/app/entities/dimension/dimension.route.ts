import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DimensionComponent } from './dimension.component';
import { DimensionDetailComponent } from './dimension-detail.component';
import { DimensionPopupComponent } from './dimension-dialog.component';
import { DimensionDeletePopupComponent } from './dimension-delete-dialog.component';

export const dimensionRoute: Routes = [
    {
        path: 'dimension',
        component: DimensionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimension.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dimension/:id',
        component: DimensionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimension.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dimensionPopupRoute: Routes = [
    {
        path: 'dimension-new',
        component: DimensionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimension.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dimension/:id/edit',
        component: DimensionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimension.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dimension/:id/delete',
        component: DimensionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.dimension.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
