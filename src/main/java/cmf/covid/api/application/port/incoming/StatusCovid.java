package cmf.covid.api.application.port.incoming;

import cmf.covid.api.application.domain.Status;
import java.util.List;

public interface StatusCovid {

  List<Status> findStatusByCountry(String status);
}
