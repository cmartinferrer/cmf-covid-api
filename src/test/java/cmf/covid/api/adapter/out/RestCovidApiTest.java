package cmf.covid.api.adapter.out;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cmf.covid.api.adapter.out.RestCovidApi.CovidApiMapper;
import cmf.covid.api.dto.ReportDto;
import java.util.List;
import lombok.val;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RestCovidApiTest {

  private static final String COUNTRY = "spain";
  private static final Integer EXPECTED_LIST_SIZE = 5;

  @InjectMocks
  private RestCovidApi api;

  @Mock
  private RestTemplate restTemplate;

  @Spy
  private CovidApiMapper mapper = Mappers.getMapper(CovidApiMapper.class);

  private EasyRandom generator = new EasyRandom();

  @Test
  void givenCountry_whenFindCovidInformation_thenReturnListWithCovidStatus() {

    when(restTemplate.getForEntity(anyString(), any()))
        .thenReturn(new ResponseEntity<>(createReportDto(), HttpStatus.OK));

    val statusList = api.findStatusByCountry(COUNTRY);

    assertAll(
        () -> assertNotNull(statusList),
        () -> assertEquals(statusList.size(), EXPECTED_LIST_SIZE)
    );

    verify(restTemplate).getForEntity(anyString(), any());
    verify(mapper).toDomain(any(List.class));
  }

  private ReportDto[] createReportDto() {
    ReportDto[] reportDtos = new ReportDto[EXPECTED_LIST_SIZE];

    for (int cont = 0; cont < reportDtos.length; cont++) {
      reportDtos[cont] = generator.nextObject(ReportDto.class);
    }
    return reportDtos;
  }

}