package com.xz.cenco.doctor;

import com.cenco.lib.common.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class DataHelper {

    private static List<FeeRatio> list;
    private static List<Person> personList;

    public static List<FeeRatio> getFeeRatios() {

        if (list != null) {
            return list;
        }
        list = new ArrayList<>();
        FeeRatio feeRatio1 = new FeeRatio(0, 4, 488, 1021);
        FeeRatio feeRatio2 = new FeeRatio(5, 10, 135, 284);
        FeeRatio feeRatio3 = new FeeRatio(11, 15, 88, 180);
        FeeRatio feeRatio4 = new FeeRatio(16, 20, 89, 181);
        FeeRatio feeRatio5 = new FeeRatio(21, 25, 129, 269);
        FeeRatio feeRatio6 = new FeeRatio(26, 30, 192, 399);
        FeeRatio feeRatio7 = new FeeRatio(31, 35, 262, 576);
        FeeRatio feeRatio8 = new FeeRatio(36, 40, 348, 868);
        FeeRatio feeRatio9 = new FeeRatio(41, 45, 450, 1286);
        FeeRatio feeRatio10 = new FeeRatio(46, 50, 572, 1797);
        FeeRatio feeRatio11 = new FeeRatio(51, 55, 747, 2407);
        FeeRatio feeRatio12 = new FeeRatio(56, 60, 1026, 3198);
        FeeRatio feeRatio13 = new FeeRatio(61, 65, 1422, 4330);
        FeeRatio feeRatio14 = new FeeRatio(66, 70, 1839, 5567);
        FeeRatio feeRatio15 = new FeeRatio(71, 75, 2321, 6994);
        FeeRatio feeRatio16 = new FeeRatio(76, 80, 2860, 8556);
        FeeRatio feeRatio17 = new FeeRatio(81, 85, 3431, 10267);
        FeeRatio feeRatio18 = new FeeRatio(86, 90, 4118, 12321);
        FeeRatio feeRatio19 = new FeeRatio(91, 95, 4941, 14787);
        FeeRatio feeRatio20 = new FeeRatio(96, 100, 5930, 17741);

        list.add(feeRatio1);
        list.add(feeRatio2);
        list.add(feeRatio3);
        list.add(feeRatio4);
        list.add(feeRatio5);
        list.add(feeRatio6);
        list.add(feeRatio7);
        list.add(feeRatio8);
        list.add(feeRatio9);
        list.add(feeRatio10);
        list.add(feeRatio11);
        list.add(feeRatio12);
        list.add(feeRatio13);
        list.add(feeRatio14);
        list.add(feeRatio15);
        list.add(feeRatio16);
        list.add(feeRatio17);
        list.add(feeRatio18);
        list.add(feeRatio19);
        list.add(feeRatio20);
        return list;
    }


    public static int getTotalFee(int currentAge, int liveAge, boolean withHealth) {

        int a = currentAge;
        List<FeeRatio> list = getFeeRatios();
        int total = 0;

        for (int i = 0; i < list.size(); i++) {
            FeeRatio feeRatio = list.get(i);
            int startAge = feeRatio.getStartAge();
            int stopAge = feeRatio.getStopAge();
            boolean up = currentAge >= startAge && currentAge <= stopAge;
            boolean down = currentAge <= liveAge;
            for (int j = startAge; up && down && j <= stopAge; j++) {
                int fee = withHealth ? feeRatio.getPriceWithHealth() : feeRatio.getPriceWithoutHealth();
                total += fee;
                LogUtils.d("年龄" + currentAge + "缴费" + fee);
                currentAge++;
                if (currentAge>liveAge){
                    break;
                }
            }

        }

        LogUtils.d("年龄(" + a +"-"+liveAge + ")共缴费" + total);
        return total;
    }

    public static int getYearFeeInFamily(int destYear){
        List<Person> personList = getPersonList();
        int total = 0;
        for (Person p : personList){
            int birthYear = p.getBirthYear();
            int age = destYear - birthYear;
            int totalFee = getTotalFee(age, age, p.isDoctor());


            p.setDestYear(destYear);
            p.setFee(totalFee);

            total += totalFee;
        }

        return total;
    }



    public static List<Person> getPersonList(){

        if(personList!=null){
            return personList;
        }
        personList= new ArrayList<>();

        Person person1 = new Person("心中", 1989, true);
        Person person2 = new Person("姗姗", 1990, true);
        Person person3 = new Person("姗爸", 1966, true);
        Person person4 = new Person("姗妈", 1964, true);
        Person person5 = new Person("忠爸", 1963, true);
        Person person6 = new Person("忠妈", 1965, true);
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        personList.add(person5);
        personList.add(person6);
        return personList;
    }


    public static String getPersonString(){
        List<Person> list = getPersonList();
        StringBuffer sb = new StringBuffer();
        for (Person p:list
             ) {
            sb.append(p.toString()+"\r\n");
        }
        return sb.toString();
    }
}
