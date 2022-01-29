package cmf.covid.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDto {

  @JsonProperty("ID")
  private String id;

  @JsonProperty("Country")
  private String country;

  @JsonProperty("CountryCode")
  private String countryCode;

  @JsonProperty("Province")
  private String province;

  @JsonProperty("City")
  private String city;

  @JsonProperty("CityCode")
  private String cityCode;

  @JsonProperty("Lat")
  private String latitude;

  @JsonProperty("Lon")
  private String longitude;

  @JsonProperty("Confirmed")
  private Integer confirmed;

  @JsonProperty("Deaths")
  private Integer deaths;

  @JsonProperty("Recovered")
  private Integer recovered;

  @JsonProperty("Active")
  private Integer active;

  @JsonProperty("Date")
  private String date;
}
