package com.adias.fap.web.rest;

import com.adias.fap.JhipsterSampleApplicationApp;

import com.adias.fap.domain.CostGride;
import com.adias.fap.repository.CostGrideRepository;
import com.adias.fap.service.CostGrideService;
import com.adias.fap.service.dto.CostGrideDTO;
import com.adias.fap.service.mapper.CostGrideMapper;
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
 * Test class for the CostGrideResource REST controller.
 *
 * @see CostGrideResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CostGrideResourceIntTest {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private CostGrideRepository costGrideRepository;

    @Autowired
    private CostGrideMapper costGrideMapper;

    @Autowired
    private CostGrideService costGrideService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCostGrideMockMvc;

    private CostGride costGride;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CostGrideResource costGrideResource = new CostGrideResource(costGrideService);
        this.restCostGrideMockMvc = MockMvcBuilders.standaloneSetup(costGrideResource)
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
    public static CostGride createEntity(EntityManager em) {
        CostGride costGride = new CostGride()
            .fileName(DEFAULT_FILE_NAME)
            .version(DEFAULT_VERSION);
        return costGride;
    }

    @Before
    public void initTest() {
        costGride = createEntity(em);
    }

    @Test
    @Transactional
    public void createCostGride() throws Exception {
        int databaseSizeBeforeCreate = costGrideRepository.findAll().size();

        // Create the CostGride
        CostGrideDTO costGrideDTO = costGrideMapper.toDto(costGride);
        restCostGrideMockMvc.perform(post("/api/cost-grides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costGrideDTO)))
            .andExpect(status().isCreated());

        // Validate the CostGride in the database
        List<CostGride> costGrideList = costGrideRepository.findAll();
        assertThat(costGrideList).hasSize(databaseSizeBeforeCreate + 1);
        CostGride testCostGride = costGrideList.get(costGrideList.size() - 1);
        assertThat(testCostGride.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testCostGride.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createCostGrideWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costGrideRepository.findAll().size();

        // Create the CostGride with an existing ID
        costGride.setId(1L);
        CostGrideDTO costGrideDTO = costGrideMapper.toDto(costGride);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostGrideMockMvc.perform(post("/api/cost-grides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costGrideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CostGride in the database
        List<CostGride> costGrideList = costGrideRepository.findAll();
        assertThat(costGrideList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCostGrides() throws Exception {
        // Initialize the database
        costGrideRepository.saveAndFlush(costGride);

        // Get all the costGrideList
        restCostGrideMockMvc.perform(get("/api/cost-grides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costGride.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getCostGride() throws Exception {
        // Initialize the database
        costGrideRepository.saveAndFlush(costGride);

        // Get the costGride
        restCostGrideMockMvc.perform(get("/api/cost-grides/{id}", costGride.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(costGride.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCostGride() throws Exception {
        // Get the costGride
        restCostGrideMockMvc.perform(get("/api/cost-grides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCostGride() throws Exception {
        // Initialize the database
        costGrideRepository.saveAndFlush(costGride);
        int databaseSizeBeforeUpdate = costGrideRepository.findAll().size();

        // Update the costGride
        CostGride updatedCostGride = costGrideRepository.findOne(costGride.getId());
        // Disconnect from session so that the updates on updatedCostGride are not directly saved in db
        em.detach(updatedCostGride);
        updatedCostGride
            .fileName(UPDATED_FILE_NAME)
            .version(UPDATED_VERSION);
        CostGrideDTO costGrideDTO = costGrideMapper.toDto(updatedCostGride);

        restCostGrideMockMvc.perform(put("/api/cost-grides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costGrideDTO)))
            .andExpect(status().isOk());

        // Validate the CostGride in the database
        List<CostGride> costGrideList = costGrideRepository.findAll();
        assertThat(costGrideList).hasSize(databaseSizeBeforeUpdate);
        CostGride testCostGride = costGrideList.get(costGrideList.size() - 1);
        assertThat(testCostGride.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testCostGride.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingCostGride() throws Exception {
        int databaseSizeBeforeUpdate = costGrideRepository.findAll().size();

        // Create the CostGride
        CostGrideDTO costGrideDTO = costGrideMapper.toDto(costGride);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCostGrideMockMvc.perform(put("/api/cost-grides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costGrideDTO)))
            .andExpect(status().isCreated());

        // Validate the CostGride in the database
        List<CostGride> costGrideList = costGrideRepository.findAll();
        assertThat(costGrideList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCostGride() throws Exception {
        // Initialize the database
        costGrideRepository.saveAndFlush(costGride);
        int databaseSizeBeforeDelete = costGrideRepository.findAll().size();

        // Get the costGride
        restCostGrideMockMvc.perform(delete("/api/cost-grides/{id}", costGride.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CostGride> costGrideList = costGrideRepository.findAll();
        assertThat(costGrideList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostGride.class);
        CostGride costGride1 = new CostGride();
        costGride1.setId(1L);
        CostGride costGride2 = new CostGride();
        costGride2.setId(costGride1.getId());
        assertThat(costGride1).isEqualTo(costGride2);
        costGride2.setId(2L);
        assertThat(costGride1).isNotEqualTo(costGride2);
        costGride1.setId(null);
        assertThat(costGride1).isNotEqualTo(costGride2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostGrideDTO.class);
        CostGrideDTO costGrideDTO1 = new CostGrideDTO();
        costGrideDTO1.setId(1L);
        CostGrideDTO costGrideDTO2 = new CostGrideDTO();
        assertThat(costGrideDTO1).isNotEqualTo(costGrideDTO2);
        costGrideDTO2.setId(costGrideDTO1.getId());
        assertThat(costGrideDTO1).isEqualTo(costGrideDTO2);
        costGrideDTO2.setId(2L);
        assertThat(costGrideDTO1).isNotEqualTo(costGrideDTO2);
        costGrideDTO1.setId(null);
        assertThat(costGrideDTO1).isNotEqualTo(costGrideDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(costGrideMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(costGrideMapper.fromId(null)).isNull();
    }
}
