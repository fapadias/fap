import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MetricComponent } from './metric.component';
import { MetricDetailComponent } from './metric-detail.component';
import { MetricPopupComponent } from './metric-dialog.component';
import { MetricDeletePopupComponent } from './metric-delete-dialog.component';

export const metricRoute: Routes = [
    {
        path: 'metric',
        component: MetricComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metric.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'metric/:id',
        component: MetricDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metric.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const metricPopupRoute: Routes = [
    {
        path: 'metric-new',
        component: MetricPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metric.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'metric/:id/edit',
        component: MetricPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metric.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'metric/:id/delete',
        component: MetricDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metric.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
