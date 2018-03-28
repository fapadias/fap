import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MetricValue } from './metric-value.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MetricValue>;

@Injectable()
export class MetricValueService {

    private resourceUrl =  SERVER_API_URL + 'api/metric-values';

    constructor(private http: HttpClient) { }

    create(metricValue: MetricValue): Observable<EntityResponseType> {
        const copy = this.convert(metricValue);
        return this.http.post<MetricValue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(metricValue: MetricValue): Observable<EntityResponseType> {
        const copy = this.convert(metricValue);
        return this.http.put<MetricValue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MetricValue>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MetricValue[]>> {
        const options = createRequestOption(req);
        return this.http.get<MetricValue[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MetricValue[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MetricValue = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MetricValue[]>): HttpResponse<MetricValue[]> {
        const jsonResponse: MetricValue[] = res.body;
        const body: MetricValue[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MetricValue.
     */
    private convertItemFromServer(metricValue: MetricValue): MetricValue {
        const copy: MetricValue = Object.assign({}, metricValue);
        return copy;
    }

    /**
     * Convert a MetricValue to a JSON which can be sent to the server.
     */
    private convert(metricValue: MetricValue): MetricValue {
        const copy: MetricValue = Object.assign({}, metricValue);
        return copy;
    }
}
