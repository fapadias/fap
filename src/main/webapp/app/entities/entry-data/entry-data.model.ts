import { BaseEntity } from './../../shared';

export class EntryData implements BaseEntity {
    constructor(
        public id?: number,
        public documentName?: string,
        public invoiceTotal?: number,
        public lines?: BaseEntity[],
        public transporterId?: number,
    ) {
    }
}
