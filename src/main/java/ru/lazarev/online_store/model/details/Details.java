package ru.lazarev.online_store.model.details;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ссылается на таблицу с подробными характеристиками
 * */
@Entity
@Data
@NoArgsConstructor
@Table(name = "details")
public class Details {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")

  private Long id;
  private Long categoryId;
  private Long productDetailsId;
}
