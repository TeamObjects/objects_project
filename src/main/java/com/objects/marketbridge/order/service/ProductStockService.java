package com.objects.marketbridge.order.service;

import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.common.interceptor.error.CustomLogicException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductStockService {
    public void decrease(List<OrderDetailEntity> orderDetailEntities) throws CustomLogicException {

        orderDetailEntities.forEach(o -> o.getProduct().decrease(o.getQuantity()));
    }
}
