/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { InvoiceLineComponent } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line.component';
import { InvoiceLineService } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line.service';
import { InvoiceLine } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line.model';

describe('Component Tests', () => {

    describe('InvoiceLine Management Component', () => {
        let comp: InvoiceLineComponent;
        let fixture: ComponentFixture<InvoiceLineComponent>;
        let service: InvoiceLineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [InvoiceLineComponent],
                providers: [
                    InvoiceLineService
                ]
            })
            .overrideTemplate(InvoiceLineComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceLineComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceLineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InvoiceLine(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.invoiceLines[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
