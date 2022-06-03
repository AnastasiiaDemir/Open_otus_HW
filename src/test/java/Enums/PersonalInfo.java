package Enums;

public enum PersonalInfo {
    NAME("Настя"),
    SNAME("Демирджи"),
    LNAME("Anastasiia"),
    LSNAME("Demirdzhi"),
    BNAME("Anastasiia"),
    BDATE("15.01.1989"),
    COMMUNICATIONMETHODSKYPE("mySkype"),
    COMMUNICATIONMETHODTELEGRAM("myTelegram"),
    COMPANYNAME("Рд-Лаб"),
    POSITIONNAME("Бизнес-аналитик");

    private String info;
    PersonalInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}

