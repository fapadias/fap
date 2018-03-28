/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EntryDataComponent } from '../../../../../../main/webapp/app/entities/entry-data/entry-data.component';
import { EntryDataService } from '../../../../../../main/webapp/app/entities/entry-data/entry-data.service';
import { EntryData } from '../../../../../../main/webapp/app/entities/entry-data/entry-data.model';

describe('Component Tests', () => {

    describe('EntryData Management Component', () => {
        let comp: EntryDataComponent;
        let fixture: ComponentFixture<EntryDataComponent>;
        let service: EntryDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [EntryDataComponent],
                providers: [
                    EntryDataService
                ]
            })
            .overrideTemplate(EntryDataComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntryDataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntryDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EntryData(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.entryData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
