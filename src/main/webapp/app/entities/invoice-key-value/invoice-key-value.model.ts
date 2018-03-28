import { BaseEntity } from './../../shared';

export class InvoiceKeyValue implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public invoiceLineId?: number,
    ) {
    }
}
