package com.adias.fap.web.rest;

import com.adias.fap.JhipsterSampleApplicationApp;

import com.adias.fap.domain.InvoiceKeyValue;
import com.adias.fap.repository.InvoiceKeyValueRepository;
import com.adias.fap.service.InvoiceKeyValueService;
import com.adias.fap.service.dto.InvoiceKeyValueDTO;
import com.adias.fap.service.mapper.InvoiceKeyValueMapper;
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
 * Test class for the InvoiceKeyValueResource REST controller.
 *
 * @see InvoiceKeyValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class InvoiceKeyValueResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private InvoiceKeyValueRepository invoiceKeyValueRepository;

    @Autowired
    private InvoiceKeyValueMapper invoiceKeyValueMapper;

    @Autowired
    private InvoiceKeyValueService invoiceKeyValueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvoiceKeyValueMockMvc;

    private InvoiceKeyValue invoiceKeyValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceKeyValueResource invoiceKeyValueResource = new InvoiceKeyValueResource(invoiceKeyValueService);
        this.restInvoiceKeyValueMockMvc = MockMvcBuilders.standaloneSetup(invoiceKeyValueResource)
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
    public static InvoiceKeyValue createEntity(EntityManager em) {
        InvoiceKeyValue invoiceKeyValue = new InvoiceKeyValue()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return invoiceKeyValue;
    }

    @Before
    public void initTest() {
        invoiceKeyValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceKeyValue() throws Exception {
        int databaseSizeBeforeCreate = invoiceKeyValueRepository.findAll().size();

        // Create the InvoiceKeyValue
        InvoiceKeyValueDTO invoiceKeyValueDTO = invoiceKeyValueMapper.toDto(invoiceKeyValue);
        restInvoiceKeyValueMockMvc.perform(post("/api/invoice-key-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceKeyValueDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceKeyValue in the database
        List<InvoiceKeyValue> invoiceKeyValueList = invoiceKeyValueRepository.findAll();
        assertThat(invoiceKeyValueList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceKeyValue testInvoiceKeyValue = invoiceKeyValueList.get(invoiceKeyValueList.size() - 1);
        assertThat(testInvoiceKeyValue.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testInvoiceKeyValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createInvoiceKeyValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceKeyValueRepository.findAll().size();

        // Create the InvoiceKeyValue with an existing ID
        invoiceKeyValue.setId(1L);
        InvoiceKeyValueDTO invoiceKeyValueDTO = invoiceKeyValueMapper.toDto(invoiceKeyValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceKeyValueMockMvc.perform(post("/api/invoice-key-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceKeyValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceKeyValue in the database
        List<InvoiceKeyValue> invoiceKeyValueList = invoiceKeyValueRepository.findAll();
        assertThat(invoiceKeyValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvoiceKeyValues() throws Exception {
        // Initialize the database
        invoiceKeyValueRepository.saveAndFlush(invoiceKeyValue);

        // Get all the invoiceKeyValueList
        restInvoiceKeyValueMockMvc.perform(get("/api/invoice-key-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceKeyValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getInvoiceKeyValue() throws Exception {
        // Initialize the database
        invoiceKeyValueRepository.saveAndFlush(invoiceKeyValue);

        // Get the invoiceKeyValue
        restInvoiceKeyValueMockMvc.perform(get("/api/invoice-key-values/{id}", invoiceKeyValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceKeyValue.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceKeyValue() throws Exception {
        // Get the invoiceKeyValue
        restInvoiceKeyValueMockMvc.perform(get("/api/invoice-key-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceKeyValue() throws Exception {
        // Initialize the database
        invoiceKeyValueRepository.saveAndFlush(invoiceKeyValue);
        int databaseSizeBeforeUpdate = invoiceKeyValueRepository.findAll().size();

        // Update the invoiceKeyValue
        InvoiceKeyValue updatedInvoiceKeyValue = invoiceKeyValueRepository.findOne(invoiceKeyValue.getId());
        // Disconnect from session so that the updates on updatedInvoiceKeyValue are not directly saved in db
        em.detach(updatedInvoiceKeyValue);
        updatedInvoiceKeyValue
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        InvoiceKeyValueDTO invoiceKeyValueDTO = invoiceKeyValueMapper.toDto(updatedInvoiceKeyValue);

        restInvoiceKeyValueMockMvc.perform(put("/api/invoice-key-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceKeyValueDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceKeyValue in the database
        List<InvoiceKeyValue> invoiceKeyValueList = invoiceKeyValueRepository.findAll();
        assertThat(invoiceKeyValueList).hasSize(databaseSizeBeforeUpdate);
        InvoiceKeyValue testInvoiceKeyValue = invoiceKeyValueList.get(invoiceKeyValueList.size() - 1);
        assertThat(testInvoiceKeyValue.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testInvoiceKeyValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceKeyValue() throws Exception {
        int databaseSizeBeforeUpdate = invoiceKeyValueRepository.findAll().size();

        // Create the InvoiceKeyValue
        InvoiceKeyValueDTO invoiceKeyValueDTO = invoiceKeyValueMapper.toDto(invoiceKeyValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvoiceKeyValueMockMvc.perform(put("/api/invoice-key-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceKeyValueDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceKeyValue in the database
        List<InvoiceKeyValue> invoiceKeyValueList = invoiceKeyValueRepository.findAll();
        assertThat(invoiceKeyValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvoiceKeyValue() throws Exception {
        // Initialize the database
        invoiceKeyValueRepository.saveAndFlush(invoiceKeyValue);
        int databaseSizeBeforeDelete = invoiceKeyValueRepository.findAll().size();

        // Get the invoiceKeyValue
        restInvoiceKeyValueMockMvc.perform(delete("/api/invoice-key-values/{id}", invoiceKeyValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceKeyValue> invoiceKeyValueList = invoiceKeyValueRepository.findAll();
        assertThat(invoiceKeyValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceKeyValue.class);
        InvoiceKeyValue invoiceKeyValue1 = new InvoiceKeyValue();
        invoiceKeyValue1.setId(1L);
        InvoiceKeyValue invoiceKeyValue2 = new InvoiceKeyValue();
        invoiceKeyValue2.setId(invoiceKeyValue1.getId());
        assertThat(invoiceKeyValue1).isEqualTo(invoiceKeyValue2);
        invoiceKeyValue2.setId(2L);
        assertThat(invoiceKeyValue1).isNotEqualTo(invoiceKeyValue2);
        invoiceKeyValue1.setId(null);
        assertThat(invoiceKeyValue1).isNotEqualTo(invoiceKeyValue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceKeyValueDTO.class);
        InvoiceKeyValueDTO invoiceKeyValueDTO1 = new InvoiceKeyValueDTO();
        invoiceKeyValueDTO1.setId(1L);
        InvoiceKeyValueDTO invoiceKeyValueDTO2 = new InvoiceKeyValueDTO();
        assertThat(invoiceKeyValueDTO1).isNotEqualTo(invoiceKeyValueDTO2);
        invoiceKeyValueDTO2.setId(invoiceKeyValueDTO1.getId());
        assertThat(invoiceKeyValueDTO1).isEqualTo(invoiceKeyValueDTO2);
        invoiceKeyValueDTO2.setId(2L);
        assertThat(invoiceKeyValueDTO1).isNotEqualTo(invoiceKeyValueDTO2);
        invoiceKeyValueDTO1.setId(null);
        assertThat(invoiceKeyValueDTO1).isNotEqualTo(invoiceKeyValueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoiceKeyValueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoiceKeyValueMapper.fromId(null)).isNull();
    }
}
