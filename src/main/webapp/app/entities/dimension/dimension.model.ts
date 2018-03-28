import { BaseEntity } from './../../shared';

export const enum DimensionType {
    'SIMPLE',
    'COMPOSITE'
}

export class Dimension implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: DimensionType,
        public costGrideId?: number,
        public metricId?: number,
    ) {
    }
}
