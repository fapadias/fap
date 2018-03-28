import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationTransporterModule } from './transporter/transporter.module';
import { JhipsterSampleApplicationCostGrideModule } from './cost-gride/cost-gride.module';
import { JhipsterSampleApplicationDimensionModule } from './dimension/dimension.module';
import { JhipsterSampleApplicationDimensionValueModule } from './dimension-value/dimension-value.module';
import { JhipsterSampleApplicationMetricModule } from './metric/metric.module';
import { JhipsterSampleApplicationMetricValueModule } from './metric-value/metric-value.module';
import { JhipsterSampleApplicationEntryDataModule } from './entry-data/entry-data.module';
import { JhipsterSampleApplicationInvoiceLineModule } from './invoice-line/invoice-line.module';
import { JhipsterSampleApplicationInvoiceKeyValueModule } from './invoice-key-value/invoice-key-value.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationTransporterModule,
        JhipsterSampleApplicationCostGrideModule,
        JhipsterSampleApplicationDimensionModule,
        JhipsterSampleApplicationDimensionValueModule,
        JhipsterSampleApplicationMetricModule,
        JhipsterSampleApplicationMetricValueModule,
        JhipsterSampleApplicationEntryDataModule,
        JhipsterSampleApplicationInvoiceLineModule,
        JhipsterSampleApplicationInvoiceKeyValueModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
