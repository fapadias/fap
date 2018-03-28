/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CostGrideComponent } from '../../../../../../main/webapp/app/entities/cost-gride/cost-gride.component';
import { CostGrideService } from '../../../../../../main/webapp/app/entities/cost-gride/cost-gride.service';
import { CostGride } from '../../../../../../main/webapp/app/entities/cost-gride/cost-gride.model';

describe('Component Tests', () => {

    describe('CostGride Management Component', () => {
        let comp: CostGrideComponent;
        let fixture: ComponentFixture<CostGrideComponent>;
        let service: CostGrideService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CostGrideComponent],
                providers: [
                    CostGrideService
                ]
            })
            .overrideTemplate(CostGrideComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostGrideComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostGrideService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CostGride(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.costGrides[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
