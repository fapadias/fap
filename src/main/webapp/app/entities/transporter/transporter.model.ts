import { BaseEntity } from './../../shared';

export class Transporter implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public userId?: number,
    ) {
    }
}
