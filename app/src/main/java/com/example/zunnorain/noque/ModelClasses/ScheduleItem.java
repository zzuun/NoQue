package com.example.zunnorain.noque.ModelClasses;

public class ScheduleItem {
    private String date;
    private String day;
    private boolean slotOne;
    private int slotOneBackground;
    private boolean slotTwo;
    private int slotTwoBackground;

    private boolean slotThree;
    private int slotThreeBackground;
    private boolean slotFour;
    private int slotFourBackground;
    private boolean slotFive;
    private int slotFiveBackground;
    private boolean sloSix;
    private int slotSixBackground;
    private boolean sloSeven;
    private int slotSevenBackground;

    public ScheduleItem() {
    }

    public ScheduleItem(String date, String day, boolean slotOne, int slotOneBackground, boolean slotTwo, int slotTwoBackground, boolean slotThree, int slotThreeBackground, boolean slotFour, int slotFourBackground, boolean slotFive, int slotFiveBackground, boolean sloSix, int slotSixBackground, boolean sloSeven, int slotSevenBackground) {
        this.date = date;
        this.day = day;
        this.slotOne = slotOne;
        this.slotOneBackground = slotOneBackground;
        this.slotTwo = slotTwo;
        this.slotTwoBackground = slotTwoBackground;
        this.slotThree = slotThree;
        this.slotThreeBackground = slotThreeBackground;
        this.slotFour = slotFour;
        this.slotFourBackground = slotFourBackground;
        this.slotFive = slotFive;
        this.slotFiveBackground = slotFiveBackground;
        this.sloSix = sloSix;
        this.slotSixBackground = slotSixBackground;
        this.sloSeven = sloSeven;
        this.slotSevenBackground = slotSevenBackground;
    }

    public ScheduleItem(String date, String day, boolean slotOne, boolean slotTwo, boolean slotThree, boolean slotFour, boolean slotFive, boolean sloSix, boolean sloSeven) {
        this.date = date;
        this.day = day;
        this.slotOne = slotOne;
        this.slotTwo = slotTwo;
        this.slotThree = slotThree;
        this.slotFour = slotFour;
        this.slotFive = slotFive;
        this.sloSix = sloSix;
        this.sloSeven = sloSeven;
    }

    public int getSlotOneBackground() {
        return slotOneBackground;
    }

    public void setSlotOneBackground(int slotOneBackground) {
        this.slotOneBackground = slotOneBackground;
    }

    public int getSlotTwoBackground() {
        return slotTwoBackground;
    }

    public void setSlotTwoBackground(int slotTwoBackground) {
        this.slotTwoBackground = slotTwoBackground;
    }

    public int getSlotThreeBackground() {
        return slotThreeBackground;
    }

    public void setSlotThreeBackground(int slotThreeBackground) {
        this.slotThreeBackground = slotThreeBackground;
    }

    public int getSlotFourBackground() {
        return slotFourBackground;
    }

    public void setSlotFourBackground(int slotFourBackground) {
        this.slotFourBackground = slotFourBackground;
    }

    public int getSlotFiveBackground() {
        return slotFiveBackground;
    }

    public void setSlotFiveBackground(int slotFiveBackground) {
        this.slotFiveBackground = slotFiveBackground;
    }

    public int getSlotSixBackground() {
        return slotSixBackground;
    }

    public void setSlotSixBackground(int slotSixBackground) {
        this.slotSixBackground = slotSixBackground;
    }

    public int getSlotSevenBackground() {
        return slotSevenBackground;
    }

    public void setSlotSevenBackground(int slotSevenBackground) {
        this.slotSevenBackground = slotSevenBackground;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isSlotOne() {
        return slotOne;
    }

    public void setSlotOne(boolean slotOne) {
        this.slotOne = slotOne;
    }

    public boolean isSlotTwo() {
        return slotTwo;
    }

    public void setSlotTwo(boolean slotTwo) {
        this.slotTwo = slotTwo;
    }

    public boolean isSlotThree() {
        return slotThree;
    }

    public void setSlotThree(boolean slotThree) {
        this.slotThree = slotThree;
    }

    public boolean isSlotFour() {
        return slotFour;
    }

    public void setSlotFour(boolean slotFour) {
        this.slotFour = slotFour;
    }

    public boolean isSlotFive() {
        return slotFive;
    }

    public void setSlotFive(boolean slotFive) {
        this.slotFive = slotFive;
    }

    public boolean isSloSix() {
        return sloSix;
    }

    public void setSloSix(boolean sloSix) {
        this.sloSix = sloSix;
    }

    public boolean isSloSeven() {
        return sloSeven;
    }

    public void setSloSeven(boolean sloSeven) {
        this.sloSeven = sloSeven;
    }
}
