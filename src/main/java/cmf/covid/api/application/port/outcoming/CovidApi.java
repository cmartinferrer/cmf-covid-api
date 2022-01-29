package cmf.covid.api.application.port.outcoming;

import cmf.covid.api.application.domain.Status;
import java.util.List;

public interface CovidApi {

  List<Status> findStatusByCountry(String country);
}
