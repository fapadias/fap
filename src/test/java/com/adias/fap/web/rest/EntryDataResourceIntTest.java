package com.adias.fap.web.rest;

import com.adias.fap.JhipsterSampleApplicationApp;

import com.adias.fap.domain.EntryData;
import com.adias.fap.repository.EntryDataRepository;
import com.adias.fap.service.EntryDataService;
import com.adias.fap.service.dto.EntryDataDTO;
import com.adias.fap.service.mapper.EntryDataMapper;
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
 * Test class for the EntryDataResource REST controller.
 *
 * @see EntryDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class EntryDataResourceIntTest {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_INVOICE_TOTAL = 1D;
    private static final Double UPDATED_INVOICE_TOTAL = 2D;

    @Autowired
    private EntryDataRepository entryDataRepository;

    @Autowired
    private EntryDataMapper entryDataMapper;

    @Autowired
    private EntryDataService entryDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntryDataMockMvc;

    private EntryData entryData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntryDataResource entryDataResource = new EntryDataResource(entryDataService);
        this.restEntryDataMockMvc = MockMvcBuilders.standaloneSetup(entryDataResource)
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
    public static EntryData createEntity(EntityManager em) {
        EntryData entryData = new EntryData()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .invoiceTotal(DEFAULT_INVOICE_TOTAL);
        return entryData;
    }

    @Before
    public void initTest() {
        entryData = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntryData() throws Exception {
        int databaseSizeBeforeCreate = entryDataRepository.findAll().size();

        // Create the EntryData
        EntryDataDTO entryDataDTO = entryDataMapper.toDto(entryData);
        restEntryDataMockMvc.perform(post("/api/entry-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryDataDTO)))
            .andExpect(status().isCreated());

        // Validate the EntryData in the database
        List<EntryData> entryDataList = entryDataRepository.findAll();
        assertThat(entryDataList).hasSize(databaseSizeBeforeCreate + 1);
        EntryData testEntryData = entryDataList.get(entryDataList.size() - 1);
        assertThat(testEntryData.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testEntryData.getInvoiceTotal()).isEqualTo(DEFAULT_INVOICE_TOTAL);
    }

    @Test
    @Transactional
    public void createEntryDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entryDataRepository.findAll().size();

        // Create the EntryData with an existing ID
        entryData.setId(1L);
        EntryDataDTO entryDataDTO = entryDataMapper.toDto(entryData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryDataMockMvc.perform(post("/api/entry-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntryData in the database
        List<EntryData> entryDataList = entryDataRepository.findAll();
        assertThat(entryDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntryData() throws Exception {
        // Initialize the database
        entryDataRepository.saveAndFlush(entryData);

        // Get all the entryDataList
        restEntryDataMockMvc.perform(get("/api/entry-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entryData.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].invoiceTotal").value(hasItem(DEFAULT_INVOICE_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getEntryData() throws Exception {
        // Initialize the database
        entryDataRepository.saveAndFlush(entryData);

        // Get the entryData
        restEntryDataMockMvc.perform(get("/api/entry-data/{id}", entryData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entryData.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME.toString()))
            .andExpect(jsonPath("$.invoiceTotal").value(DEFAULT_INVOICE_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntryData() throws Exception {
        // Get the entryData
        restEntryDataMockMvc.perform(get("/api/entry-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntryData() throws Exception {
        // Initialize the database
        entryDataRepository.saveAndFlush(entryData);
        int databaseSizeBeforeUpdate = entryDataRepository.findAll().size();

        // Update the entryData
        EntryData updatedEntryData = entryDataRepository.findOne(entryData.getId());
        // Disconnect from session so that the updates on updatedEntryData are not directly saved in db
        em.detach(updatedEntryData);
        updatedEntryData
            .documentName(UPDATED_DOCUMENT_NAME)
            .invoiceTotal(UPDATED_INVOICE_TOTAL);
        EntryDataDTO entryDataDTO = entryDataMapper.toDto(updatedEntryData);

        restEntryDataMockMvc.perform(put("/api/entry-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryDataDTO)))
            .andExpect(status().isOk());

        // Validate the EntryData in the database
        List<EntryData> entryDataList = entryDataRepository.findAll();
        assertThat(entryDataList).hasSize(databaseSizeBeforeUpdate);
        EntryData testEntryData = entryDataList.get(entryDataList.size() - 1);
        assertThat(testEntryData.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testEntryData.getInvoiceTotal()).isEqualTo(UPDATED_INVOICE_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingEntryData() throws Exception {
        int databaseSizeBeforeUpdate = entryDataRepository.findAll().size();

        // Create the EntryData
        EntryDataDTO entryDataDTO = entryDataMapper.toDto(entryData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntryDataMockMvc.perform(put("/api/entry-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryDataDTO)))
            .andExpect(status().isCreated());

        // Validate the EntryData in the database
        List<EntryData> entryDataList = entryDataRepository.findAll();
        assertThat(entryDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntryData() throws Exception {
        // Initialize the database
        entryDataRepository.saveAndFlush(entryData);
        int databaseSizeBeforeDelete = entryDataRepository.findAll().size();

        // Get the entryData
        restEntryDataMockMvc.perform(delete("/api/entry-data/{id}", entryData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntryData> entryDataList = entryDataRepository.findAll();
        assertThat(entryDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryData.class);
        EntryData entryData1 = new EntryData();
        entryData1.setId(1L);
        EntryData entryData2 = new EntryData();
        entryData2.setId(entryData1.getId());
        assertThat(entryData1).isEqualTo(entryData2);
        entryData2.setId(2L);
        assertThat(entryData1).isNotEqualTo(entryData2);
        entryData1.setId(null);
        assertThat(entryData1).isNotEqualTo(entryData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryDataDTO.class);
        EntryDataDTO entryDataDTO1 = new EntryDataDTO();
        entryDataDTO1.setId(1L);
        EntryDataDTO entryDataDTO2 = new EntryDataDTO();
        assertThat(entryDataDTO1).isNotEqualTo(entryDataDTO2);
        entryDataDTO2.setId(entryDataDTO1.getId());
        assertThat(entryDataDTO1).isEqualTo(entryDataDTO2);
        entryDataDTO2.setId(2L);
        assertThat(entryDataDTO1).isNotEqualTo(entryDataDTO2);
        entryDataDTO1.setId(null);
        assertThat(entryDataDTO1).isNotEqualTo(entryDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entryDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entryDataMapper.fromId(null)).isNull();
    }
}
