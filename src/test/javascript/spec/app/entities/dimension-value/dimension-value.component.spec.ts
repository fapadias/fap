/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DimensionValueComponent } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value.component';
import { DimensionValueService } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value.service';
import { DimensionValue } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value.model';

describe('Component Tests', () => {

    describe('DimensionValue Management Component', () => {
        let comp: DimensionValueComponent;
        let fixture: ComponentFixture<DimensionValueComponent>;
        let service: DimensionValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DimensionValueComponent],
                providers: [
                    DimensionValueService
                ]
            })
            .overrideTemplate(DimensionValueComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DimensionValueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DimensionValueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DimensionValue(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dimensionValues[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
