import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Transporter } from './transporter.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Transporter>;

@Injectable()
export class TransporterService {

    private resourceUrl =  SERVER_API_URL + 'api/transporters';

    constructor(private http: HttpClient) { }

    create(transporter: Transporter): Observable<EntityResponseType> {
        const copy = this.convert(transporter);
        return this.http.post<Transporter>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(transporter: Transporter): Observable<EntityResponseType> {
        const copy = this.convert(transporter);
        return this.http.put<Transporter>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Transporter>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Transporter[]>> {
        const options = createRequestOption(req);
        return this.http.get<Transporter[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Transporter[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Transporter = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Transporter[]>): HttpResponse<Transporter[]> {
        const jsonResponse: Transporter[] = res.body;
        const body: Transporter[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Transporter.
     */
    private convertItemFromServer(transporter: Transporter): Transporter {
        const copy: Transporter = Object.assign({}, transporter);
        return copy;
    }

    /**
     * Convert a Transporter to a JSON which can be sent to the server.
     */
    private convert(transporter: Transporter): Transporter {
        const copy: Transporter = Object.assign({}, transporter);
        return copy;
    }
}
