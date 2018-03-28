import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MetricValueComponent } from './metric-value.component';
import { MetricValueDetailComponent } from './metric-value-detail.component';
import { MetricValuePopupComponent } from './metric-value-dialog.component';
import { MetricValueDeletePopupComponent } from './metric-value-delete-dialog.component';

export const metricValueRoute: Routes = [
    {
        path: 'metric-value',
        component: MetricValueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metricValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'metric-value/:id',
        component: MetricValueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metricValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const metricValuePopupRoute: Routes = [
    {
        path: 'metric-value-new',
        component: MetricValuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metricValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'metric-value/:id/edit',
        component: MetricValuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metricValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'metric-value/:id/delete',
        component: MetricValueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.metricValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
