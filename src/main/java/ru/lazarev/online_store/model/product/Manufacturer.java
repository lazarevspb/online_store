package ru.lazarev.online_store.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "manufacturer")
public class Manufacturer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  private Long name;// TODO: 21.04.2021 проверить тип
  private String description;
  private Integer countryId;


}
