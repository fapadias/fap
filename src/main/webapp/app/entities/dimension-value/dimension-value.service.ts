import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DimensionValue } from './dimension-value.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DimensionValue>;

@Injectable()
export class DimensionValueService {

    private resourceUrl =  SERVER_API_URL + 'api/dimension-values';

    constructor(private http: HttpClient) { }

    create(dimensionValue: DimensionValue): Observable<EntityResponseType> {
        const copy = this.convert(dimensionValue);
        return this.http.post<DimensionValue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dimensionValue: DimensionValue): Observable<EntityResponseType> {
        const copy = this.convert(dimensionValue);
        return this.http.put<DimensionValue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DimensionValue>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DimensionValue[]>> {
        const options = createRequestOption(req);
        return this.http.get<DimensionValue[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DimensionValue[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DimensionValue = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DimensionValue[]>): HttpResponse<DimensionValue[]> {
        const jsonResponse: DimensionValue[] = res.body;
        const body: DimensionValue[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DimensionValue.
     */
    private convertItemFromServer(dimensionValue: DimensionValue): DimensionValue {
        const copy: DimensionValue = Object.assign({}, dimensionValue);
        return copy;
    }

    /**
     * Convert a DimensionValue to a JSON which can be sent to the server.
     */
    private convert(dimensionValue: DimensionValue): DimensionValue {
        const copy: DimensionValue = Object.assign({}, dimensionValue);
        return copy;
    }
}
