/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MetricComponent } from '../../../../../../main/webapp/app/entities/metric/metric.component';
import { MetricService } from '../../../../../../main/webapp/app/entities/metric/metric.service';
import { Metric } from '../../../../../../main/webapp/app/entities/metric/metric.model';

describe('Component Tests', () => {

    describe('Metric Management Component', () => {
        let comp: MetricComponent;
        let fixture: ComponentFixture<MetricComponent>;
        let service: MetricService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [MetricComponent],
                providers: [
                    MetricService
                ]
            })
            .overrideTemplate(MetricComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MetricComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MetricService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Metric(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.metrics[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
