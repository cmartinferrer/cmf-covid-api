package cmf.covid.api.application.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cmf.covid.api.application.domain.Status;
import cmf.covid.api.application.port.outcoming.CovidApi;
import java.util.ArrayList;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatusCovidImplTest {

  private static final String COUNTRY = "spain";

  @InjectMocks
  private StatusCovidImpl service;

  @Mock
  private CovidApi api;

  private EasyRandom generator = new EasyRandom();

  @Test
  void givenCountry_whenFindCovidInformation_thenReturnListWithCovidStatus() {
    when(api.findStatusByCountry(COUNTRY))
        .thenReturn(createCovidStatusList());

    service.findStatusByCountry(COUNTRY);

    verify(api).findStatusByCountry(COUNTRY);
  }

  private List<Status> createCovidStatusList() {
    List<Status> list = new ArrayList<>();
    for (int cont = 0; cont < 5; cont++) {
      list.add(generator.nextObject(Status.class));
    }
    return list;
  }
}