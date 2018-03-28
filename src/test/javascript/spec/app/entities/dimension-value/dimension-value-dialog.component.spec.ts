/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DimensionValueDialogComponent } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value-dialog.component';
import { DimensionValueService } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value.service';
import { DimensionValue } from '../../../../../../main/webapp/app/entities/dimension-value/dimension-value.model';
import { DimensionService } from '../../../../../../main/webapp/app/entities/dimension';
import { MetricValueService } from '../../../../../../main/webapp/app/entities/metric-value';

describe('Component Tests', () => {

    describe('DimensionValue Management Dialog Component', () => {
        let comp: DimensionValueDialogComponent;
        let fixture: ComponentFixture<DimensionValueDialogComponent>;
        let service: DimensionValueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DimensionValueDialogComponent],
                providers: [
                    DimensionService,
                    MetricValueService,
                    DimensionValueService
                ]
            })
            .overrideTemplate(DimensionValueDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DimensionValueDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DimensionValueService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DimensionValue(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dimensionValue = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dimensionValueListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DimensionValue();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dimensionValue = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dimensionValueListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
