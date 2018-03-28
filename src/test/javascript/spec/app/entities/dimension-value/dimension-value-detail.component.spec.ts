/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DimensionValueDetailComponent } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value-detail.component';
import { DimensionValueService } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value.service';
import { DimensionValue } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value.model';

describe('Component Tests', () => {

    describe('DimensionValue Management Detail Component', () => {
        let comp: DimensionValueDetailComponent;
        let fixture: ComponentFixture<DimensionValueDetailComponent>;
        let service: DimensionValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DimensionValueDetailComponent],
                providers: [
                    DimensionValueService
                ]
            })
            .overrideTemplate(DimensionValueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DimensionValueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DimensionValueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DimensionValue(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dimensionValue).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
