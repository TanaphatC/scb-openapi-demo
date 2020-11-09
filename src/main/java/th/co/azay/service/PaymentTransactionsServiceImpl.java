package th.co.azay.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.azay.domain.entity.Deeplink;
import th.co.azay.domain.entity.PaymentTrans;
import th.co.azay.domain.repository.DeeplinkRepository;
import th.co.azay.domain.repository.PaymentTransactionsRepository;

import java.util.List;

@Service
public class PaymentTransactionsServiceImpl implements PaymentTransactionsService {
    private static final Logger logger = LogManager.getLogger(PaymentTransactionsServiceImpl.class);
    @Autowired
    PaymentTransactionsRepository paymentTransactionsRepository;
    @Autowired
    DeeplinkRepository deeplinkRepository;

    @Override
    public void insertPaymentDetail(PaymentTrans paymentTrans) {
        paymentTransactionsRepository.save(paymentTrans);
        logger.info("insertPaymentDetail success");
    }

    @Override
    public void insertDeeplink(List<Deeplink> deeplinkList){
        deeplinkRepository.saveAll(deeplinkList);
        logger.info("insertDeeplink success");
    }
}
