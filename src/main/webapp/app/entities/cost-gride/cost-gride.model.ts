import { BaseEntity } from './../../shared';

export class CostGride implements BaseEntity {
    constructor(
        public id?: number,
        public fileName?: string,
        public version?: string,
        public transporterId?: number,
    ) {
    }
}
