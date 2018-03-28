/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { InvoiceKeyValueComponent } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value.component';
import { InvoiceKeyValueService } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value.service';
import { InvoiceKeyValue } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value.model';

describe('Component Tests', () => {

    describe('InvoiceKeyValue Management Component', () => {
        let comp: InvoiceKeyValueComponent;
        let fixture: ComponentFixture<InvoiceKeyValueComponent>;
        let service: InvoiceKeyValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [InvoiceKeyValueComponent],
                providers: [
                    InvoiceKeyValueService
                ]
            })
            .overrideTemplate(InvoiceKeyValueComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceKeyValueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceKeyValueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InvoiceKeyValue(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.invoiceKeyValues[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
