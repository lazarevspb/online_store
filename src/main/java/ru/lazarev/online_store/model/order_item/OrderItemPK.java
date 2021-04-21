package ru.lazarev.online_store.model.order_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
class OrderItemPK implements Serializable {
    protected Long levelStation;
    protected Long confPathID;
}
