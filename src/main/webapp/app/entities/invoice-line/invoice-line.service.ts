import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { InvoiceLine } from './invoice-line.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InvoiceLine>;

@Injectable()
export class InvoiceLineService {

    private resourceUrl =  SERVER_API_URL + 'api/invoice-lines';

    constructor(private http: HttpClient) { }

    create(invoiceLine: InvoiceLine): Observable<EntityResponseType> {
        const copy = this.convert(invoiceLine);
        return this.http.post<InvoiceLine>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(invoiceLine: InvoiceLine): Observable<EntityResponseType> {
        const copy = this.convert(invoiceLine);
        return this.http.put<InvoiceLine>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InvoiceLine>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InvoiceLine[]>> {
        const options = createRequestOption(req);
        return this.http.get<InvoiceLine[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InvoiceLine[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InvoiceLine = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InvoiceLine[]>): HttpResponse<InvoiceLine[]> {
        const jsonResponse: InvoiceLine[] = res.body;
        const body: InvoiceLine[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InvoiceLine.
     */
    private convertItemFromServer(invoiceLine: InvoiceLine): InvoiceLine {
        const copy: InvoiceLine = Object.assign({}, invoiceLine);
        return copy;
    }

    /**
     * Convert a InvoiceLine to a JSON which can be sent to the server.
     */
    private convert(invoiceLine: InvoiceLine): InvoiceLine {
        const copy: InvoiceLine = Object.assign({}, invoiceLine);
        return copy;
    }
}
