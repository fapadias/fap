import { BaseEntity } from './../../shared';

export class InvoiceLine implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public amount?: number,
        public entryDataId?: number,
        public attributes?: BaseEntity[],
    ) {
    }
}
