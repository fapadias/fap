/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DimensionDetailComponent } from '../../../../../../main/webapp/app/entities/dimension/dimension-detail.component';
import { DimensionService } from '../../../../../../main/webapp/app/entities/dimension/dimension.service';
import { Dimension } from '../../../../../../main/webapp/app/entities/dimension/dimension.model';

describe('Component Tests', () => {

    describe('Dimension Management Detail Component', () => {
        let comp: DimensionDetailComponent;
        let fixture: ComponentFixture<DimensionDetailComponent>;
        let service: DimensionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DimensionDetailComponent],
                providers: [
                    DimensionService
                ]
            })
            .overrideTemplate(DimensionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DimensionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DimensionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Dimension(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dimension).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
