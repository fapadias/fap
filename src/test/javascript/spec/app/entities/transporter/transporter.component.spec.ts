/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransporterComponent } from '../../../../../../main/webapp/app/entities/transporter/transporter.component';
import { TransporterService } from '../../../../../../main/webapp/app/entities/transporter/transporter.service';
import { Transporter } from '../../../../../../main/webapp/app/entities/transporter/transporter.model';

describe('Component Tests', () => {

    describe('Transporter Management Component', () => {
        let comp: TransporterComponent;
        let fixture: ComponentFixture<TransporterComponent>;
        let service: TransporterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TransporterComponent],
                providers: [
                    TransporterService
                ]
            })
            .overrideTemplate(TransporterComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransporterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransporterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Transporter(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.transporters[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
