package cmf.covid.api.adapter.in;

import cmf.covid.api.application.domain.Status;
import cmf.covid.api.application.port.incoming.StatusCovid;
import cmf.covid.api.dto.StatusDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Covid Controller.
 *
 * @author Cristian Martín Ferrer on 2021-09-18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cmf/api/covid/v1")
public class CovidController {

  private final StatusCovid statusCovid;
  private final CovidControllerMapper mapper;

  @GetMapping("/status/country/{country}")
  public ResponseEntity<List<StatusDto>> getStatusByCountry(@PathVariable String country) {

    val covidStatusList = statusCovid.findStatusByCountry(country);

    return new ResponseEntity<>(mapper.toDto(covidStatusList), HttpStatus.OK);
  }

  @Mapper
  public interface CovidControllerMapper {

    List<StatusDto> toDto(List<Status> response);
  }
}
