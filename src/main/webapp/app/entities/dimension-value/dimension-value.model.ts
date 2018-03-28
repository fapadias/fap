import { BaseEntity } from './../../shared';

export class DimensionValue implements BaseEntity {
    constructor(
        public id?: number,
        public stringValue?: string,
        public lowerBound?: string,
        public upperBound?: string,
        public dimensionId?: number,
        public metricValueId?: number,
    ) {
    }
}
