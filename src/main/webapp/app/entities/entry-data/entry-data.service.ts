import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EntryData } from './entry-data.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EntryData>;

@Injectable()
export class EntryDataService {

    private resourceUrl =  SERVER_API_URL + 'api/entry-data';

    constructor(private http: HttpClient) { }

    create(entryData: EntryData): Observable<EntityResponseType> {
        const copy = this.convert(entryData);
        return this.http.post<EntryData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(entryData: EntryData): Observable<EntityResponseType> {
        const copy = this.convert(entryData);
        return this.http.put<EntryData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EntryData>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EntryData[]>> {
        const options = createRequestOption(req);
        return this.http.get<EntryData[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EntryData[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EntryData = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EntryData[]>): HttpResponse<EntryData[]> {
        const jsonResponse: EntryData[] = res.body;
        const body: EntryData[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EntryData.
     */
    private convertItemFromServer(entryData: EntryData): EntryData {
        const copy: EntryData = Object.assign({}, entryData);
        return copy;
    }

    /**
     * Convert a EntryData to a JSON which can be sent to the server.
     */
    private convert(entryData: EntryData): EntryData {
        const copy: EntryData = Object.assign({}, entryData);
        return copy;
    }
}
