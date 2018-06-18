package com.infosystem.springmvc.util;

import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionsRulesCheckerTest {

    private Tariff tariff;

    private Set<TariffOption> tariffOptionSet = new HashSet<>();
    private Set<TariffOption> toBeAddedOptionsList = new HashSet<>();
    private Set<TariffOption> toBeAddedOptionsListTariffAlrdyHave = new HashSet<>();

    @Before
    public void setUp(){
        setTariffOptionSet();
        setToBeAddedOptionsList();
        setToBeAddedOptionsListTariffAlrdyHave();
        tariff = new Tariff();
        tariff.setId(1);
        tariff.setAvailableOptions(tariffOptionSet);
    }

    private void setTariffOptionSet() {
        TariffOption tariffOption1 = new TariffOption();
        TariffOption tariffOption2 = new TariffOption();
        tariffOption1.setId(1);
        tariffOption2.setId(2);
        tariffOption1.setName("name1");
        tariffOption2.setName("name2");
        tariffOptionSet.addAll(Arrays.asList(tariffOption1, tariffOption2));
    }


    private void setToBeAddedOptionsList() {
        TariffOption tariffOption3 = new TariffOption();
        TariffOption tariffOption4 = new TariffOption();
        tariffOption3.setId(3);
        tariffOption4.setId(4);
        tariffOption3.setName("name3");
        tariffOption4.setName("name4");
        toBeAddedOptionsList.addAll(Arrays.asList(tariffOption3, tariffOption4));
    }


    private void setToBeAddedOptionsListTariffAlrdyHave() {
        TariffOption tariffOption2 = new TariffOption();
        TariffOption tariffOption3 = new TariffOption();
        tariffOption2.setId(2);
        tariffOption3.setId(3);
        tariffOption2.setName("name2");
        tariffOption3.setName("name3");
        toBeAddedOptionsListTariffAlrdyHave.addAll(Arrays.asList(tariffOption2, tariffOption3));
    }

    @After
    public void tearDown(){
    }

    @Test
    @DisplayName("Tariff already have options.")
    public void test_1_0_CheckIfTariffAlreadyHave() {
        Set<TariffOption> currentOptions = new HashSet<>(tariff.getAvailableOptions());
        currentOptions.retainAll(toBeAddedOptionsListTariffAlrdyHave);

        StringBuilder sb = new StringBuilder();
        currentOptions.forEach(tariffOption -> sb.append("Tariff ").append(tariff.getId()).append(" already has ")
                .append(tariffOption.getName()).append(" option.\n"));

        LogicException exception = assertThrows(LogicException.class, () -> {
            if (!currentOptions.isEmpty()) {
                throw new LogicException(sb.toString());
            }
        });
        assertEquals(sb.toString(), exception.getMessage());
    }

}
