package th.co.azay.service;

import th.co.azay.domain.entity.Deeplink;
import th.co.azay.domain.entity.PaymentTrans;

import java.util.List;

public interface PaymentTransactionsService {
    void insertPaymentDetail(PaymentTrans paymentTrans);
    void insertDeeplink(List<Deeplink> deeplinkList);
}
