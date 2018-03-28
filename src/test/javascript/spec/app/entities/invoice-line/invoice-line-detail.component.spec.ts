/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { InvoiceLineDetailComponent } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line-detail.component';
import { InvoiceLineService } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line.service';
import { InvoiceLine } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line.model';

describe('Component Tests', () => {

    describe('InvoiceLine Management Detail Component', () => {
        let comp: InvoiceLineDetailComponent;
        let fixture: ComponentFixture<InvoiceLineDetailComponent>;
        let service: InvoiceLineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [InvoiceLineDetailComponent],
                providers: [
                    InvoiceLineService
                ]
            })
            .overrideTemplate(InvoiceLineDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceLineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceLineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InvoiceLine(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.invoiceLine).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
