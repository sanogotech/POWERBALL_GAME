package com.spiritbeing.powerball.ServiceAddon;

import com.spiritbeing.powerball.model.BallsFrequency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FrequencyService {

    Page<BallsFrequency> findByOrderById(Pageable pageable);
}
