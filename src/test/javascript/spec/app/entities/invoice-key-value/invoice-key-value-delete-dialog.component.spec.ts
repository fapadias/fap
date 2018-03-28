/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { InvoiceKeyValueDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value-delete-dialog.component';
import { InvoiceKeyValueService } from '../../../../../../main/webapp/app/entities/invoice-key-value/invoice-key-value.service';

describe('Component Tests', () => {

    describe('InvoiceKeyValue Management Delete Component', () => {
        let comp: InvoiceKeyValueDeleteDialogComponent;
        let fixture: ComponentFixture<InvoiceKeyValueDeleteDialogComponent>;
        let service: InvoiceKeyValueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [InvoiceKeyValueDeleteDialogComponent],
                providers: [
                    InvoiceKeyValueService
                ]
            })
            .overrideTemplate(InvoiceKeyValueDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvoiceKeyValueDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceKeyValueService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
