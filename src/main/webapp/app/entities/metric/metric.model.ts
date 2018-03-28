import { BaseEntity } from './../../shared';

export class Metric implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public dimensions?: BaseEntity[],
        public costGrideId?: number,
    ) {
    }
}
