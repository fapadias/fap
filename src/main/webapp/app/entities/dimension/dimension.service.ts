import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Dimension } from './dimension.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Dimension>;

@Injectable()
export class DimensionService {

    private resourceUrl =  SERVER_API_URL + 'api/dimensions';

    constructor(private http: HttpClient) { }

    create(dimension: Dimension): Observable<EntityResponseType> {
        const copy = this.convert(dimension);
        return this.http.post<Dimension>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dimension: Dimension): Observable<EntityResponseType> {
        const copy = this.convert(dimension);
        return this.http.put<Dimension>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Dimension>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Dimension[]>> {
        const options = createRequestOption(req);
        return this.http.get<Dimension[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Dimension[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Dimension = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Dimension[]>): HttpResponse<Dimension[]> {
        const jsonResponse: Dimension[] = res.body;
        const body: Dimension[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Dimension.
     */
    private convertItemFromServer(dimension: Dimension): Dimension {
        const copy: Dimension = Object.assign({}, dimension);
        return copy;
    }

    /**
     * Convert a Dimension to a JSON which can be sent to the server.
     */
    private convert(dimension: Dimension): Dimension {
        const copy: Dimension = Object.assign({}, dimension);
        return copy;
    }
}
