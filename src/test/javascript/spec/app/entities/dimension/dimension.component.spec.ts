/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DimensionComponent } from '../../../../../../main/webapp/app/entities/dimension/dimension.component';
import { DimensionService } from '../../../../../../main/webapp/app/entities/dimension/dimension.service';
import { Dimension } from '../../../../../../main/webapp/app/entities/dimension/dimension.model';

describe('Component Tests', () => {

    describe('Dimension Management Component', () => {
        let comp: DimensionComponent;
        let fixture: ComponentFixture<DimensionComponent>;
        let service: DimensionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DimensionComponent],
                providers: [
                    DimensionService
                ]
            })
            .overrideTemplate(DimensionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DimensionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DimensionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Dimension(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dimensions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
