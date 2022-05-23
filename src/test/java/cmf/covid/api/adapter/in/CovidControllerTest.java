package cmf.covid.api.adapter.in;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cmf.covid.api.adapter.in.CovidController.CovidControllerMapper;
import cmf.covid.api.application.domain.Status;
import cmf.covid.api.application.port.incoming.StatusCovid;
import java.util.ArrayList;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CovidControllerTest {

  private static final String URL_TEMPLATE = "/cmf/api/covid/v1/status/country/{country}";
  private static final String COUNTRY = "spain";
  private static final Integer EXPECTED_LIST_SIZE = 5;

  @Mock
  private StatusCovid statusCovid;

  @Spy
  private CovidControllerMapper mapper = Mappers.getMapper(CovidControllerMapper.class);

  private MockMvc mvc;
  private EasyRandom generator = new EasyRandom();

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders
        .standaloneSetup(new CovidController(statusCovid, mapper))
        .build();
  }

  @Test
  void givenValidRequest_whenCallCovidService_thenOk() throws Exception {
    when(statusCovid.findStatusByCountry(COUNTRY))
        .thenReturn(createCovidStatusList());

    mvc.perform(get(URL_TEMPLATE, COUNTRY))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(EXPECTED_LIST_SIZE)))
        .andDo(print());

    verify(statusCovid).findStatusByCountry(COUNTRY);
    verify(mapper).toDto(anyList());
  }

  private List<Status> createCovidStatusList() {
    List<Status> list = new ArrayList<>();
    for (int cont = 0; cont < EXPECTED_LIST_SIZE; cont++) {
      list.add(generator.nextObject(Status.class));
    }
    return list;
  }
}