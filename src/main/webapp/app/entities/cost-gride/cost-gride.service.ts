import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CostGride } from './cost-gride.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CostGride>;

@Injectable()
export class CostGrideService {

    private resourceUrl =  SERVER_API_URL + 'api/cost-grides';

    constructor(private http: HttpClient) { }

    create(costGride: CostGride): Observable<EntityResponseType> {
        const copy = this.convert(costGride);
        return this.http.post<CostGride>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(costGride: CostGride): Observable<EntityResponseType> {
        const copy = this.convert(costGride);
        return this.http.put<CostGride>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CostGride>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CostGride[]>> {
        const options = createRequestOption(req);
        return this.http.get<CostGride[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CostGride[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CostGride = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CostGride[]>): HttpResponse<CostGride[]> {
        const jsonResponse: CostGride[] = res.body;
        const body: CostGride[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CostGride.
     */
    private convertItemFromServer(costGride: CostGride): CostGride {
        const copy: CostGride = Object.assign({}, costGride);
        return copy;
    }

    /**
     * Convert a CostGride to a JSON which can be sent to the server.
     */
    private convert(costGride: CostGride): CostGride {
        const copy: CostGride = Object.assign({}, costGride);
        return copy;
    }
}
