import { BaseEntity } from './../../shared';

export class MetricValue implements BaseEntity {
    constructor(
        public id?: number,
        public value?: number,
        public faceValue?: string,
        public dimensions?: BaseEntity[],
        public metricId?: number,
    ) {
    }
}
