package com.taotao.store.order.mapper;

import java.util.Date;



import org.apache.ibatis.annotations.Param;

import com.taotao.store.order.pojo.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends IMapper<Order>{
	
	public void paymentOrderScan(@Param("date") Date date);

}
