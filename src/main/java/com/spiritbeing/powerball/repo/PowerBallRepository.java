package com.spiritbeing.powerball.repo;

import com.spiritbeing.powerball.model.PowerBall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface PowerBallRepository extends PagingAndSortingRepository<PowerBall, Long> {
    Page<PowerBall> findByOrderByCreatedDateDesc(Pageable pageable);

//    @Query(nativeQuery=true, value="select sum(pb.ball_1) from power_ball pb where pb.ball_1 in ?1")
    @Query(nativeQuery=true, value="select sum(pb.ball_1) as amount from power_ball pb where pb.ball_1 in ?1 and pb.ball_2 > ?2")
    Double sumofEntryNative(Collection<Integer> balls, int vaue);

    //JPQL
    @Query("select sum(pb.ball_1) as amount from PowerBall pb where pb.ball_1 in ?1 and pb.ball_2 > ?2")
    Double sumofEntryJPQL(Collection<Integer> balls, int vaue);
}
