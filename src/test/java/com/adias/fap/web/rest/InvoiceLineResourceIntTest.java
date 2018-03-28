package com.adias.fap.web.rest;

import com.adias.fap.JhipsterSampleApplicationApp;

import com.adias.fap.domain.InvoiceLine;
import com.adias.fap.repository.InvoiceLineRepository;
import com.adias.fap.service.InvoiceLineService;
import com.adias.fap.service.dto.InvoiceLineDTO;
import com.adias.fap.service.mapper.InvoiceLineMapper;
import com.adias.fap.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.adias.fap.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvoiceLineResource REST controller.
 *
 * @see InvoiceLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class InvoiceLineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private InvoiceLineMapper invoiceLineMapper;

    @Autowired
    private InvoiceLineService invoiceLineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvoiceLineMockMvc;

    private InvoiceLine invoiceLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceLineResource invoiceLineResource = new InvoiceLineResource(invoiceLineService);
        this.restInvoiceLineMockMvc = MockMvcBuilders.standaloneSetup(invoiceLineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceLine createEntity(EntityManager em) {
        InvoiceLine invoiceLine = new InvoiceLine()
            .name(DEFAULT_NAME)
            .amount(DEFAULT_AMOUNT);
        return invoiceLine;
    }

    @Before
    public void initTest() {
        invoiceLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceLine() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(invoiceLine);
        restInvoiceLineMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInvoiceLine.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createInvoiceLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine with an existing ID
        invoiceLine.setId(1L);
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(invoiceLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceLineMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/{id}", invoiceLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceLine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceLine() throws Exception {
        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Update the invoiceLine
        InvoiceLine updatedInvoiceLine = invoiceLineRepository.findOne(invoiceLine.getId());
        // Disconnect from session so that the updates on updatedInvoiceLine are not directly saved in db
        em.detach(updatedInvoiceLine);
        updatedInvoiceLine
            .name(UPDATED_NAME)
            .amount(UPDATED_AMOUNT);
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(updatedInvoiceLine);

        restInvoiceLineMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInvoiceLine.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(invoiceLine);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvoiceLineMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);
        int databaseSizeBeforeDelete = invoiceLineRepository.findAll().size();

        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(delete("/api/invoice-lines/{id}", invoiceLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLine.class);
        InvoiceLine invoiceLine1 = new InvoiceLine();
        invoiceLine1.setId(1L);
        InvoiceLine invoiceLine2 = new InvoiceLine();
        invoiceLine2.setId(invoiceLine1.getId());
        assertThat(invoiceLine1).isEqualTo(invoiceLine2);
        invoiceLine2.setId(2L);
        assertThat(invoiceLine1).isNotEqualTo(invoiceLine2);
        invoiceLine1.setId(null);
        assertThat(invoiceLine1).isNotEqualTo(invoiceLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLineDTO.class);
        InvoiceLineDTO invoiceLineDTO1 = new InvoiceLineDTO();
        invoiceLineDTO1.setId(1L);
        InvoiceLineDTO invoiceLineDTO2 = new InvoiceLineDTO();
        assertThat(invoiceLineDTO1).isNotEqualTo(invoiceLineDTO2);
        invoiceLineDTO2.setId(invoiceLineDTO1.getId());
        assertThat(invoiceLineDTO1).isEqualTo(invoiceLineDTO2);
        invoiceLineDTO2.setId(2L);
        assertThat(invoiceLineDTO1).isNotEqualTo(invoiceLineDTO2);
        invoiceLineDTO1.setId(null);
        assertThat(invoiceLineDTO1).isNotEqualTo(invoiceLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoiceLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoiceLineMapper.fromId(null)).isNull();
    }
}
