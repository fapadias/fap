/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { InvoiceLineDialogComponent } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line-dialog.component';
import { InvoiceLineService } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line.service';
import { InvoiceLine } from '../../../../../../main/webapp/app/entities/invoice-line/invoice-line.model';
import { EntryDataService } from '../../../../../../main/webapp/app/entities/entry-data';

describe('Component Tests', () => {

    describe('InvoiceLine Management Dialog Component', () => {
        let comp: InvoiceLineDialogComponent;
        let fixture: ComponentFixture<InvoiceLineDialogComponent>;
        let service: InvoiceLineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [InvoiceLineDialogComponent],
                providers: [
                    EntryDataService,
                    InvoiceLineService
                ]
            })
            .overrideTemplate(InvoiceLineDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceLineDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceLineService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InvoiceLine(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.invoiceLine = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'invoiceLineListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InvoiceLine();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.invoiceLine = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'invoiceLineListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
