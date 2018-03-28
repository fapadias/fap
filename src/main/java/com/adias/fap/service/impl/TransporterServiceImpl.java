package com.adias.fap.service.impl;

import com.adias.fap.service.TransporterService;
import com.adias.fap.domain.Transporter;
import com.adias.fap.repository.TransporterRepository;
import com.adias.fap.service.dto.TransporterDTO;
import com.adias.fap.service.mapper.TransporterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Transporter.
 */
@Service
@Transactional
public class TransporterServiceImpl implements TransporterService {

    private final Logger log = LoggerFactory.getLogger(TransporterServiceImpl.class);

    private final TransporterRepository transporterRepository;

    private final TransporterMapper transporterMapper;

    public TransporterServiceImpl(TransporterRepository transporterRepository, TransporterMapper transporterMapper) {
        this.transporterRepository = transporterRepository;
        this.transporterMapper = transporterMapper;
    }

    /**
     * Save a transporter.
     *
     * @param transporterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TransporterDTO save(TransporterDTO transporterDTO) {
        log.debug("Request to save Transporter : {}", transporterDTO);
        Transporter transporter = transporterMapper.toEntity(transporterDTO);
        transporter = transporterRepository.save(transporter);
        return transporterMapper.toDto(transporter);
    }

    /**
     * Get all the transporters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransporterDTO> findAll() {
        log.debug("Request to get all Transporters");
        return transporterRepository.findAll().stream()
            .map(transporterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one transporter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TransporterDTO findOne(Long id) {
        log.debug("Request to get Transporter : {}", id);
        Transporter transporter = transporterRepository.findOne(id);
        return transporterMapper.toDto(transporter);
    }

    /**
     * Delete the transporter by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transporter : {}", id);
        transporterRepository.delete(id);
    }
}
