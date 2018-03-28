/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EntryDataDetailComponent } from '../../../../../../main/webapp/app/entities/entry-data/entry-data-detail.component';
import { EntryDataService } from '../../../../../../main/webapp/app/entities/entry-data/entry-data.service';
import { EntryData } from '../../../../../../main/webapp/app/entities/entry-data/entry-data.model';

describe('Component Tests', () => {

    describe('EntryData Management Detail Component', () => {
        let comp: EntryDataDetailComponent;
        let fixture: ComponentFixture<EntryDataDetailComponent>;
        let service: EntryDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [EntryDataDetailComponent],
                providers: [
                    EntryDataService
                ]
            })
            .overrideTemplate(EntryDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntryDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntryDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EntryData(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.entryData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
