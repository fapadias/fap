/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MetricValueDetailComponent } from '../../../../../../main/webapp/app/entities/metric-value/metric-value-detail.component';
import { MetricValueService } from '../../../../../../main/webapp/app/entities/metric-value/metric-value.service';
import { MetricValue } from '../../../../../../main/webapp/app/entities/metric-value/metric-value.model';

describe('Component Tests', () => {

    describe('MetricValue Management Detail Component', () => {
        let comp: MetricValueDetailComponent;
        let fixture: ComponentFixture<MetricValueDetailComponent>;
        let service: MetricValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [MetricValueDetailComponent],
                providers: [
                    MetricValueService
                ]
            })
            .overrideTemplate(MetricValueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MetricValueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MetricValueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MetricValue(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.metricValue).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
