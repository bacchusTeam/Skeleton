package sample.jsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sample.flyway.Person;
import sample.flyway.PersonRepository;
import sample.mybatis.dao.CityDao;
import sample.mybatis.domain.Greeting;
import sample.mybatis.domain.Hotel;
import sample.mybatis.mapper.HotelMapper;
import sample.mybatis.service.SampleService;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestAPIController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  private final CityDao cityDao;
  private final HotelMapper hotelMapper;
  private final SampleService sampleService;
  private final PersonRepository personRepository;

  public RestAPIController(CityDao cityDao, HotelMapper hotelMapper, SampleService sampleService, PersonRepository personRepository) {
    this.cityDao = cityDao;
    this.hotelMapper = hotelMapper;
    this.sampleService = sampleService;
    this.personRepository = personRepository;
    }

  @GetMapping("/v1/{name}")
  public Greeting getData(@PathVariable String name) {
    log.info("v1");
    Greeting greeting = new Greeting();
    greeting.setHotel(hotelMapper.selectByCityId(1));
    greeting.setContent(String.format(template, name));
    greeting.setId(counter.incrementAndGet());
    return greeting;
  }

  @GetMapping("/v2")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    log.info("v2");
    Greeting greeting = new Greeting();
    greeting.setCity(cityDao.selectCityById(1));
    greeting.setContent(String.format(template, name));
    greeting.setId(counter.incrementAndGet());
    return greeting;
  }

  @GetMapping("/v3/{first}/{last}")
  public Iterable<Person> person(@PathVariable String first, @PathVariable String last) {
    log.info("v3");
    Person person = new Person();
    person.setFirstName(first);
    person.setLastName(last);
    personRepository.save(person);
    return this.personRepository.findAll();
  }
}
