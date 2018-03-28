import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CostGrideComponent } from './cost-gride.component';
import { CostGrideDetailComponent } from './cost-gride-detail.component';
import { CostGridePopupComponent } from './cost-gride-dialog.component';
import { CostGrideDeletePopupComponent } from './cost-gride-delete-dialog.component';

export const costGrideRoute: Routes = [
    {
        path: 'cost-gride',
        component: CostGrideComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.costGride.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cost-gride/:id',
        component: CostGrideDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.costGride.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const costGridePopupRoute: Routes = [
    {
        path: 'cost-gride-new',
        component: CostGridePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.costGride.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cost-gride/:id/edit',
        component: CostGridePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.costGride.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cost-gride/:id/delete',
        component: CostGrideDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.costGride.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
