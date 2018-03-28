/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CostGrideDetailComponent } from '../../../../../../main/webapp/app/entities/cost-gride/cost-gride-detail.component';
import { CostGrideService } from '../../../../../../main/webapp/app/entities/cost-gride/cost-gride.service';
import { CostGride } from '../../../../../../main/webapp/app/entities/cost-gride/cost-gride.model';

describe('Component Tests', () => {

    describe('CostGride Management Detail Component', () => {
        let comp: CostGrideDetailComponent;
        let fixture: ComponentFixture<CostGrideDetailComponent>;
        let service: CostGrideService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CostGrideDetailComponent],
                providers: [
                    CostGrideService
                ]
            })
            .overrideTemplate(CostGrideDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostGrideDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostGrideService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CostGride(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.costGride).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
