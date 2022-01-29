package cmf.covid.api.application.services;

import cmf.covid.api.application.domain.Status;
import cmf.covid.api.application.port.incoming.StatusCovid;
import cmf.covid.api.application.port.outcoming.CovidApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Cristian Martín Ferrer on 2021-09-18
 */
@Service
@RequiredArgsConstructor
public class StatusCovidImpl implements StatusCovid {

  private final CovidApi api;

  @Override
  public List<Status> findStatusByCountry(String country) {

    return api.findStatusByCountry(country);
  }
}
