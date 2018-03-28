/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { InvoiceKeyValueDetailComponent } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value-detail.component';
import { InvoiceKeyValueService } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value.service';
import { InvoiceKeyValue } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value.model';

describe('Component Tests', () => {

    describe('InvoiceKeyValue Management Detail Component', () => {
        let comp: InvoiceKeyValueDetailComponent;
        let fixture: ComponentFixture<InvoiceKeyValueDetailComponent>;
        let service: InvoiceKeyValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [InvoiceKeyValueDetailComponent],
                providers: [
                    InvoiceKeyValueService
                ]
            })
            .overrideTemplate(InvoiceKeyValueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceKeyValueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceKeyValueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InvoiceKeyValue(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.invoiceKeyValue).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
