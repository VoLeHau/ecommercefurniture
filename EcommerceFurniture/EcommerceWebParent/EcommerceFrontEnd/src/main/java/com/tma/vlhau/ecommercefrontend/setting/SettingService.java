package com.tma.vlhau.ecommercefrontend.setting;

import com.tma.vlhau.ecommercecommon.entity.Currency;
import com.tma.vlhau.ecommercecommon.entity.setting.Setting;
import com.tma.vlhau.ecommercecommon.entity.setting.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Setting> listAllSettings() {
        return (List<Setting>) settingRepository.findAll();
    }

    public void saveAll(Iterable<Setting> listSettings) {
        settingRepository.saveAll(listSettings);
    }

    public List<Setting> getSettings() {
        return settingRepository.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }

    public EmailSettingBag getEmailSettings() {
        List<Setting> settings = settingRepository.findAllBySettingCategory(SettingCategory.MAIL_SERVER);
        settings.addAll(settingRepository.findAllBySettingCategory(SettingCategory.MAIL_TEMPLATE));

        return new EmailSettingBag(settings);
    }

    public CurrencySettingBag getCurrencySettings() {
        List<Setting> settings = settingRepository.findAllBySettingCategory(SettingCategory.CURRENCY);
        return new CurrencySettingBag(settings);
    }

    public PaymentSettingBag getPaymentSettings() {
        List<Setting> settings = settingRepository.findAllBySettingCategory(SettingCategory.PAYMENT);
        return new PaymentSettingBag(settings);
    }

    public String getCurrencyCode() {
        Setting setting = settingRepository.findByKey("CURRENCY_ID");
        Integer currencyId = Integer.parseInt(setting.getValue());

        Currency currency = currencyRepository.findById(currencyId).get();

        return currency.getCode();
    }

}
