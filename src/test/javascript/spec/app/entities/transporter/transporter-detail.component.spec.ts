/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransporterDetailComponent } from '../../../../../../main/webapp/app/entities/transporter/transporter-detail.component';
import { TransporterService } from '../../../../../../main/webapp/app/entities/transporter/transporter.service';
import { Transporter } from '../../../../../../main/webapp/app/entities/transporter/transporter.model';

describe('Component Tests', () => {

    describe('Transporter Management Detail Component', () => {
        let comp: TransporterDetailComponent;
        let fixture: ComponentFixture<TransporterDetailComponent>;
        let service: TransporterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TransporterDetailComponent],
                providers: [
                    TransporterService
                ]
            })
            .overrideTemplate(TransporterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransporterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransporterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Transporter(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.transporter).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
