package com.adias.fap.web.rest;

import com.adias.fap.JhipsterSampleApplicationApp;

import com.adias.fap.domain.MetricValue;
import com.adias.fap.repository.MetricValueRepository;
import com.adias.fap.service.MetricValueService;
import com.adias.fap.service.dto.MetricValueDTO;
import com.adias.fap.service.mapper.MetricValueMapper;
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
 * Test class for the MetricValueResource REST controller.
 *
 * @see MetricValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class MetricValueResourceIntTest {

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final String DEFAULT_FACE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_FACE_VALUE = "BBBBBBBBBB";

    @Autowired
    private MetricValueRepository metricValueRepository;

    @Autowired
    private MetricValueMapper metricValueMapper;

    @Autowired
    private MetricValueService metricValueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMetricValueMockMvc;

    private MetricValue metricValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MetricValueResource metricValueResource = new MetricValueResource(metricValueService);
        this.restMetricValueMockMvc = MockMvcBuilders.standaloneSetup(metricValueResource)
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
    public static MetricValue createEntity(EntityManager em) {
        MetricValue metricValue = new MetricValue()
            .value(DEFAULT_VALUE)
            .faceValue(DEFAULT_FACE_VALUE);
        return metricValue;
    }

    @Before
    public void initTest() {
        metricValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createMetricValue() throws Exception {
        int databaseSizeBeforeCreate = metricValueRepository.findAll().size();

        // Create the MetricValue
        MetricValueDTO metricValueDTO = metricValueMapper.toDto(metricValue);
        restMetricValueMockMvc.perform(post("/api/metric-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metricValueDTO)))
            .andExpect(status().isCreated());

        // Validate the MetricValue in the database
        List<MetricValue> metricValueList = metricValueRepository.findAll();
        assertThat(metricValueList).hasSize(databaseSizeBeforeCreate + 1);
        MetricValue testMetricValue = metricValueList.get(metricValueList.size() - 1);
        assertThat(testMetricValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMetricValue.getFaceValue()).isEqualTo(DEFAULT_FACE_VALUE);
    }

    @Test
    @Transactional
    public void createMetricValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = metricValueRepository.findAll().size();

        // Create the MetricValue with an existing ID
        metricValue.setId(1L);
        MetricValueDTO metricValueDTO = metricValueMapper.toDto(metricValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetricValueMockMvc.perform(post("/api/metric-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metricValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetricValue in the database
        List<MetricValue> metricValueList = metricValueRepository.findAll();
        assertThat(metricValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMetricValues() throws Exception {
        // Initialize the database
        metricValueRepository.saveAndFlush(metricValue);

        // Get all the metricValueList
        restMetricValueMockMvc.perform(get("/api/metric-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metricValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].faceValue").value(hasItem(DEFAULT_FACE_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getMetricValue() throws Exception {
        // Initialize the database
        metricValueRepository.saveAndFlush(metricValue);

        // Get the metricValue
        restMetricValueMockMvc.perform(get("/api/metric-values/{id}", metricValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(metricValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.faceValue").value(DEFAULT_FACE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMetricValue() throws Exception {
        // Get the metricValue
        restMetricValueMockMvc.perform(get("/api/metric-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMetricValue() throws Exception {
        // Initialize the database
        metricValueRepository.saveAndFlush(metricValue);
        int databaseSizeBeforeUpdate = metricValueRepository.findAll().size();

        // Update the metricValue
        MetricValue updatedMetricValue = metricValueRepository.findOne(metricValue.getId());
        // Disconnect from session so that the updates on updatedMetricValue are not directly saved in db
        em.detach(updatedMetricValue);
        updatedMetricValue
            .value(UPDATED_VALUE)
            .faceValue(UPDATED_FACE_VALUE);
        MetricValueDTO metricValueDTO = metricValueMapper.toDto(updatedMetricValue);

        restMetricValueMockMvc.perform(put("/api/metric-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metricValueDTO)))
            .andExpect(status().isOk());

        // Validate the MetricValue in the database
        List<MetricValue> metricValueList = metricValueRepository.findAll();
        assertThat(metricValueList).hasSize(databaseSizeBeforeUpdate);
        MetricValue testMetricValue = metricValueList.get(metricValueList.size() - 1);
        assertThat(testMetricValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMetricValue.getFaceValue()).isEqualTo(UPDATED_FACE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingMetricValue() throws Exception {
        int databaseSizeBeforeUpdate = metricValueRepository.findAll().size();

        // Create the MetricValue
        MetricValueDTO metricValueDTO = metricValueMapper.toDto(metricValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMetricValueMockMvc.perform(put("/api/metric-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metricValueDTO)))
            .andExpect(status().isCreated());

        // Validate the MetricValue in the database
        List<MetricValue> metricValueList = metricValueRepository.findAll();
        assertThat(metricValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMetricValue() throws Exception {
        // Initialize the database
        metricValueRepository.saveAndFlush(metricValue);
        int databaseSizeBeforeDelete = metricValueRepository.findAll().size();

        // Get the metricValue
        restMetricValueMockMvc.perform(delete("/api/metric-values/{id}", metricValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MetricValue> metricValueList = metricValueRepository.findAll();
        assertThat(metricValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetricValue.class);
        MetricValue metricValue1 = new MetricValue();
        metricValue1.setId(1L);
        MetricValue metricValue2 = new MetricValue();
        metricValue2.setId(metricValue1.getId());
        assertThat(metricValue1).isEqualTo(metricValue2);
        metricValue2.setId(2L);
        assertThat(metricValue1).isNotEqualTo(metricValue2);
        metricValue1.setId(null);
        assertThat(metricValue1).isNotEqualTo(metricValue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetricValueDTO.class);
        MetricValueDTO metricValueDTO1 = new MetricValueDTO();
        metricValueDTO1.setId(1L);
        MetricValueDTO metricValueDTO2 = new MetricValueDTO();
        assertThat(metricValueDTO1).isNotEqualTo(metricValueDTO2);
        metricValueDTO2.setId(metricValueDTO1.getId());
        assertThat(metricValueDTO1).isEqualTo(metricValueDTO2);
        metricValueDTO2.setId(2L);
        assertThat(metricValueDTO1).isNotEqualTo(metricValueDTO2);
        metricValueDTO1.setId(null);
        assertThat(metricValueDTO1).isNotEqualTo(metricValueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(metricValueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(metricValueMapper.fromId(null)).isNull();
    }
}
