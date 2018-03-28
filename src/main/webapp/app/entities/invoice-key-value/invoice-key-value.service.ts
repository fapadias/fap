import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { InvoiceKeyValue } from './invoice-key-value.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InvoiceKeyValue>;

@Injectable()
export class InvoiceKeyValueService {

    private resourceUrl =  SERVER_API_URL + 'api/invoice-key-values';

    constructor(private http: HttpClient) { }

    create(invoiceKeyValue: InvoiceKeyValue): Observable<EntityResponseType> {
        const copy = this.convert(invoiceKeyValue);
        return this.http.post<InvoiceKeyValue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(invoiceKeyValue: InvoiceKeyValue): Observable<EntityResponseType> {
        const copy = this.convert(invoiceKeyValue);
        return this.http.put<InvoiceKeyValue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InvoiceKeyValue>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InvoiceKeyValue[]>> {
        const options = createRequestOption(req);
        return this.http.get<InvoiceKeyValue[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InvoiceKeyValue[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InvoiceKeyValue = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InvoiceKeyValue[]>): HttpResponse<InvoiceKeyValue[]> {
        const jsonResponse: InvoiceKeyValue[] = res.body;
        const body: InvoiceKeyValue[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InvoiceKeyValue.
     */
    private convertItemFromServer(invoiceKeyValue: InvoiceKeyValue): InvoiceKeyValue {
        const copy: InvoiceKeyValue = Object.assign({}, invoiceKeyValue);
        return copy;
    }

    /**
     * Convert a InvoiceKeyValue to a JSON which can be sent to the server.
     */
    private convert(invoiceKeyValue: InvoiceKeyValue): InvoiceKeyValue {
        const copy: InvoiceKeyValue = Object.assign({}, invoiceKeyValue);
        return copy;
    }
}
