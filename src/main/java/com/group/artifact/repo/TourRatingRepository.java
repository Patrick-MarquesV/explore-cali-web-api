package com.group.artifact.repo;

import com.group.artifact.domain.TourRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, String> {
    /**
     * Procura todos os TourRatings de um tour
     *
     * @param tourId é o identificador do Tour
     * @return uma lista de todos os TourRatings encontrados
     */
    List<TourRating> findByTourId(String tourId);

    /**
     * Procura um TourRating pelo seu Id e pelo Id do cliente
     * @param tourId é o identificador do Tour
     * @param customerId é o identificador do cliente
     * @return um Optinal do TourRating encontrado
     */

    Optional<TourRating> findByTourIdAndCustomerId(String tourId, Integer customerId);


    Page<TourRating> findByTourId(String tourId, Pageable pageable);

}
