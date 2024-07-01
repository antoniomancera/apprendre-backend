package com.antonio.apprendrebackend.service.impl;


import com.antonio.apprendrebackend.dto.MotPalabraDTO;
import com.antonio.apprendrebackend.mapper.MotPalabraMapper;
import com.antonio.apprendrebackend.model.MotPalabra;
import com.antonio.apprendrebackend.model.MotPalabraPool;
import com.antonio.apprendrebackend.repository.MotPalabraPoolRepository;
import com.antonio.apprendrebackend.repository.MotPalabraRepository;
import com.antonio.apprendrebackend.service.MotPalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MotPalabraServiceImpl implements MotPalabraService {
    @Autowired
    MotPalabraPoolRepository motPalabraPoolRepository;
    @Autowired
    MotPalabraRepository motPalabraRepository;

    @Autowired
    private MotPalabraMapper motPalabraMapper;

    @Override
    public MotPalabraDTO getRandomMotPalabraPhrase() {
        MotPalabraPool motPalabraPool = motPalabraPoolRepository.findRandomMotPalabraPool();

        System.out.println(
                motPalabraPool.toString());

        if (motPalabraPool == null) {
            return null;
        }

        MotPalabra motPalabra = motPalabraPool.getMotPalabra();

        if (motPalabra == null) {
            return null;
        }

        MotPalabraDTO motPalabraDTO = motPalabraMapper.toDTO(motPalabra);

        System.out.println(
                motPalabraDTO.toString());

        return motPalabraDTO;
    }
}