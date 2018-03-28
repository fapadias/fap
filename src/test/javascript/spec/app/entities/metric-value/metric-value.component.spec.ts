/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MetricValueComponent } from '../../../../../../main/webapp/app/entities/metric-value/metric-value.component';
import { MetricValueService } from '../../../../../../main/webapp/app/entities/metric-value/metric-value.service';
import { MetricValue } from '../../../../../../main/webapp/app/entities/metric-value/metric-value.model';

describe('Component Tests', () => {

    describe('MetricValue Management Component', () => {
        let comp: MetricValueComponent;
        let fixture: ComponentFixture<MetricValueComponent>;
        let service: MetricValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [MetricValueComponent],
                providers: [
                    MetricValueService
                ]
            })
            .overrideTemplate(MetricValueComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MetricValueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MetricValueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MetricValue(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.metricValues[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
