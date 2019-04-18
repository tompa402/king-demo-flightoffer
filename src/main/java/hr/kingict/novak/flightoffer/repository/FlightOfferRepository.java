package hr.kingict.novak.flightoffer.repository;

import hr.kingict.novak.flightoffer.model.dto.FlightOfferDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightOfferRepository extends JpaRepository<FlightOfferDto, Integer>, QueryByExampleExecutor<FlightOfferDto> {

}
