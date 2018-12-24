/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.flyway;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
@ToString
public class Person {

  /**
   * @TODO
   * flyway 로 데이터 미리 입력된 상태에서
   * auto_increment 에 따른 값 가지고 오기 안됨 확인 해보기
   */
  @Id
  @SequenceGenerator(name = "person_generator", sequenceName = "person_sequence", allocationSize = 1)
  @GeneratedValue(generator = "person_generator")
  private Long id;
  private String firstName;
  private String lastName;

}
