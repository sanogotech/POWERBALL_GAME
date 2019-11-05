package com.spiritbeing.powerball.repo;

import com.spiritbeing.powerball.model.PowerBall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PowerBallRepository extends PagingAndSortingRepository<PowerBall, Long> {
    //Set<PowerBall> findByOrderByCreatedDateAsc();
    Page<PowerBall> findByOrderByCreatedDateDesc(Pageable pageable);

}
