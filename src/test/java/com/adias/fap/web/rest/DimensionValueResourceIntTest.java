package com.adias.fap.web.rest;

import com.adias.fap.JhipsterSampleApplicationApp;

import com.adias.fap.domain.DimensionValue;
import com.adias.fap.repository.DimensionValueRepository;
import com.adias.fap.service.DimensionValueService;
import com.adias.fap.service.dto.DimensionValueDTO;
import com.adias.fap.service.mapper.DimensionValueMapper;
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
 * Test class for the DimensionValueResource REST controller.
 *
 * @see DimensionValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DimensionValueResourceIntTest {

    private static final String DEFAULT_STRING_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_STRING_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_LOWER_BOUND = "AAAAAAAAAA";
    private static final String UPDATED_LOWER_BOUND = "BBBBBBBBBB";

    private static final String DEFAULT_UPPER_BOUND = "AAAAAAAAAA";
    private static final String UPDATED_UPPER_BOUND = "BBBBBBBBBB";

    @Autowired
    private DimensionValueRepository dimensionValueRepository;

    @Autowired
    private DimensionValueMapper dimensionValueMapper;

    @Autowired
    private DimensionValueService dimensionValueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDimensionValueMockMvc;

    private DimensionValue dimensionValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DimensionValueResource dimensionValueResource = new DimensionValueResource(dimensionValueService);
        this.restDimensionValueMockMvc = MockMvcBuilders.standaloneSetup(dimensionValueResource)
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
    public static DimensionValue createEntity(EntityManager em) {
        DimensionValue dimensionValue = new DimensionValue()
            .stringValue(DEFAULT_STRING_VALUE)
            .lowerBound(DEFAULT_LOWER_BOUND)
            .upperBound(DEFAULT_UPPER_BOUND);
        return dimensionValue;
    }

    @Before
    public void initTest() {
        dimensionValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createDimensionValue() throws Exception {
        int databaseSizeBeforeCreate = dimensionValueRepository.findAll().size();

        // Create the DimensionValue
        DimensionValueDTO dimensionValueDTO = dimensionValueMapper.toDto(dimensionValue);
        restDimensionValueMockMvc.perform(post("/api/dimension-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionValueDTO)))
            .andExpect(status().isCreated());

        // Validate the DimensionValue in the database
        List<DimensionValue> dimensionValueList = dimensionValueRepository.findAll();
        assertThat(dimensionValueList).hasSize(databaseSizeBeforeCreate + 1);
        DimensionValue testDimensionValue = dimensionValueList.get(dimensionValueList.size() - 1);
        assertThat(testDimensionValue.getStringValue()).isEqualTo(DEFAULT_STRING_VALUE);
        assertThat(testDimensionValue.getLowerBound()).isEqualTo(DEFAULT_LOWER_BOUND);
        assertThat(testDimensionValue.getUpperBound()).isEqualTo(DEFAULT_UPPER_BOUND);
    }

    @Test
    @Transactional
    public void createDimensionValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dimensionValueRepository.findAll().size();

        // Create the DimensionValue with an existing ID
        dimensionValue.setId(1L);
        DimensionValueDTO dimensionValueDTO = dimensionValueMapper.toDto(dimensionValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimensionValueMockMvc.perform(post("/api/dimension-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DimensionValue in the database
        List<DimensionValue> dimensionValueList = dimensionValueRepository.findAll();
        assertThat(dimensionValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDimensionValues() throws Exception {
        // Initialize the database
        dimensionValueRepository.saveAndFlush(dimensionValue);

        // Get all the dimensionValueList
        restDimensionValueMockMvc.perform(get("/api/dimension-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimensionValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].stringValue").value(hasItem(DEFAULT_STRING_VALUE.toString())))
            .andExpect(jsonPath("$.[*].lowerBound").value(hasItem(DEFAULT_LOWER_BOUND.toString())))
            .andExpect(jsonPath("$.[*].upperBound").value(hasItem(DEFAULT_UPPER_BOUND.toString())));
    }

    @Test
    @Transactional
    public void getDimensionValue() throws Exception {
        // Initialize the database
        dimensionValueRepository.saveAndFlush(dimensionValue);

        // Get the dimensionValue
        restDimensionValueMockMvc.perform(get("/api/dimension-values/{id}", dimensionValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dimensionValue.getId().intValue()))
            .andExpect(jsonPath("$.stringValue").value(DEFAULT_STRING_VALUE.toString()))
            .andExpect(jsonPath("$.lowerBound").value(DEFAULT_LOWER_BOUND.toString()))
            .andExpect(jsonPath("$.upperBound").value(DEFAULT_UPPER_BOUND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDimensionValue() throws Exception {
        // Get the dimensionValue
        restDimensionValueMockMvc.perform(get("/api/dimension-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDimensionValue() throws Exception {
        // Initialize the database
        dimensionValueRepository.saveAndFlush(dimensionValue);
        int databaseSizeBeforeUpdate = dimensionValueRepository.findAll().size();

        // Update the dimensionValue
        DimensionValue updatedDimensionValue = dimensionValueRepository.findOne(dimensionValue.getId());
        // Disconnect from session so that the updates on updatedDimensionValue are not directly saved in db
        em.detach(updatedDimensionValue);
        updatedDimensionValue
            .stringValue(UPDATED_STRING_VALUE)
            .lowerBound(UPDATED_LOWER_BOUND)
            .upperBound(UPDATED_UPPER_BOUND);
        DimensionValueDTO dimensionValueDTO = dimensionValueMapper.toDto(updatedDimensionValue);

        restDimensionValueMockMvc.perform(put("/api/dimension-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionValueDTO)))
            .andExpect(status().isOk());

        // Validate the DimensionValue in the database
        List<DimensionValue> dimensionValueList = dimensionValueRepository.findAll();
        assertThat(dimensionValueList).hasSize(databaseSizeBeforeUpdate);
        DimensionValue testDimensionValue = dimensionValueList.get(dimensionValueList.size() - 1);
        assertThat(testDimensionValue.getStringValue()).isEqualTo(UPDATED_STRING_VALUE);
        assertThat(testDimensionValue.getLowerBound()).isEqualTo(UPDATED_LOWER_BOUND);
        assertThat(testDimensionValue.getUpperBound()).isEqualTo(UPDATED_UPPER_BOUND);
    }

    @Test
    @Transactional
    public void updateNonExistingDimensionValue() throws Exception {
        int databaseSizeBeforeUpdate = dimensionValueRepository.findAll().size();

        // Create the DimensionValue
        DimensionValueDTO dimensionValueDTO = dimensionValueMapper.toDto(dimensionValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDimensionValueMockMvc.perform(put("/api/dimension-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionValueDTO)))
            .andExpect(status().isCreated());

        // Validate the DimensionValue in the database
        List<DimensionValue> dimensionValueList = dimensionValueRepository.findAll();
        assertThat(dimensionValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDimensionValue() throws Exception {
        // Initialize the database
        dimensionValueRepository.saveAndFlush(dimensionValue);
        int databaseSizeBeforeDelete = dimensionValueRepository.findAll().size();

        // Get the dimensionValue
        restDimensionValueMockMvc.perform(delete("/api/dimension-values/{id}", dimensionValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DimensionValue> dimensionValueList = dimensionValueRepository.findAll();
        assertThat(dimensionValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DimensionValue.class);
        DimensionValue dimensionValue1 = new DimensionValue();
        dimensionValue1.setId(1L);
        DimensionValue dimensionValue2 = new DimensionValue();
        dimensionValue2.setId(dimensionValue1.getId());
        assertThat(dimensionValue1).isEqualTo(dimensionValue2);
        dimensionValue2.setId(2L);
        assertThat(dimensionValue1).isNotEqualTo(dimensionValue2);
        dimensionValue1.setId(null);
        assertThat(dimensionValue1).isNotEqualTo(dimensionValue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DimensionValueDTO.class);
        DimensionValueDTO dimensionValueDTO1 = new DimensionValueDTO();
        dimensionValueDTO1.setId(1L);
        DimensionValueDTO dimensionValueDTO2 = new DimensionValueDTO();
        assertThat(dimensionValueDTO1).isNotEqualTo(dimensionValueDTO2);
        dimensionValueDTO2.setId(dimensionValueDTO1.getId());
        assertThat(dimensionValueDTO1).isEqualTo(dimensionValueDTO2);
        dimensionValueDTO2.setId(2L);
        assertThat(dimensionValueDTO1).isNotEqualTo(dimensionValueDTO2);
        dimensionValueDTO1.setId(null);
        assertThat(dimensionValueDTO1).isNotEqualTo(dimensionValueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dimensionValueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dimensionValueMapper.fromId(null)).isNull();
    }
}
